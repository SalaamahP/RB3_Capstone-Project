import React, { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import { useCart } from '../../contexts/CartContext';
import { useNotifications } from '../../contexts/NotificationContext';
import { Button } from '../ui/button';
import { 
  Calendar, 
  User, 
  ShoppingCart, 
  Bell, 
  LogOut, 
  Menu, 
  X,
  MapPin,
  Settings,
  BookOpen,
  Home
} from 'lucide-react';
import { Badge } from '../ui/badge';

export default function Navigation() {
  const [isOpen, setIsOpen] = useState(false);
  const { user, logout, hasRole } = useAuth();
  const { getTotalItems } = useCart();
  const { unreadCount } = useNotifications();
  const location = useLocation();

  const navigation = [
    { name: 'Dashboard', href: '/dashboard', icon: Home },
    { name: 'Events', href: '/events', icon: Calendar },
    { name: 'Bookings', href: '/bookings', icon: BookOpen },
    ...(hasRole('ADMIN') ? [
      { name: 'Venues', href: '/venues', icon: MapPin },
      { name: 'Admin', href: '/admin', icon: Settings },
    ] : []),
  ];

  const isActive = (href: string) => location.pathname === href;

  return (
    <nav className="bg-white shadow-lg fixed top-0 left-0 right-0 z-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16">
          <div className="flex items-center">
            <Link to="/dashboard" className="flex items-center space-x-2">
              <Calendar className="h-8 w-8 text-blue-600" />
              <span className="text-xl text-gray-900">EventHub</span>
            </Link>
          </div>

          {/* Desktop Navigation */}
          <div className="hidden md:flex items-center space-x-4">
            {navigation.map((item) => {
              const Icon = item.icon;
              return (
                <Link
                  key={item.name}
                  to={item.href}
                  className={`flex items-center space-x-1 px-3 py-2 rounded-md transition-colors ${
                    isActive(item.href)
                      ? 'bg-blue-100 text-blue-700'
                      : 'text-gray-700 hover:text-blue-600 hover:bg-gray-100'
                  }`}
                >
                  <Icon className="h-4 w-4" />
                  <span>{item.name}</span>
                </Link>
              );
            })}
          </div>

          {/* User Actions */}
          <div className="hidden md:flex items-center space-x-4">
            {/* Cart */}
            <Link to="/cart" className="relative p-2 text-gray-700 hover:text-blue-600 transition-colors">
              <ShoppingCart className="h-6 w-6" />
              {getTotalItems() > 0 && (
                <Badge className="absolute -top-1 -right-1 h-5 w-5 flex items-center justify-center p-0 text-xs">
                  {getTotalItems()}
                </Badge>
              )}
            </Link>

            {/* Notifications */}
            <button className="relative p-2 text-gray-700 hover:text-blue-600 transition-colors">
              <Bell className="h-6 w-6" />
              {unreadCount > 0 && (
                <Badge className="absolute -top-1 -right-1 h-5 w-5 flex items-center justify-center p-0 text-xs">
                  {unreadCount}
                </Badge>
              )}
            </button>

            {/* Profile */}
            <Link 
              to="/profile" 
              className="flex items-center space-x-2 p-2 text-gray-700 hover:text-blue-600 transition-colors"
            >
              <User className="h-6 w-6" />
              <span className="hidden lg:block">{user?.name}</span>
            </Link>

            {/* Logout */}
            <Button
              onClick={logout}
              variant="ghost"
              size="sm"
              className="text-gray-700 hover:text-red-600"
            >
              <LogOut className="h-4 w-4" />
            </Button>
          </div>

          {/* Mobile menu button */}
          <div className="md:hidden flex items-center">
            <button
              onClick={() => setIsOpen(!isOpen)}
              className="p-2 text-gray-700 hover:text-blue-600 transition-colors"
            >
              {isOpen ? <X className="h-6 w-6" /> : <Menu className="h-6 w-6" />}
            </button>
          </div>
        </div>
      </div>

      {/* Mobile Navigation */}
      {isOpen && (
        <div className="md:hidden bg-white border-t">
          <div className="px-2 pt-2 pb-3 space-y-1">
            {navigation.map((item) => {
              const Icon = item.icon;
              return (
                <Link
                  key={item.name}
                  to={item.href}
                  onClick={() => setIsOpen(false)}
                  className={`flex items-center space-x-2 px-3 py-2 rounded-md transition-colors ${
                    isActive(item.href)
                      ? 'bg-blue-100 text-blue-700'
                      : 'text-gray-700 hover:text-blue-600 hover:bg-gray-100'
                  }`}
                >
                  <Icon className="h-4 w-4" />
                  <span>{item.name}</span>
                </Link>
              );
            })}
            
            <div className="border-t pt-4 mt-4">
              <Link
                to="/cart"
                onClick={() => setIsOpen(false)}
                className="flex items-center justify-between px-3 py-2 text-gray-700 hover:text-blue-600 transition-colors"
              >
                <div className="flex items-center space-x-2">
                  <ShoppingCart className="h-4 w-4" />
                  <span>Cart</span>
                </div>
                {getTotalItems() > 0 && (
                  <Badge>{getTotalItems()}</Badge>
                )}
              </Link>
              
              <Link
                to="/profile"
                onClick={() => setIsOpen(false)}
                className="flex items-center space-x-2 px-3 py-2 text-gray-700 hover:text-blue-600 transition-colors"
              >
                <User className="h-4 w-4" />
                <span>Profile</span>
              </Link>
              
              <button
                onClick={() => {
                  logout();
                  setIsOpen(false);
                }}
                className="flex items-center space-x-2 px-3 py-2 w-full text-left text-gray-700 hover:text-red-600 transition-colors"
              >
                <LogOut className="h-4 w-4" />
                <span>Logout</span>
              </button>
            </div>
          </div>
        </div>
      )}
    </nav>
  );
}