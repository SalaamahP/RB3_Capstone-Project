import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import { useCart } from '../../contexts/CartContext';
import apiService, { Event, Venue } from '../../services/api';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '../ui/card';
import { Button } from '../ui/button';
import { Input } from '../ui/input';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '../ui/select';
import { Badge } from '../ui/badge';
import { 
  Calendar, 
  MapPin, 
  Clock, 
  Users, 
  ShoppingCart, 
  Search,
  Filter,
  Plus,
  Edit
} from 'lucide-react';
import { toast } from '../ui/sonner';

export default function EventList() {
  const { hasRole } = useAuth();
  const { addToCart } = useCart();
  const [events, setEvents] = useState<Event[]>([]);
  const [venues, setVenues] = useState<Venue[]>([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [categoryFilter, setCategoryFilter] = useState('all');
  const [venueFilter, setVenueFilter] = useState('all');

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      const [eventsData, venuesData] = await Promise.all([
        apiService.getEvents(),
        apiService.getVenues()
      ]);
      setEvents(eventsData);
      setVenues(venuesData);
    } catch (error) {
      console.error('Failed to load events:', error);
      toast.error('Failed to load events');
    } finally {
      setLoading(false);
    }
  };

  const handleAddToCart = async (eventId: number) => {
    console.log('🛒 Add to Cart clicked for event ID:', eventId);
    try {
      console.log('🔄 Calling addToCart function...');
      await addToCart(eventId, 1);
      console.log('✅ Add to Cart completed successfully');
    } catch (error) {
      console.error('❌ Add to Cart failed:', error);
      // Error handled by CartContext
    }
  };

  const filteredEvents = events.filter(event => {
    const matchesSearch = event.eventName.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         event.eventDescription.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesCategory = categoryFilter === 'all' || event.eventCategory === categoryFilter;
    const matchesVenue = venueFilter === 'all' || event.venueId.toString() === venueFilter;
    
    return matchesSearch && matchesCategory && matchesVenue;
  });

  const getUniqueCategories = () => {
    return [...new Set(events.map(event => event.eventCategory))];
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      weekday: 'short',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  const formatCurrency = (amount: number) => {
    if (typeof amount !== 'number') return 'R 0.00';
    return new Intl.NumberFormat('en-ZA', {
      style: 'currency',
      currency: 'ZAR'
    }).format(amount);
  };

  const getVenueName = (venueId: number) => {
    const venue = venues.find(v => v.venueId === venueId);
    return venue?.venueName || `Venue ${venueId}`;
  };

  const isEventPast = (dateTime: string) => {
    return new Date(dateTime) < new Date();
  };

  if (loading) {
    return (
      <div className="p-6">
        <div className="animate-pulse space-y-6">
          <div className="h-8 bg-gray-200 rounded w-1/4"></div>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            {[...Array(6)].map((_, i) => (
              <div key={i} className="h-80 bg-gray-200 rounded"></div>
            ))}
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="p-6 space-y-6">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl text-gray-900">Events</h1>
          <p className="text-gray-600 mt-1">
            Discover and book tickets for upcoming events
          </p>
        </div>
        {(hasRole('ADMIN') || hasRole('EVENT_ORGANIZER')) && (
          <Button asChild>
            <Link to="/events/create">
              <Plus className="h-4 w-4 mr-2" />
              Create Event
            </Link>
          </Button>
        )}
      </div>

      {/* Filters */}
      <Card>
        <CardHeader>
          <CardTitle className="flex items-center space-x-2">
            <Filter className="h-5 w-5" />
            <span>Filter Events</span>
          </CardTitle>
        </CardHeader>
        <CardContent>
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
            <div className="relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-4 w-4" />
              <Input
                placeholder="Search events..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="pl-10"
              />
            </div>
            
            <Select value={categoryFilter} onValueChange={setCategoryFilter}>
              <SelectTrigger>
                <SelectValue placeholder="Category" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="all">All Categories</SelectItem>
                {getUniqueCategories().map(category => (
                  <SelectItem key={category} value={category}>
                    {category}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>

            <Select value={venueFilter} onValueChange={setVenueFilter}>
              <SelectTrigger>
                <SelectValue placeholder="Venue" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="all">All Venues</SelectItem>
                {venues.map(venue => (
                  <SelectItem key={venue.venueId} value={venue.venueId.toString()}>
                    {venue.venueName}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>

            <Button
              variant="outline"
              onClick={() => {
                setSearchTerm('');
                setCategoryFilter('all');
                setVenueFilter('all');
              }}
            >
              Clear Filters
            </Button>
          </div>
        </CardContent>
      </Card>

      {/* Events Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {filteredEvents.length === 0 ? (
          <div className="col-span-full text-center py-12">
            <Calendar className="h-16 w-16 text-gray-300 mx-auto mb-4" />
            <h3 className="text-lg text-gray-900 mb-2">No events found</h3>
            <p className="text-gray-600">Try adjusting your filters or search terms.</p>
          </div>
        ) : (
          filteredEvents.map((event) => (
            <Card key={event.eventId} className="overflow-hidden hover:shadow-lg transition-shadow">
              <CardHeader className="pb-3">
                <div className="flex items-center justify-between">
                  <Badge variant="secondary">{event.eventCategory}</Badge>
                  {isEventPast(event.dateTime) && (
                    <Badge variant="destructive">Past Event</Badge>
                  )}
                </div>
                <CardTitle className="line-clamp-2">{event.eventName}</CardTitle>
                <CardDescription className="line-clamp-3">
                  {event.eventDescription}
                </CardDescription>
              </CardHeader>
              
              <CardContent className="space-y-4">
                <div className="space-y-2 text-sm text-gray-600">
                  <div className="flex items-center">
                    <Clock className="h-4 w-4 mr-2" />
                    {formatDate(event.dateTime)}
                  </div>
                  <div className="flex items-center">
                    <MapPin className="h-4 w-4 mr-2" />
                    {getVenueName(event.venueId)}
                  </div>
                </div>

                <div className="flex items-center justify-between pt-2 border-t">
                  <div className="text-xl text-gray-900">
                    {formatCurrency(event.ticketPrice)}
                  </div>
                  <div className="flex space-x-2">
                    <Button variant="outline" size="sm" asChild>
                      <Link to={`/events/${event.eventId}`}>
                        View Details
                      </Link>
                    </Button>
                    
                    {!isEventPast(event.dateTime) && (
                      <Button 
                        size="sm" 
                        onClick={() => handleAddToCart(event.eventId)}
                      >
                        <ShoppingCart className="h-4 w-4 mr-1" />
                        Add to Cart
                      </Button>
                    )}
                    
                    {(hasRole('ADMIN') || hasRole('EVENT_ORGANIZER')) && (
                      <Button variant="outline" size="sm" asChild>
                        <Link to={`/events/${event.eventId}/edit`}>
                          <Edit className="h-4 w-4" />
                        </Link>
                      </Button>
                    )}
                  </div>
                </div>
              </CardContent>
            </Card>
          ))
        )}
      </div>

      {/* Results Summary */}
      {filteredEvents.length > 0 && (
        <div className="text-center text-gray-600">
          Showing {filteredEvents.length} of {events.length} events
        </div>
      )}
    </div>
  );
}
