import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './contexts/AuthContext';
import { CartProvider } from './contexts/CartContext';
import { NotificationProvider } from './contexts/NotificationContext';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import Dashboard from './components/dashboard/Dashboard';
import EventList from './components/events/EventList';
import EventDetails from './components/events/EventDetails';
import CreateEvent from './components/events/CreateEvent';
import EditEvent from './components/events/EditEvent';
import VenueList from './components/venues/VenueList';
import CreateVenue from './components/venues/CreateVenue';
import Cart from './components/cart/Cart';
import BookingHistory from './components/bookings/BookingHistory';
import UserProfile from './components/profile/UserProfile';
import AdminPanel from './components/admin/AdminPanel';
import Navigation from './components/layout/Navigation';
import ProtectedRoute from './components/auth/ProtectedRoute';
import { Toaster } from './components/ui/sonner';

function AppContent() {
  const { user, loading } = useAuth();

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="animate-spin rounded-full h-32 w-32 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      {user && <Navigation />}
      <main className={user ? "pt-16" : ""}>
        <Routes>
          {/* Public Routes */}
          <Route 
            path="/login" 
            element={user ? <Navigate to="/dashboard" /> : <Login />} 
          />
          <Route 
            path="/register" 
            element={user ? <Navigate to="/dashboard" /> : <Register />} 
          />

          {/* Protected Routes */}
          <Route path="/dashboard" element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          } />
          
          <Route path="/events" element={
            <ProtectedRoute>
              <EventList />
            </ProtectedRoute>
          } />
          
          <Route path="/events/:id" element={
            <ProtectedRoute>
              <EventDetails />
            </ProtectedRoute>
          } />
          
          <Route path="/events/create" element={
            <ProtectedRoute allowedRoles={['ADMIN', 'EVENT_ORGANIZER']}>
              <CreateEvent />
            </ProtectedRoute>
          } />
          
          <Route path="/events/:id/edit" element={
            <ProtectedRoute allowedRoles={['ADMIN', 'EVENT_ORGANIZER']}>
              <EditEvent />
            </ProtectedRoute>
          } />
          
          <Route path="/venues" element={
            <ProtectedRoute allowedRoles={['ADMIN']}>
              <VenueList />
            </ProtectedRoute>
          } />
          
          <Route path="/venues/create" element={
            <ProtectedRoute allowedRoles={['ADMIN']}>
              <CreateVenue />
            </ProtectedRoute>
          } />
          
          <Route path="/cart" element={
            <ProtectedRoute>
              <Cart />
            </ProtectedRoute>
          } />
          
          <Route path="/bookings" element={
            <ProtectedRoute>
              <BookingHistory />
            </ProtectedRoute>
          } />
          
          <Route path="/profile" element={
            <ProtectedRoute>
              <UserProfile />
            </ProtectedRoute>
          } />
          
          <Route path="/admin" element={
            <ProtectedRoute allowedRoles={['ADMIN']}>
              <AdminPanel />
            </ProtectedRoute>
          } />

          {/* Default Route */}
          <Route 
            path="/" 
            element={
              user ? <Navigate to="/dashboard" /> : <Navigate to="/login" />
            } 
          />
        </Routes>
      </main>
      <Toaster position="top-right" />
    </div>
  );
}

export default function App() {
  return (
    <Router>
      <AuthProvider>
        <CartProvider>
          <NotificationProvider>
            <AppContent />
          </NotificationProvider>
        </CartProvider>
      </AuthProvider>
    </Router>
  );
}