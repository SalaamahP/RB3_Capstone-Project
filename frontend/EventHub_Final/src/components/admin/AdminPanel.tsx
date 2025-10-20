import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import apiService, { Event, Venue, User, TicketBooking } from '../../services/api';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '../ui/card';
import { Button } from '../ui/button';
import { Badge } from '../ui/badge';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '../ui/tabs';
import { 
  Users, 
  Calendar, 
  MapPin, 
  TrendingUp, 
  DollarSign,
  Settings,
  BarChart3,
  Plus,
  Eye,
  Edit,
  Trash2
} from 'lucide-react';
import { toast } from '../ui/sonner';

export default function AdminPanel() {
  const [stats, setStats] = useState({
    totalUsers: 0,
    totalEvents: 0,
    totalVenues: 0,
    totalBookings: 0,
    totalRevenue: 0
  });
  const [recentEvents, setRecentEvents] = useState<Event[]>([]);
  const [recentBookings, setRecentBookings] = useState<TicketBooking[]>([]);
  const [venues, setVenues] = useState<Venue[]>([]);
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);


  useEffect(() => {
    loadAdminData();
    loadUsers();
  }, []);

  const loadUsers = async () => {
    setLoading(true);
        try {
          const data = await apiService.getUsers();
          setUsers(data);
        }catch (error){
          console.error("Failed to load users", error);
        }
      };

      const handleAddRole = async (userId: number, roleId: number) =>{
        try {
          await apiService.addUserRole(userId, roleId);
          toast.success("User Role added successfully");
          loadUsers();
        } catch (error){
          toast.error("Failed to add User Role");
        }
      };

       const handleRemoveRole = async (userId: number, roleId: number) =>{
        try {
          await apiService.removeUserRole(userId, roleId);
          toast.success("User Role removed successfully");
          loadUsers();
        } catch (error){
          toast.error("Failed to remove User Role");
        }
      };

  const loadAdminData = async () => {
    try {
      const [eventsData, venuesData, bookingsData] = await Promise.all([
        apiService.getEvents(),
        apiService.getVenues(),
        apiService.getBookings()
      ]);

      

      // Calculate stats
      const totalRevenue = bookingsData.reduce((sum, booking) => sum + booking.total, 0);
      
      setStats({
        totalUsers: 0, // Would need a users endpoint
        totalEvents: eventsData.length,
        totalVenues: venuesData.length,
        totalBookings: bookingsData.length,
        totalRevenue
      });

      // Get recent data
      const recentEvs = eventsData
        .sort((a, b) => new Date(b.dateTime).getTime() - new Date(a.dateTime).getTime())
        .slice(0, 5);
      
      const recentBooks = bookingsData
        .sort((a, b) => new Date(b.bookingDate).getTime() - new Date(a.bookingDate).getTime())
        .slice(0, 5);

      setRecentEvents(recentEvs);
      setRecentBookings(recentBooks);
      setVenues(venuesData);
    } catch (error) {
      console.error('Failed to load admin data:', error);
    } finally {
      setLoading(false);
    }
  };

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('en-ZA', {
      style: 'currency',
      currency: 'ZAR'
    }).format(amount);
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  if (loading) {
    return (
      <div className="p-6">
        <div className="animate-pulse space-y-6">
          <div className="h-8 bg-gray-200 rounded w-1/4"></div>
          <div className="grid grid-cols-1 md:grid-cols-5 gap-6">
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
        <h1 className="text-3xl text-gray-900">Admin Panel</h1>
        <p className="text-gray-600 mt-1">
          Manage the Student Event Management System
        </p>
      </div>

      {/* Stats Overview */}
      <div className="grid grid-cols-1 md:grid-cols-5 gap-4">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm">Total Users</CardTitle>
            <Users className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl">{stats.totalUsers}</div>
            <p className="text-xs text-muted-foreground">
              Registered users
            </p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm">Total Events</CardTitle>
            <Calendar className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl">{stats.totalEvents}</div>
            <p className="text-xs text-muted-foreground">
              Events created
            </p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm">Venues</CardTitle>
            <MapPin className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl">{stats.totalVenues}</div>
            <p className="text-xs text-muted-foreground">
              Available venues
            </p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm">Bookings</CardTitle>
            <TrendingUp className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl">{stats.totalBookings}</div>
            <p className="text-xs text-muted-foreground">
              Total bookings
            </p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm">Revenue</CardTitle>
            <DollarSign className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl">{formatCurrency(stats.totalRevenue)}</div>
            <p className="text-xs text-muted-foreground">
              Total revenue
            </p>
          </CardContent>
        </Card>
      </div>

      {/* Management Tabs */}
      <Tabs defaultValue="overview" className="space-y-4">
        <TabsList>
          <TabsTrigger value="overview">Overview</TabsTrigger>
          <TabsTrigger value="users">Users</TabsTrigger>
          <TabsTrigger value="events">Events</TabsTrigger>
          <TabsTrigger value="venues">Venues</TabsTrigger>
          <TabsTrigger value="bookings">Bookings</TabsTrigger>
        </TabsList>

        <TabsContent value="overview" className="space-y-4">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
            {/* Recent Events */}
            <Card>
              <CardHeader>
                <div className="flex items-center justify-between">
                  <CardTitle>Recent Events</CardTitle>
                  <Button size="sm" asChild>
                    <Link to="/events/create">
                      <Plus className="h-4 w-4 mr-2" />
                      New Event
                    </Link>
                  </Button>
                </div>
              </CardHeader>
              <CardContent>
                <div className="space-y-3">
                  {recentEvents.length === 0 ? (
                    <p className="text-gray-500 text-center py-4">No events yet</p>
                  ) : (
                    recentEvents.map((event) => (
                      <div key={event.eventId} className="flex items-center justify-between p-3 border rounded-lg">
                        <div>
                          <h4 className="font-medium">{event.eventName}</h4>
                          <p className="text-sm text-gray-500">{formatDate(event.dateTime)}</p>
                        </div>
                        <Badge variant="secondary">{event.eventCategory}</Badge>
                      </div>
                    ))
                  )}
                </div>
              </CardContent>
            </Card>

          
            

            {/* Recent Bookings */}
            <Card>
              <CardHeader>
                <CardTitle>Recent Bookings</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-3">
                  {recentBookings.length === 0 ? (
                    <p className="text-gray-500 text-center py-4">No bookings yet</p>
                  ) : (
                    recentBookings.map((booking) => (
                      <div key={booking.bookingId} className="flex items-center justify-between p-3 border rounded-lg">
                        <div>
                          <h4 className="font-medium">Booking #{booking.bookingId}</h4>
                          <p className="text-sm text-gray-500">{formatDate(booking.bookingDate)}</p>
                        </div>
                        <div className="text-right">
                          <div className="font-medium">{formatCurrency(booking.total)}</div>
                          <Badge 
                            variant={booking.status === 'CONFIRMED' ? 'default' : 'secondary'}
                          >
                            {booking.status}
                          </Badge>
                        </div>
                      </div>
                    ))
                  )}
                </div>
              </CardContent>
            </Card>
          </div>
        </TabsContent>

        <TabsContent value="events" className="space-y-4">
          <Card>
            <CardHeader>
              <div className="flex items-center justify-between">
                <CardTitle>Event Management</CardTitle>
                <Button asChild>
                  <Link to="/events/create">
                    <Plus className="h-4 w-4 mr-2" />
                    Create Event
                  </Link>
                </Button>
              </div>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                {recentEvents.map((event) => (
                  <div key={event.eventId} className="flex items-center justify-between p-4 border rounded-lg">
                    <div className="flex-1">
                      <h4 className="font-medium">{event.eventName}</h4>
                      <p className="text-sm text-gray-500 mt-1">{event.eventDescription}</p>
                      <div className="flex items-center space-x-4 mt-2 text-xs text-gray-500">
                        <span>{formatDate(event.dateTime)}</span>
                        <span>Venue {event.venueId}</span>
                        <span>{formatCurrency(event.ticketPrice)}</span>
                      </div>
                    </div>
                    <div className="flex space-x-2">
                      <Button size="sm" variant="outline" asChild>
                        <Link to={`/events/${event.eventId}`}>
                          <Eye className="h-3 w-3" />
                        </Link>
                      </Button>
                      <Button size="sm" variant="outline" asChild>
                        <Link to={`/events/${event.eventId}/edit`}>
                          <Edit className="h-3 w-3" />
                        </Link>
                      </Button>
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="venues" className="space-y-4">
          <Card>
            <CardHeader>
              <div className="flex items-center justify-between">
                <CardTitle>Venue Management</CardTitle>
                <Button asChild>
                  <Link to="/venues/create">
                    <Plus className="h-4 w-4 mr-2" />
                    Add Venue
                  </Link>
                </Button>
              </div>
            </CardHeader>
            <CardContent>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                {venues.map((venue) => (
                  <div key={venue.venueId} className="p-4 border rounded-lg">
                    <h4 className="font-medium">{venue.venueName}</h4>
                    <p className="text-sm text-gray-500 mt-1">{venue.venueLocation}</p>
                    <div className="flex items-center justify-between mt-3">
                      <div className="flex items-center text-sm text-gray-500">
                        <Users className="h-4 w-4 mr-1" />
                        {venue.capacity} capacity
                      </div>
                      <Badge variant="secondary">ID: {venue.venueId}</Badge>
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="bookings" className="space-y-4">
          <Card>
            <CardHeader>
              <CardTitle>Booking Management</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                {recentBookings.map((booking) => (
                  <div key={booking.bookingId} className="flex items-center justify-between p-4 border rounded-lg">
                    <div className="flex-1">
                      <h4 className="font-medium">Booking #{booking.bookingId}</h4>
                      <div className="flex items-center space-x-4 mt-1 text-sm text-gray-500">
                        <span>{booking.numberOfTickets} tickets</span>
                        <span>{booking.paymentSelection}</span>
                        <span>{formatDate(booking.bookingDate)}</span>
                      </div>
                    </div>
                    <div className="text-right">
                      <div className="font-medium">{formatCurrency(booking.total)}</div>
                      <Badge 
                        variant={
                          booking.status === 'CONFIRMED' ? 'default' : 
                          booking.status === 'PENDING' ? 'secondary' : 
                          'destructive'
                        }
                      >
                        {booking.status}
                      </Badge>
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </TabsContent>
        <TabsContent value="users" className= "space-y-4">
              <Card>
                <CardHeader>
                  <CardTitle>User Management</CardTitle>
                  <p className="text-sm text-gray-500">
                    View and Manage User Roles
                    </p>
                </CardHeader>
                <CardContent>
                  {loading ? (
                    <p>Loading users...</p>
                  ) : (
                    <div className="overflow-x-auto">
                      <table className="min-w-full border rounded-lg">
                        <thead className="bg-gray-50">
                          <tr>
                            <th className="px-4 py-2 text-left text-sm text-gray-600">User Id</th>
                            <th className="px-4 py-2 text-left text-sm text-gray-600">Name</th>
                            <th className="px-4 py-2 text-left text-sm text-gray-600">Email</th>
                            <th className="px-4 py-2 text-left text-sm text-gray-600">Role</th>
                            <th className="px-4 py-2 text-left text-sm text-center">Actions</th>
                          </tr>
                        </thead>
                        <tbody>
                          {users.map((user)=>(
                            <tr key={user.userId} className="Border-t hover:bg-gray-50">
                            <td className="px-4 py-2">{user.userId}</td>
                            <td className="px-4 py-2">{user.name} {user.surname}</td>
                            <td className="px-4 py-2">{user.email}</td>
                            <td className="px-4 py-2">
                            {user.roles?.map(r => ( <Badge key={r.roleId} variant="secondary" className="mr-1"> {r.roleName} </Badge> ))}
                            </td>
                            <td className="px-4 py-2 text-center space-x-2">
                              <Button
                              size="sm"
                              variant="outline"
                              onClick={() => handleAddRole(Number(user.userId), 1)}
                              >
                                Promote
                              </Button>
                              <Button
                              size="sm"
                              variant="destructive"
                            onClick={() => handleRemoveRole(Number(user.userId), 1)}
                              >
                                Demote
                              </Button>
                            </td>
                            </tr>
                          ))}
                        </tbody>
                      </table>
                    </div>
                  )}
                </CardContent>
              </Card>
            </TabsContent>

            
      </Tabs>
    </div>
  );
}