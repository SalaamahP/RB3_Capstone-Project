import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import apiService, { Event, Venue } from '../../services/api';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '../ui/card';
import { Button } from '../ui/button';
import { Input } from '../ui/input';
import { Textarea } from '../ui/textarea';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '../ui/select';
import { Label } from '../ui/label';
import { ArrowLeft, Calendar, MapPin, DollarSign } from 'lucide-react';
import { toast } from '../ui/sonner';

export default function EditEvent() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [event, setEvent] = useState<Event | null>(null);
  const [venues, setVenues] = useState<Venue[]>([]);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [formData, setFormData] = useState({
    eventName: '',
    eventDescription: '',
    eventCategory: '',
    dateTime: '',
    venueId: '',
    ticketPrice: ''
  });

  const categories = [
    'SPORT',
    'SEMINAR',
    'OTHER',
  ];

  useEffect(() => {
    if (id) {
      loadEventData();
    }
  }, [id]);

  const loadEventData = async () => {
    try {
      const [eventData, venuesData] = await Promise.all([
        apiService.getEvent(Number(id)),
        apiService.getVenues()
      ]);

      setEvent(eventData);
      setVenues(venuesData);

      // Populate form with existing data
      setFormData({
        eventName: eventData.eventName,
        eventDescription: eventData.eventDescription,
        eventCategory: eventData.eventCategory,
        dateTime: eventData.dateTime.slice(0, 16), // Format for datetime-local input
        venueId: eventData.venueId.toString(),
        ticketPrice: eventData.ticketPrice.toString()
      });
    } catch (error) {
      console.error('Failed to load event data:', error);
      toast.error('Failed to load event data');
      navigate('/event');
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (field: string, value: string) => {
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!formData.eventName || !formData.eventDescription || !formData.eventCategory || 
        !formData.dateTime || !formData.venueId || !formData.ticketPrice) {
      toast.error('Please fill in all fields');
      return;
    }

    try {
      setSaving(true);
      const eventData = {
        eventName: formData.eventName,
        eventDescription: formData.eventDescription,
        eventCategory: formData.eventCategory,
        dateTime: formData.dateTime,
        venueId: Number(formData.venueId),
        ticketPrice: Number(formData.ticketPrice)
      };

      await apiService.updateEvent(Number(id), eventData);
      toast.success('Event updated successfully!');
      navigate(`/event/${id}`);
    } catch (error) {
      toast.error(error instanceof Error ? error.message : 'Failed to update event');
    } finally {
      setSaving(false);
    }
  };

  const isFormValid = Object.values(formData).every(value => value.trim() !== '');

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
        <div className="flex items-center space-x-4">
          <Button variant="ghost" asChild>
            <Link to={`/events/${id}`}>
              <ArrowLeft className="h-4 w-4 mr-2" />
              Back to Event
            </Link>
          </Button>
          <div>
            <h1 className="text-3xl text-gray-900">Edit Event</h1>
            <p className="text-gray-600 mt-1">Update event information</p>
          </div>
        </div>
      </div>

      <div className="max-w-2xl mx-auto">
        <Card>
          <CardHeader>
            <CardTitle>Event Details</CardTitle>
            <CardDescription>
              Update the information for "{event.eventName}"
            </CardDescription>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-6">
              {/* Event Name */}
              <div className="space-y-2">
                <Label htmlFor="eventName">Event Name *</Label>
                <Input
                  id="eventName"
                  type="text"
                  placeholder="Enter event name"
                  value={formData.eventName}
                  onChange={(e) => handleChange('eventName', e.target.value)}
                  required
                />
              </div>

              {/* Event Description */}
              <div className="space-y-2">
                <Label htmlFor="eventDescription">Event Description *</Label>
                <Textarea
                  id="eventDescription"
                  placeholder="Describe your event in detail"
                  value={formData.eventDescription}
                  onChange={(e) => handleChange('eventDescription', e.target.value)}
                  rows={4}
                  required
                />
              </div>

              {/* Category and Date */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div className="space-y-2">
                  <Label htmlFor="eventCategory">Category *</Label>
                  <Select
                    value={formData.eventCategory}
                    onValueChange={(value) => handleChange('eventCategory', value)}
                  >
                    <SelectTrigger>
                      <SelectValue placeholder="Select category" />
                    </SelectTrigger>
                    <SelectContent>
                      {categories.map(category => (
                        <SelectItem key={category} value={category}>
                          {category}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>

                <div className="space-y-2">
                  <Label htmlFor="dateTime">Date & Time *</Label>
                  <div className="relative">
                    <Calendar className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-4 w-4" />
                    <Input
                      id="dateTime"
                      type="datetime-local"
                      value={formData.dateTime}
                      onChange={(e) => handleChange('dateTime', e.target.value)}
                      className="pl-10"
                      required
                    />
                  </div>
                </div>
              </div>

              {/* Venue and Price */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div className="space-y-2">
                  <Label htmlFor="venueId">Venue *</Label>
                  <Select
                    value={formData.venueId}
                    onValueChange={(value) => handleChange('venueId', value)}
                  >
                    <SelectTrigger>
                      <SelectValue placeholder="Select venue" />
                    </SelectTrigger>
                    <SelectContent>
                      {venues.map(venue => (
                        <SelectItem key={venue.venueId} value={venue.venueId.toString()}>
                          <div className="flex items-center space-x-2">
                            <MapPin className="h-4 w-4" />
                            <div>
                              <div>{venue.venueName}</div>
                              <div className="text-xs text-gray-500">
                                Capacity: {venue.capacity} | {venue.venueLocation}
                              </div>
                            </div>
                          </div>
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>

                <div className="space-y-2">
                  <Label htmlFor="ticketPrice">Ticket Price *</Label>
                  <div className="relative">
                    <span className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-700 font-semibold text-sm">R</span>
                    <Input
                      id="ticketPrice"
                      type="number"
                      min="0"
                      step="0.01"
                      placeholder="0.00"
                      value={formData.ticketPrice}
                      onChange={(e) => handleChange('ticketPrice', e.target.value)}
                      className="pl-10"
                      required
                    />
                  </div>
                </div>
              </div>

              {/* Actions */}
              <div className="flex space-x-4 pt-6">
                <Button
                  type="submit"
                  className="flex-1"
                  disabled={saving || !isFormValid}
                >
                  {saving ? 'Updating Event...' : 'Update Event'}
                </Button>
                <Button
                  type="button"
                  variant="outline"
                  onClick={() => navigate(`/events/${id}`)}
                  disabled={saving}
                >
                  Cancel
                </Button>
              </div>
            </form>
          </CardContent>
        </Card>

        {/* Preview Card */}
        {formData.eventName && formData.eventDescription && (
          <Card className="mt-6">
            <CardHeader>
              <CardTitle>Preview</CardTitle>
              <CardDescription>
                Here's how your updated event will appear to users
              </CardDescription>
            </CardHeader>
            <CardContent>
              <div className="border rounded-lg p-4 space-y-3">
                <div className="flex items-center justify-between">
                  {formData.eventCategory && (
                    <span className="px-2 py-1 bg-blue-100 text-blue-800 rounded text-sm">
                      {formData.eventCategory}
                    </span>
                  )}
                  {formData.ticketPrice && (
                    <span className="text-lg font-semibold text-green-600">
                      R{Number(formData.ticketPrice).toFixed(2)}
                    </span>
                  )}
                </div>
                <h3 className="text-xl font-semibold">{formData.eventName}</h3>
                <p className="text-gray-600">{formData.eventDescription}</p>
                <div className="flex items-center space-x-4 text-sm text-gray-500">
                  {formData.dateTime && (
                    <div className="flex items-center">
                      <Calendar className="h-4 w-4 mr-1" />
                      {new Date(formData.dateTime).toLocaleDateString('en-US', {
                        weekday: 'short',
                        month: 'short',
                        day: 'numeric',
                        hour: '2-digit',
                        minute: '2-digit'
                      })}
                    </div>
                  )}
                  {formData.venueId && venues.length > 0 && (
                    <div className="flex items-center">
                      <MapPin className="h-4 w-4 mr-1" />
                      {venues.find(v => v.venueId.toString() === formData.venueId)?.venueName}
                    </div>
                  )}
                </div>
              </div>
            </CardContent>
          </Card>
        )}
      </div>
    </div>
  );
}
