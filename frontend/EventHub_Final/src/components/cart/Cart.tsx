import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { useCart } from '../../contexts/CartContext';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '../ui/card';
import { Button } from '../ui/button';
import { Input } from '../ui/input';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '../ui/select';
import { Badge } from '../ui/badge';
import { Separator } from '../ui/separator';
import { 
  ShoppingCart, 
  Trash2, 
  Plus, 
  Minus, 
  Calendar, 
  MapPin,
  ArrowLeft
} from 'lucide-react';
import { toast } from '../ui/sonner';

export default function Cart() {
  const { cart, loading, updateCartItem, removeFromCart, checkout, getTotalItems, getTotalPrice } = useCart();
  const [paymentOption, setPaymentOption] = useState('');
  const [checkoutLoading, setCheckoutLoading] = useState(false);

  const paymentOptions = [
      { value: 'CASH', label: 'Cash on Pickup' }
      {value: 'DEPOSIT', label: 'Deposit'}
  ];

  const handleQuantityChange = async (cartItemId: number, newQuantity: number) => {
    if (newQuantity < 1) {
      await handleRemoveItem(cartItemId);
      return;
    }
    
    try {
      await updateCartItem(cartItemId, newQuantity);
    } catch (error) {
      // Error handled by CartContext
    }
  };

  const handleRemoveItem = async (cartItemId: number) => {
    try {
      await removeFromCart(cartItemId);
    } catch (error) {
      // Error handled by CartContext
    }
  };

  const handleCheckout = async () => {
    if (!paymentOption) {
      toast.error('Please select a payment option');
      return;
    }

    try {
      setCheckoutLoading(true);
      await checkout(paymentOption);
    } catch (error) {
      // Error handled by CartContext
    } finally {
      setCheckoutLoading(false);
    }
  };

  const clearMockCart = () => {
    localStorage.removeItem('mockCart');
    window.location.reload();
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
          <div className="h-96 bg-gray-200 rounded"></div>
        </div>
      </div>
    );
  }

  const cartItems = cart?.items || [];
  const isEmpty = cartItems.length === 0;

  return (
    <div className="p-6 space-y-6">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div className="flex items-center space-x-4">
          <Button variant="ghost" asChild>
            <Link to="/events">
              <ArrowLeft className="h-4 w-4 mr-2" />
              Continue Shopping
            </Link>
          </Button>
          <div>
            <h1 className="text-3xl text-gray-900">Shopping Cart</h1>
            <p className="text-gray-600 mt-1">
              {isEmpty ? 'Your cart is empty' : `${getTotalItems()} item${getTotalItems() !== 1 ? 's' : ''} in your cart`}
            </p>
          </div>
        </div>
      </div>

      {isEmpty ? (
        <div className="text-center py-12">
          <ShoppingCart className="h-16 w-16 text-gray-300 mx-auto mb-4" />
          <h3 className="text-lg text-gray-900 mb-2">Your cart is empty</h3>
          <p className="text-gray-600 mb-6">Add some events to get started!</p>
          <Button asChild>
            <Link to="/events">Browse Events</Link>
          </Button>
        </div>
      ) : (
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Cart Items */}
          <div className="lg:col-span-2 space-y-4">
            <Card>
              <CardHeader>
                <CardTitle>Cart Items</CardTitle>
                <CardDescription>
                  Review your selected events and adjust quantities
                </CardDescription>
              </CardHeader>
              <CardContent className="space-y-4">
                {cartItems.map((item, index) => (
                  <div key={item.cartItemId}>
                    {index > 0 && <Separator />}
                    <div className="flex items-center justify-between py-4">
                      <div className="flex-1">
                        <h4 className="font-medium text-gray-900">
                          {item.event?.eventName || `Event ${item.eventId}`}
                        </h4>
                        <div className="flex items-center space-x-4 mt-1 text-sm text-gray-500">
                          {item.event?.dateTime && (
                            <div className="flex items-center">
                              <Calendar className="h-3 w-3 mr-1" />
                              {formatDate(item.event.dateTime)}
                            </div>
                          )}
                          {item.event?.venueId && (
                            <div className="flex items-center">
                              <MapPin className="h-3 w-3 mr-1" />
                              Venue {item.event.venueId}
                            </div>
                          )}
                        </div>
                        <div className="mt-2">
                          <Badge variant="secondary">
                            {item.event?.eventCategory || 'Event'}
                          </Badge>
                        </div>
                      </div>

                      <div className="flex items-center space-x-4">
                        {/* Quantity Controls */}
                        <div className="flex items-center space-x-2">
                          <Button
                            size="sm"
                            variant="outline"
                            onClick={() => handleQuantityChange(item.cartItemId, item.quantity - 1)}
                            disabled={loading}
                          >
                            <Minus className="h-3 w-3" />
                          </Button>
                          <Input
                            type="number"
                            min="1"
                            max="10"
                            value={item.quantity}
                            onChange={(e) => handleQuantityChange(item.cartItemId, parseInt(e.target.value) || 1)}
                            className="w-16 text-center"
                            disabled={loading}
                          />
                          <Button
                            size="sm"
                            variant="outline"
                            onClick={() => handleQuantityChange(item.cartItemId, item.quantity + 1)}
                            disabled={loading}
                          >
                            <Plus className="h-3 w-3" />
                          </Button>
                        </div>

                        {/* Price */}
                        <div className="text-right min-w-[100px]">
                          <div className="font-medium">
                            {formatCurrency(item.totalPrice)}
                          </div>
                          <div className="text-sm text-gray-500">
                            {formatCurrency(item.ticketPrice)} each
                          </div>
                        </div>

                        {/* Remove Button */}
                        <Button
                          size="sm"
                          variant="ghost"
                          onClick={() => handleRemoveItem(item.cartItemId)}
                          disabled={loading}
                          className="text-red-600 hover:text-red-800"
                        >
                          <Trash2 className="h-4 w-4" />
                        </Button>
                      </div>
                    </div>
                  </div>
                ))}
              </CardContent>
            </Card>
          </div>

          {/* Order Summary */}
          <div className="space-y-6">
            <Card>
              <CardHeader>
                <CardTitle>Order Summary</CardTitle>
              </CardHeader>
              <CardContent className="space-y-4">
                <div className="space-y-2">
                  {cartItems.map((item) => (
                    <div key={item.cartItemId} className="flex justify-between text-sm">
                      <span className="text-gray-600">
                        {item.event?.eventName || `Event ${item.eventId}`} x{item.quantity}
                      </span>
                      <span>{formatCurrency(item.totalPrice)}</span>
                    </div>
                  ))}
                </div>

                <Separator />

                <div className="flex justify-between font-semibold text-lg">
                  <span>Total</span>
                  <span>{formatCurrency(getTotalPrice())}</span>
                </div>
              </CardContent>
            </Card>

            <Card>
              <CardHeader>
                <CardTitle className="flex items-center space-x-2">
                  <CreditCard className="h-5 w-5" />
                  <span>Payment Options</span>
                </CardTitle>
                <CardDescription>
                  Choose how you'd like to pay for your tickets
                </CardDescription>
              </CardHeader>
              <CardContent className="space-y-4">
                <Select value={paymentOption} onValueChange={setPaymentOption}>
                  <SelectTrigger>
                    <SelectValue placeholder="Select payment method" />
                  </SelectTrigger>
                  <SelectContent>
                    {paymentOptions.map((option) => (
                      <SelectItem key={option.value} value={option.value}>
                        {option.label}
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>

                <Button
                  className="w-full"
                  onClick={handleCheckout}
                  disabled={checkoutLoading || !paymentOption}
                  size="lg"
                >
                  {checkoutLoading ? 'Processing...' : `Checkout ${formatCurrency(getTotalPrice())}`}
                </Button>

                <div className="text-xs text-gray-500 text-center">
                  By proceeding to checkout, you agree to our terms and conditions.
                </div>
              </CardContent>
            </Card>

            {/* Quick Actions */}
            <Card>
              <CardHeader>
                <CardTitle>Need Help?</CardTitle>
              </CardHeader>
              <CardContent className="space-y-2">
                <Button variant="outline" className="w-full" asChild>
                  <Link to="/events">Add More Events</Link>
                </Button>
                <Button variant="outline" className="w-full" asChild>
                  <Link to="/bookings">View Past Bookings</Link>
                </Button>
              </CardContent>
            </Card>

            {/* Debug Section - Remove in production */}
            <Card className="border-yellow-200 bg-yellow-50">
              <CardHeader>
                <CardTitle className="text-yellow-800">🔧 Debug Info</CardTitle>
                <CardDescription className="text-yellow-700">
                  Development mode - Backend status
                </CardDescription>
              </CardHeader>
              <CardContent className="space-y-2">
                <p className="text-sm text-yellow-800">
                  Backend URL: {import.meta.env.VITE_API_URL || 'http://localhost:8080/SEMS'}
                </p>
                <p className="text-sm text-yellow-800">
                  Using mock data: Cart items are stored in localStorage
                </p>
                <Button 
                  variant="outline" 
                  size="sm" 
                  onClick={clearMockCart}
                  className="w-full text-yellow-800 border-yellow-300"
                >
                  Clear Mock Cart
                </Button>
              </CardContent>
            </Card>
          </div>
        </div>
      )}
    </div>
  );
}