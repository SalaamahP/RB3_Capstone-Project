import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import apiService, { TicketBooking } from '../../services/api';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '../ui/card';
import { Button } from '../ui/button';
import { Badge } from '../ui/badge';
import { Input } from '../ui/input';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '../ui/select';
import { 
  Calendar, 
  MapPin, 
  Clock, 
  Ticket, 
  Search,
  Filter,
  Download,
  Eye
} from 'lucide-react';

export default function BookingHistory() {
  const [bookings, setBookings] = useState<TicketBooking[]>([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [statusFilter, setStatusFilter] = useState('all');

  useEffect(() => {
    loadBookings();
  }, []);

  const loadBookings = async () => {
    try {
      const bookingsData = await apiService.getBookings();
      setBookings(bookingsData);
    } catch (error) {
      console.error('Failed to load bookings:', error);
    } finally {
      setLoading(false);
    }
  };

  const filteredBookings = bookings.filter(booking => {
    const matchesSearch = 
      booking.bookingId.toString().includes(searchTerm) ||
      booking.event?.eventName?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      booking.paymentSelection.toLowerCase().includes(searchTerm.toLowerCase());
    
    const matchesStatus = statusFilter === 'all' || booking.status === statusFilter;
    
    return matchesSearch && matchesStatus;
  });

  const getUniqueStatuses = () => {
    return [...new Set(bookings.map(booking => booking.status))];
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD'
    }).format(amount);
  };

  const getStatusBadge = (status: string) => {
    switch (status.toUpperCase()) {
      case 'CONFIRMED':
        return <Badge className="bg-green-100 text-green-800">Confirmed</Badge>;
      case 'PENDING':
        return <Badge variant="secondary">Pending</Badge>;
      case 'CANCELLED':
        return <Badge variant="destructive">Cancelled</Badge>;
      case 'REFUNDED':
        return <Badge className="bg-orange-100 text-orange-800">Refunded</Badge>;
      default:
        return <Badge variant="secondary">{status}</Badge>;
    }
  };

  if (loading) {
    return (
      <div className="p-6">
        <div className="animate-pulse space-y-6">
          <div className="h-8 bg-gray-200 rounded w-1/4"></div>
          <div className="space-y-4">
            {[...Array(5)].map((_, i) => (
              <div key={i} className="h-32 bg-gray-200 rounded"></div>
            ))}
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="p-6 space-y-6">
      {/* Header */}
      <div>
        <h1 className="text-3xl text-gray-900">Booking History</h1>
        <p className="text-gray-600 mt-1">
          View and manage your event ticket bookings
        </p>
      </div>

      {/* Filters */}
      <Card>
        <CardHeader>
          <CardTitle className="flex items-center space-x-2">
            <Filter className="h-5 w-5" />
            <span>Filter Bookings</span>
          </CardTitle>
        </CardHeader>
        <CardContent>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div className="relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-4 w-4" />
              <Input
                placeholder="Search bookings..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="pl-10"
              />
            </div>
            
            <Select value={statusFilter} onValueChange={setStatusFilter}>
              <SelectTrigger>
                <SelectValue placeholder="Status" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="all">All Statuses</SelectItem>
                {getUniqueStatuses().map(status => (
                  <SelectItem key={status} value={status}>
                    {status}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>

            <Button
              variant="outline"
              onClick={() => {
                setSearchTerm('');
                setStatusFilter('all');
              }}
            >
              Clear Filters
            </Button>
          </div>
        </CardContent>
      </Card>

      {/* Bookings List */}
      <div className="space-y-4">
        {filteredBookings.length === 0 ? (
          <Card>
            <CardContent className="text-center py-12">
              <Ticket className="h-16 w-16 text-gray-300 mx-auto mb-4" />
              <h3 className="text-lg text-gray-900 mb-2">
                {bookings.length === 0 ? 'No bookings yet' : 'No bookings found'}
              </h3>
              <p className="text-gray-600 mb-6">
                {bookings.length === 0 
                  ? 'Start booking tickets for events you\'re interested in!'
                  : 'Try adjusting your filters or search terms.'
                }
              </p>
              <Button asChild>
                <Link to="/events">Browse Events</Link>
              </Button>
            </CardContent>
          </Card>
        ) : (
          filteredBookings.map((booking) => (
            <Card key={booking.bookingId} className="hover:shadow-md transition-shadow">
              <CardContent className="p-6">
                <div className="flex items-center justify-between">
                  <div className="flex-1">
                    <div className="flex items-center space-x-4 mb-2">
                      <h3 className="text-lg font-semibold text-gray-900">
                        Booking #{booking.bookingId}
                      </h3>
                      {getStatusBadge(booking.status)}
                    </div>
                    
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-sm text-gray-600">
                      <div>
                        <div className="flex items-center mb-1">
                          <Calendar className="h-4 w-4 mr-2" />
                          <span className="font-medium">Event:</span>
                          <span className="ml-1">
                            {booking.event?.eventName || `Event ${booking.eventId}`}
                          </span>
                        </div>
                        
                        {booking.event?.dateTime && (
                          <div className="flex items-center mb-1">
                            <Clock className="h-4 w-4 mr-2" />
                            <span className="font-medium">Date:</span>
                            <span className="ml-1">{formatDate(booking.event.dateTime)}</span>
                          </div>
                        )}
                        
                        {booking.event?.venueId && (
                          <div className="flex items-center">
                            <MapPin className="h-4 w-4 mr-2" />
                            <span className="font-medium">Venue:</span>
                            <span className="ml-1">Venue {booking.event.venueId}</span>
                          </div>
                        )}
                      </div>
                      
                      <div>
                        <div className="flex items-center mb-1">
                          <Ticket className="h-4 w-4 mr-2" />
                          <span className="font-medium">Tickets:</span>
                          <span className="ml-1">{booking.numberOfTickets}</span>
                        </div>
                        
                        <div className="flex items-center mb-1">
                          <span className="font-medium">Payment:</span>
                          <span className="ml-1 capitalize">
                            {booking.paymentSelection.replace('_', ' ')}
                          </span>
                        </div>
                        
                        <div className="flex items-center">
                          <span className="font-medium">Booked:</span>
                          <span className="ml-1">{formatDate(booking.bookingDate)}</span>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div className="text-right ml-6">
                    <div className="text-2xl text-gray-900 mb-2">
                      {formatCurrency(booking.total)}
                    </div>
                    
                    <div className="flex flex-col space-y-2">
                      {booking.event && (
                        <Button variant="outline" size="sm" asChild>
                          <Link to={`/events/${booking.eventId}`}>
                            <Eye className="h-3 w-3 mr-1" />
                            View Event
                          </Link>
                        </Button>
                      )}
                      
                      <Button variant="outline" size="sm">
                        <Download className="h-3 w-3 mr-1" />
                        Download
                      </Button>
                    </div>
                  </div>
                </div>
              </CardContent>
            </Card>
          ))
        )}
      </div>

      {/* Summary */}
      {filteredBookings.length > 0 && (
        <Card>
          <CardHeader>
            <CardTitle>Summary</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="grid grid-cols-1 md:grid-cols-4 gap-4 text-center">
              <div>
                <div className="text-2xl text-gray-900">{filteredBookings.length}</div>
                <div className="text-sm text-gray-600">Total Bookings</div>
              </div>
              <div>
                <div className="text-2xl text-green-600">
                  {filteredBookings.filter(b => b.status === 'CONFIRMED').length}
                </div>
                <div className="text-sm text-gray-600">Confirmed</div>
              </div>
              <div>
                <div className="text-2xl text-yellow-600">
                  {filteredBookings.filter(b => b.status === 'PENDING').length}
                </div>
                <div className="text-sm text-gray-600">Pending</div>
              </div>
              <div>
                <div className="text-2xl text-blue-600">
                  {formatCurrency(
                    filteredBookings.reduce((sum, booking) => sum + booking.total, 0)
                  )}
                </div>
                <div className="text-sm text-gray-600">Total Spent</div>
              </div>
            </div>
          </CardContent>
        </Card>
      )}
    </div>
  );
}