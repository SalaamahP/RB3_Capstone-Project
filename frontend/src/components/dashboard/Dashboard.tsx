import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import { useCart } from '../../contexts/CartContext';
import { useNotifications } from '../../contexts/NotificationContext';
import apiService, { Event, TicketBooking } from '../../services/api';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '../ui/card';
import { Button } from '../ui/button';
import { Badge } from '../ui/badge';
import { 
  Calendar, 
  MapPin, 
  Clock, 
  Users, 
  ShoppingCart, 
  Bell,
  TrendingUp,
  Ticket,
  Plus
} from 'lucide-react';

export default function Dashboard() {
  const { user, hasRole } = useAuth();
  const { getTotalItems, getTotalPrice } = useCart();
  const { notifications, unreadCount } = useNotifications();
  const [upcomingEvents, setUpcomingEvents] = useState<Event[]>([]);
  const [recentBookings, setRecentBookings] = useState<TicketBooking[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadDashboardData();
  }, []);

  const loadDashboardData = async () => {
    try {
      const [eventsData, bookingsData] = await Promise.all([
        apiService.getEvents(),
        apiService.getBookings()
      ]);

      // Get upcoming events (next 5)
      const upcoming = eventsData
        .filter(event => new Date(event.dateTime) > new Date())
        .sort((a, b) => new Date(a.dateTime).getTime() - new Date(b.dateTime).getTime())
        .slice(0, 5);

      // Get recent bookings (last 5)
      const recent = bookingsData
        .sort((a, b) => new Date(b.bookingDate).getTime() - new Date(a.bookingDate).getTime())
        .slice(0, 5);

      setUpcomingEvents(upcoming);
      setRecentBookings(recent);
    } catch (error) {
      console.error('Failed to load dashboard data:', error);
    } finally {
      setLoading(false);
    }
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
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD'
    }).format(amount);
  };

  if (loading) {
    return (
      <div className="p-6">
        <div className="animate-pulse space-y-6">
          <div className="h-8 bg-gray-200 rounded w-1/4"></div>
          <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
            {[...Array(4)].map((_, i) => (
              <div key={i} className="h-32 bg-gray-200 rounded"></div>
            ))}
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="p-6 space-y-6">
      {/* Welcome Header */}
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl text-gray-900">
            Welcome back, {user?.name}!
          </h1>
          <p className="text-gray-600 mt-1">
            Here's what's happening with your events
          </p>
        </div>
        {hasRole('ADMIN') || hasRole('EVENT_ORGANIZER') ? (
          <Button asChild>
            <Link to="/events/create">
              <Plus className="h-4 w-4 mr-2" />
              Create Event
            </Link>
          </Button>
        ) : null}
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm">Upcoming Events</CardTitle>
            <Calendar className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl">{upcomingEvents.length}</div>
            <p className="text-xs text-muted-foreground">
              Events you can attend
            </p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm">Cart Items</CardTitle>
            <ShoppingCart className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl">{getTotalItems()}</div>
            <p className="text-xs text-muted-foreground">
              {formatCurrency(getTotalPrice())} total
            </p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm">My Bookings</CardTitle>
            <Ticket className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl">{recentBookings.length}</div>
            <p className="text-xs text-muted-foreground">
              Total bookings made
            </p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm">Notifications</CardTitle>
            <Bell className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl">{unreadCount}</div>
            <p className="text-xs text-muted-foreground">
              Unread notifications
            </p>
          </CardContent>
        </Card>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Upcoming Events */}
        <Card>
          <CardHeader>
            <CardTitle>Upcoming Events</CardTitle>
            <CardDescription>
              Events happening soon that you might be interested in
            </CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {upcomingEvents.length === 0 ? (
                <p className="text-gray-500 text-center py-4">No upcoming events</p>
              ) : (
                upcomingEvents.map((event) => (
                  <div key={event.eventId} className="flex items-center justify-between p-3 border rounded-lg">
                    <div className="flex-1">
                      <h4 className="font-medium text-gray-900">{event.eventName}</h4>
                      <div className="flex items-center space-x-4 mt-1 text-sm text-gray-500">
                        <div className="flex items-center">
                          <Clock className="h-3 w-3 mr-1" />
                          {formatDate(event.dateTime)}
                        </div>
                        <div className="flex items-center">
                          <MapPin className="h-3 w-3 mr-1" />
                          Venue {event.venueId}
                        </div>
                      </div>
                    </div>
                    <div className="text-right">
                      <Badge variant="secondary">{formatCurrency(event.ticketPrice)}</Badge>
                      <Button asChild size="sm" className="ml-2">
                        <Link to={`/events/${event.eventId}`}>View</Link>
                      </Button>
                    </div>
                  </div>
                ))
              )}
            </div>
            {upcomingEvents.length > 0 && (
              <div className="mt-4 text-center">
                <Button variant="outline" asChild>
                  <Link to="/events">View All Events</Link>
                </Button>
              </div>
            )}
          </CardContent>
        </Card>

        {/* Recent Bookings */}
        <Card>
          <CardHeader>
            <CardTitle>Recent Bookings</CardTitle>
            <CardDescription>
              Your latest ticket bookings and their status
            </CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {recentBookings.length === 0 ? (
                <p className="text-gray-500 text-center py-4">No bookings yet</p>
              ) : (
                recentBookings.map((booking) => (
                  <div key={booking.bookingId} className="flex items-center justify-between p-3 border rounded-lg">
                    <div className="flex-1">
                      <h4 className="font-medium text-gray-900">
                        Booking #{booking.bookingId}
                      </h4>
                      <div className="flex items-center space-x-4 mt-1 text-sm text-gray-500">
                        <span>{booking.numberOfTickets} tickets</span>
                        <span>{formatDate(booking.bookingDate)}</span>
                      </div>
                    </div>
                    <div className="text-right">
                      <Badge 
                        variant={
                          booking.status === 'CONFIRMED' ? 'default' : 
                          booking.status === 'PENDING' ? 'secondary' : 
                          'destructive'
                        }
                      >
                        {booking.status}
                      </Badge>
                      <div className="text-sm text-gray-900 mt-1">
                        {formatCurrency(booking.total)}
                      </div>
                    </div>
                  </div>
                ))
              )}
            </div>
            {recentBookings.length > 0 && (
              <div className="mt-4 text-center">
                <Button variant="outline" asChild>
                  <Link to="/bookings">View All Bookings</Link>
                </Button>
              </div>
            )}
          </CardContent>
        </Card>
      </div>

      {/* Recent Notifications */}
      {notifications.length > 0 && (
        <Card>
          <CardHeader>
            <CardTitle>Recent Notifications</CardTitle>
            <CardDescription>
              Latest updates and announcements
            </CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-3">
              {notifications.slice(0, 5).map((notification) => (
                <div 
                  key={notification.notificationId} 
                  className={`p-3 border rounded-lg ${notification.read ? 'bg-gray-50' : 'bg-blue-50 border-blue-200'}`}
                >
                  <div className="flex items-start justify-between">
                    <p className="text-sm text-gray-900 flex-1">{notification.message}</p>
                    <span className="text-xs text-gray-500 ml-2">
                      {formatDate(notification.timestamp)}
                    </span>
                  </div>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>
      )}
    </div>
  );
}