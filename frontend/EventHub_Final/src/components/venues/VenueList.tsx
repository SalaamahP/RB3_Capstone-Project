import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import apiService, { Venue } from '../../services/api';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '../ui/card';
import { Button } from '../ui/button';
import { Input } from '../ui/input';
import { Badge } from '../ui/badge';
import { 
  MapPin, 
  Users, 
  Search,
  Plus,
  Building
} from 'lucide-react';
import { toast } from '../ui/sonner';
import { error } from 'console';

export default function VenueList() {
  const [venues, setVenues] = useState<Venue[]>([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const navigate = useNavigate();

   const handleEdit = (venue: Venue) => {
    navigate(`/venues/edit/${venue.venueId}`);
 };

  useEffect(() => {
    loadVenues();
  }, []);

  const loadVenues = async () => {
    try {
      const venuesData = await apiService.getVenues();
      setVenues(venuesData);
    } catch (error) {
      console.error('Failed to load venues:', error);
      toast.error('Failed to load venues');
    } finally {
      setLoading(false);
    }
  };

                const handleDelete = async (id: number) => {
                  if(!confirm('Are you sure you want to delete this venue?')) return;

                  try{
                    await apiService.deleteVenue(id);
                    toast.success('Venue had been successfully deleted')
                    loadVenues();
                  }catch(error) {
                    console.error(error);
                    toast.error('Venue could not be deleted');
                    
                  }
                };

  const filteredVenues = venues.filter(venue =>
    venue.venueName.toLowerCase().includes(searchTerm.toLowerCase()) ||
    venue.venueLocation.toLowerCase().includes(searchTerm.toLowerCase())
  );
  
                

  if (loading) {
    return (
      <div className="p-6">
        <div className="animate-pulse space-y-6">
          <div className="h-8 bg-gray-200 rounded w-1/4"></div>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            {[...Array(6)].map((_, i) => (
              <div key={i} className="h-48 bg-gray-200 rounded"></div>
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
          <h1 className="text-3xl text-gray-900">Venues</h1>
          <p className="text-gray-600 mt-1">
            Manage event venues and their details
          </p>
        </div>
        <Button asChild>
          <Link to="/venues/create">
            <Plus className="h-4 w-4 mr-2" />
            Add Venue
          </Link>
        </Button>
      </div>

      {/* Search */}
      <Card>
        <CardHeader>
          <CardTitle className="flex items-center space-x-2">
            <Search className="h-5 w-5" />
            <span>Search Venues</span>
          </CardTitle>
        </CardHeader>
        <CardContent>
          <div className="relative max-w-md">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-4 w-4" />
            <Input
              placeholder="Search by name or location..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="pl-10"
            />
          </div>
        </CardContent>
      </Card>

      {/* Venues Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {filteredVenues.length === 0 ? (
          <div className="col-span-full text-center py-12">
            <Building className="h-16 w-16 text-gray-300 mx-auto mb-4" />
            <h3 className="text-lg text-gray-900 mb-2">
              {venues.length === 0 ? 'No venues available' : 'No venues found'}
            </h3>
            <p className="text-gray-600 mb-6">
              {venues.length === 0 
                ? 'Add some venues to start hosting events.'
                : 'Try adjusting your search terms.'
              }
            </p>
            {venues.length === 0 && (
              <Button asChild>
                <Link to="/venues/create">Add First Venue</Link>
              </Button>
            )}
          </div>
        ) : (
          filteredVenues.map((venue) => (
            <Card key={venue.venueId} className="hover:shadow-lg transition-shadow">
              <CardHeader>
                <div className="flex items-center justify-between">
                  <Badge variant="secondary">ID: {venue.venueId}</Badge>
                  <div className="flex items-center text-sm text-gray-500">
                    <Users className="h-4 w-4 mr-1" />
                    {venue.capacity}
                  </div>
                </div>
                <CardTitle className="line-clamp-2">{venue.venueName}</CardTitle>
              </CardHeader>
              
              <CardContent className="space-y-4">
                <div className="flex items-start">
                  <MapPin className="h-4 w-4 mr-2 mt-0.5 text-gray-400" />
                  <span className="text-sm text-gray-600 line-clamp-2">
                    {venue.venueLocation}
                  </span>
                </div>

                <div className="grid grid-cols-2 gap-4 text-sm">
                  <div>
                    <span className="font-medium text-gray-700">Capacity:</span>
                    <div className="text-gray-900">{venue.capacity} people</div>
                  </div>
                  <div>
                    <span className="font-medium text-gray-700">Venue ID:</span>
                    <div className="text-gray-900">{venue.venueId}</div>
                  </div>
                </div>



                <div className="pt-2 border-t flex space-x-2">
                  <Button 
                  variant="outline" 
                  className="flex-1"
                  onClick={()=> handleEdit(venue)}
                  >
                    Edit
                  </Button>

                   <Button 
                  variant="destructive" 
                  className="flex-1"
                  onClick={()=> handleDelete(venue.venueId)}
                  >
                    Delete
                  </Button>
                </div>
              </CardContent>
            </Card>
          ))
        )}
      </div>

      {/* Results Summary */}
      {filteredVenues.length > 0 && (
        <div className="text-center text-gray-600">
          Showing {filteredVenues.length} of {venues.length} venues
        </div>
      )}

      {/* Venues Statistics */}
      {venues.length > 0 && (
        <Card>
          <CardHeader>
            <CardTitle>Venue Statistics</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="grid grid-cols-1 md:grid-cols-4 gap-4 text-center">
              <div>
                <div className="text-2xl text-gray-900">{venues.length}</div>
                <div className="text-sm text-gray-600">Total Venues</div>
              </div>
              <div>
                <div className="text-2xl text-blue-600">
                  {Math.max(...venues.map(v => v.capacity))}
                </div>
                <div className="text-sm text-gray-600">Largest Capacity</div>
              </div>
              <div>
                <div className="text-2xl text-green-600">
                  {Math.min(...venues.map(v => v.capacity))}
                </div>
                <div className="text-sm text-gray-600">Smallest Capacity</div>
              </div>
              <div>
                <div className="text-2xl text-purple-600">
                  {Math.round(venues.reduce((sum, v) => sum + v.capacity, 0) / venues.length)}
                </div>
                <div className="text-sm text-gray-600">Average Capacity</div>
              </div>
            </div>
          </CardContent>
        </Card>
      )}
    </div>
  );
}