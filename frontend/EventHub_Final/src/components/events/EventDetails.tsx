import React, { useState, useEffect } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import { useCart } from '../../contexts/CartContext';
import apiService, { Event, Venue, RSVP } from '../../services/api';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '../ui/card';
import { Button } from '../ui/button';
import { Input } from '../ui/input';
import { Badge } from '../ui/badge';
import { Separator } from '../ui/separator';
import { 
  Calendar, 
  MapPin, 
  Clock, 
  Users, 
  ShoppingCart, 
  ArrowLeft,
  Edit,
  Trash2,
  CheckCircle,
  XCircle,
  AlertCircle
} from 'lucide-react';
import { toast } from '../ui/sonner';

export default function EventDetails() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { user, hasRole } = useAuth();
  const { addToCart } = useCart();
  
  const [event, setEvent] = useState<Event | null>(null);
  const [venue, setVenue] = useState<Venue | null>(null);
  const [rsvp, setRsvp] = useState<RSVP | null>(null);
  const [quantity, setQuantity] = useState(1);
  const [studentNumber, setStudentNumber] = useState('');
  const [loading, setLoading] = useState(true);
  const [actionLoading, setActionLoading] = useState(false);

  useEffect(() => {
    if (id) {
      loadEventDetails();
    }
  }, [id]);

  const loadEventDetails = async () => {
    try {
      const eventData = await apiService.getEvent(Number(id));
      setEvent(eventData);

      // Load venue details
      const venues = await apiService.getVenues();
      const eventVenue = venues.find(v => v.venueId === eventData.venueId);
      setVenue(eventVenue || null);

      // Check if user has RSVP'd
      try {
        const rsvps = await apiService.getRSVPs();
        const existingRsvp = rsvps.find(rsvp => rsvp.eventId === eventData.eventId);
        setRsvp(existingRsvp || null);
      } catch (error) {
        // RSVP might not exist, that's okay
      }
    } catch (error) {
      console.error('Failed to load event details:', error);
      toast.error('Failed to load event details');
      navigate('/events');
    } finally {
      setLoading(false);
    }
  };

  const handleAddToCart = async () => {
    if (!event) return;
    
    try {
      setActionLoading(true);
      await addToCart(event.eventId, quantity);
    } catch (error) {
      // Error handled by CartContext
    } finally {
      setActionLoading(false);
    }
  };

  const handleRSVP = async () => {
    if (!event || !studentNumber.trim()) {
      toast.error('Please enter your student number');
      return;
    }

    try {
      setActionLoading(true);
      await apiService.createRSVP(event.eventId, studentNumber);
      toast.success('RSVP submitted successfully!');
      loadEventDetails(); // Reload to get updated RSVP status
    } catch (error) {
      toast.error(error instanceof Error ? error.message : 'Failed to submit RSVP');
    } finally {
      setActionLoading(false);
    }
  };

  const handleDeleteEvent = async () => {
    if (!event) return;
    
    if (!confirm('Are you sure you want to delete this event? This action cannot be undone.')) {
      return;
    }

    try {
      setActionLoading(true);
      await apiService.deleteEvent(event.eventId);
      toast.success('Event deleted successfully');
      navigate('/events');
    } catch (error) {
      toast.error(error instanceof Error ? error.message : 'Failed to delete event');
    } finally {
      setActionLoading(false);
    }
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      weekday: 'long',
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  const formatCurrency = (amount:number) => {
    if (typeof amount !== 'number') return 'R 0.00';
    return new Intl.NumberFormat('en-ZA', {
      style: 'currency',
      currency: 'ZAR'
    }).format(amount);
  };

  const isEventPast = (dateTime: string) => {
    return new Date(dateTime) < new Date();
  };

  const getRSVPStatusBadge = () => {
    if (!rsvp) return null;
    
    switch (rsvp.status) {
      case 'CONFIRMED':
        return <Badge className="bg-green-100 text-green-800"><CheckCircle className="h-3 w-3 mr-1" />Confirmed</Badge>;
      case 'PENDING':
        return <Badge variant="secondary"><AlertCircle className="h-3 w-3 mr-1" />Pending</Badge>;
      case 'DECLINED':
        return <Badge variant="destructive"><XCircle className="h-3 w-3 mr-1" />Declined</Badge>;
      default:
        return <Badge variant="secondary">{rsvp.status}</Badge>;
    }
  };

  if (loading) {
    return (
      <div className="p-6">
        <div className="animate-pulse space-y-6">
          <div className="h-8 bg-gray-200 rounded w-1/4"></div>
          <div className="h-96 bg-gray-200 rounded"></div>
        </div>
      </div>
    );
  }

  if (!event) {
    return (
      <div className="p-6 text-center">
        <h1 className="text-2xl text-gray-900 mb-4">Event not found</h1>
        <Button asChild>
          <Link to="/events">Back to Events</Link>
        </Button>
      </div>
    );
  }

  return (
    <div className="p-6 space-y-6">
      {/* Header */}
      <div className="flex items-center justify-between">
        <Button variant="ghost" asChild>
          <Link to="/events">
            <ArrowLeft className="h-4 w-4 mr-2" />
            Back to Events
          </Link>
        </Button>
        
        {(hasRole('ADMIN') || hasRole('EVENT_ORGANIZER')) && (
          <div className="flex space-x-2">
            <Button variant="outline" asChild>
              <Link to={`/events/${event.eventId}/edit`}>
                <Edit className="h-4 w-4 mr-2" />
                Edit Event
              </Link>
            </Button>
            <Button 
              variant="destructive" 
              onClick={handleDeleteEvent}
              disabled={actionLoading}
            >
              <Trash2 className="h-4 w-4 mr-2" />
              Delete Event
            </Button>
          </div>
        )}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Main Event Details */}
        <div className="lg:col-span-2 space-y-6">
          <Card>
            <CardHeader>
              <div className="flex items-center justify-between">
                <Badge variant="secondary">{event.eventCategory}</Badge>
                {isEventPast(event.dateTime) && (
                  <Badge variant="destructive">Past Event</Badge>
                )}
              </div>
              <CardTitle className="text-3xl">{event.eventName}</CardTitle>
            </CardHeader>
            <CardContent className="space-y-6">
              <p className="text-gray-700 leading-relaxed">{event.eventDescription}</p>
              
              <Separator />
              
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <h3 className="text-lg text-gray-900 mb-3">Event Details</h3>
                  <div className="space-y-3">
                    <div className="flex items-center">
                      <Clock className="h-5 w-5 text-gray-400 mr-3" />
                      <div>
                        <div className="font-medium">{formatDate(event.dateTime)}</div>
                      </div>
                    </div>
                    
                    <div className="flex items-center">
                      <MapPin className="h-5 w-5 text-gray-400 mr-3" />
                      <div>
                        <div className="font-medium">{venue?.venueName || `Venue ${event.venueId}`}</div>
                        {venue && <div className="text-sm text-gray-600">{venue.venueLocation}</div>}
                      </div>
                    </div>
                    
                    {venue && (
                      <div className="flex items-center">
                        <Users className="h-5 w-5 text-gray-400 mr-3" />
                        <div>
                          <div className="font-medium">Capacity: {venue.capacity}</div>
                        </div>
                      </div>
                    )}
                  </div>
                </div>
                
                <div>
                  <h3 className="text-lg text-gray-900 mb-3">Pricing</h3>
                  <div className="text-3xl text-blue-600 font-bold">
                    {formatCurrency(event.ticketPrice)}
                  </div>
                  <p className="text-gray-600 mt-1">per ticket</p>
                </div>
              </div>
            </CardContent>
          </Card>
        </div>

        {/* Actions Sidebar */}
        <div className="space-y-6">
          {!isEventPast(event.dateTime) && (
            <Card>
              <CardHeader>
                <CardTitle>Book Tickets</CardTitle>
                <CardDescription>
                  Add tickets to your cart and proceed to checkout
                </CardDescription>
              </CardHeader>
              <CardContent className="space-y-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Quantity
                  </label>
                  <Input
                    type="number"
                    min="1"
                    max="10"
                    value={quantity}
                    onChange={(e) => setQuantity(Number(e.target.value))}
                  />
                </div>
                
                <div className="border-t pt-4">
                  <div className="flex justify-between text-lg font-semibold">
                    <span>Total:</span>
                    <span>{formatCurrency(event.ticketPrice * quantity)}</span>
                  </div>
                </div>
                
                <Button 
                  className="w-full" 
                  onClick={handleAddToCart}
                  disabled={actionLoading}
                >
                  <ShoppingCart className="h-4 w-4 mr-2" />
                  {actionLoading ? 'Adding...' : 'Add to Cart'}
                </Button>
              </CardContent>
            </Card>
          )}

          {/* RSVP Section */}
          <Card>
            <CardHeader>
              <CardTitle>RSVP</CardTitle>
              <CardDescription>
                Let us know if you're planning to attend
              </CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              {rsvp ? (
                <div className="text-center">
                  <div className="mb-3">
                    {getRSVPStatusBadge()}
                  </div>
                  <p className="text-sm text-gray-600">
                    You have already RSVP'd for this event.
                  </p>
                </div>
              ) : (
                <div className="space-y-3">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      Student Number
                    </label>
                    <Input
                      type="text"
                      placeholder="Enter your student number"
                      value={studentNumber}
                      onChange={(e) => setStudentNumber(e.target.value)}
                    />
                  </div>
                  
                  <Button 
                    className="w-full" 
                    variant="outline"
                    onClick={handleRSVP}
                    disabled={actionLoading || !studentNumber.trim()}
                  >
                    {actionLoading ? 'Submitting...' : 'Submit RSVP'}
                  </Button>
                </div>
              )}
            </CardContent>
          </Card>

          {/* Event Info */}
          <Card>
            <CardHeader>
              <CardTitle>Event Information</CardTitle>
            </CardHeader>
            <CardContent className="space-y-3 text-sm">
              <div className="flex justify-between">
                <span className="text-gray-600">Event ID:</span>
                <span className="font-medium">{event.eventId}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-gray-600">Category:</span>
                <span className="font-medium">{event.eventCategory}</span>
              </div>
              {venue && (
                <div className="flex justify-between">
                  <span className="text-gray-600">Venue Capacity:</span>
                  <span className="font-medium">{venue.capacity}</span>
                </div>
              )}
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
}
