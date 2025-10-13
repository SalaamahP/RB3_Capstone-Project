import React, { useState } from 'react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '../ui/card';
import { Button } from '../ui/button';
import { Input } from '../ui/input';
import { Textarea } from '../ui/textarea';
import { Label } from '../ui/label';
import { ArrowLeft, MapPin, Users, Building } from 'lucide-react';
import { toast } from '../ui/sonner';
import apiService, {Venue} from '../../services/api';
import { useNavigate, Link, useParams } from 'react-router-dom';

interface CreateVenueProps {
  venue?: Venue;
}

export default function CreateVenue({venue}: CreateVenueProps) {
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();
  const isEdit = !!id;

  const [loading, setLoading] = useState(false);
  const [formData, setFormData] = useState({
    venueName: venue?.venueName || '',
    venueLocation: venue?.venueLocation ||  '',
    capacity: venue?.capacity ||  ''
  });

  React.useEffect(()=> {
    const loadVenue = async () => {
      if (!id) return;
      try {
        setLoading(true);
        const fetchedVenue = await apiService.getVenue(Number(id));
        setFormData({
          venueName: fetchedVenue.venueName,
          venueLocation: fetchedVenue.venueLocation,
          capacity: String(fetchedVenue.capacity),
        });
      } catch (error){
        toast.error('Failed to load venue details');
        console.error(error);
      } finally {
        setLoading(false);
      }
    };

    loadVenue();
  }, [id]);

  const handleChange = (field: string, value: string) => {
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!formData.venueName || !formData.venueLocation || !formData.capacity) {
      toast.error('Please fill in all fields');
      return;
    }

    const capacity = parseInt(formData.capacity);
    if (capacity <= 0) {
      toast.error('Capacity must be a positive number');
      return;
    }

    try {
      setLoading(true);

      const venueData = {
        venueName: formData.venueName,
        venueLocation: formData.venueLocation,
        capacity: capacity
      };

      if (isEdit && id) {
        await apiService.updateVenue(Number(id),{
        venueName: formData.venueName,
        venueLocation: formData.venueLocation,
        capacity: capacity
        });
        toast.success('Venue updated successfully');
      } else {

      await apiService.createVenue(venueData);
      toast.success('Venue created successfully!');
      }

      navigate('/venues');
    } catch (error) {
      toast.error(error instanceof Error ? error.message : 'Failed to create venue');
    } finally {
      setLoading(false);
    }
  };

  const isFormValid = Object.values(formData).every(value => value.trim() !== '') &&
                     parseInt(formData.capacity) > 0;

  return (
    <div className="p-6 space-y-6">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div className="flex items-center space-x-4">
          <Button variant="ghost" asChild>
            <Link to="/venues">
              <ArrowLeft className="h-4 w-4 mr-2" />
              Back to Venues
            </Link>
          </Button>
          <div>
            <h1 className="text-3xl text-gray-900">
              {isEdit ? 'Edit Venue' : 'Add New Venue'}
              </h1>
            <p className="text-gray-600 mt-1">
              {isEdit ? 'Update venue details' : 'Create a new venue for hosting events'}
              </p>
          </div>
        </div>
      </div>

      <div className="max-w-2xl mx-auto">
        <Card>
          <CardHeader>
            <CardTitle className="flex items-center space-x-2">
              <Building className="h-6 w-6" />
              <span>Venue Details</span>
            </CardTitle>
            <CardDescription>
              Fill in the information for the new venue
            </CardDescription>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-6">
              {/* Venue Name */}
              <div className="space-y-2">
                <Label htmlFor="venueName">Venue Name *</Label>
                <Input
                  id="venueName"
                  type="text"
                  placeholder="Enter venue name (e.g., Main Auditorium)"
                  value={formData.venueName}
                  onChange={(e) => handleChange('venueName', e.target.value)}
                  required
                />
              </div>

              {/* Venue Location */}
              <div className="space-y-2">
                <Label htmlFor="venueLocation">Location *</Label>
                <div className="relative">
                  <MapPin className="absolute left-3 top-3 text-gray-400 h-4 w-4" />
                  <Textarea
                    id="venueLocation"
                    placeholder="Enter full address or location details"
                    value={formData.venueLocation}
                    onChange={(e) => handleChange('venueLocation', e.target.value)}
                    className="pl-10"
                    rows={3}
                    required
                  />
                </div>
              </div>

              {/* Capacity */}
              <div className="space-y-2">
                <Label htmlFor="capacity">Capacity *</Label>
                <div className="relative">
                  <Users className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-4 w-4" />
                  <Input
                    id="capacity"
                    type="number"
                    min="1"
                    placeholder="Enter maximum capacity"
                    value={formData.capacity}
                    onChange={(e) => handleChange('capacity', e.target.value)}
                    className="pl-10"
                    required
                  />
                </div>
                <p className="text-sm text-gray-500">
                  Maximum number of people this venue can accommodate
                </p>
              </div>

              {/* Actions */}
              <div className="flex space-x-4 pt-6">
                <Button
                  type="submit"
                  className="flex-1"
                  disabled={loading || !isFormValid}
                >
                  {loading 
                  ? (isEdit ? 'Updating Venue' : 'Creating Venue')
                  : (isEdit ? 'Update Venue' : 'Create Venue')}
                </Button>
                <Button
                  type="button"
                  variant="outline"
                  onClick={() => navigate('/venues')}
                  disabled={loading}
                >
                  Cancel
                </Button>
              </div>
            </form>
          </CardContent>
        </Card>

        {/* Preview Card */}
        {formData.venueName && formData.venueLocation && (
          <Card className="mt-6">
            <CardHeader>
              <CardTitle>Preview</CardTitle>
              <CardDescription>
                Here's how your venue will appear in the system
              </CardDescription>
            </CardHeader>
            <CardContent>
              <div className="border rounded-lg p-4 space-y-3">
                <div className="flex items-center justify-between">
                  <h3 className="text-xl font-semibold">{formData.venueName}</h3>
                  {formData.capacity && (
                    <div className="flex items-center text-sm text-gray-500">
                      <Users className="h-4 w-4 mr-1" />
                      {formData.capacity} capacity
                    </div>
                  )}
                </div>
                <div className="flex items-start">
                  <MapPin className="h-4 w-4 mr-2 mt-0.5 text-gray-400" />
                  <span className="text-gray-600">{formData.venueLocation}</span>
                </div>
                {formData.capacity && (
                  <div className="text-sm text-gray-500">
                    Can accommodate up to {formData.capacity} people
                  </div>
                )}
              </div>
            </CardContent>
          </Card>
        )}

        {/* Guidelines */}
        <Card className="mt-6">
          <CardHeader>
            <CardTitle>Guidelines</CardTitle>
          </CardHeader>
          <CardContent className="space-y-3 text-sm text-gray-600">
            <div className="flex items-start">
              <div className="w-2 h-2 bg-blue-500 rounded-full mt-2 mr-3 flex-shrink-0"></div>
              <p>Use clear, descriptive names for venues (e.g., "Main Auditorium", "Conference Room A")</p>
            </div>
            <div className="flex items-start">
              <div className="w-2 h-2 bg-blue-500 rounded-full mt-2 mr-3 flex-shrink-0"></div>
              <p>Include complete address or detailed location information</p>
            </div>
            <div className="flex items-start">
              <div className="w-2 h-2 bg-blue-500 rounded-full mt-2 mr-3 flex-shrink-0"></div>
              <p>Set capacity based on safe occupancy limits, considering exits and safety regulations</p>
            </div>
            <div className="flex items-start">
              <div className="w-2 h-2 bg-blue-500 rounded-full mt-2 mr-3 flex-shrink-0"></div>
              <p>Double-check all information before creating - venues are referenced by events</p>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}