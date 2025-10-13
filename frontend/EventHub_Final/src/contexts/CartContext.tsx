import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import apiService, { Cart, CartItem } from '../services/api';
import { useAuth } from './AuthContext';
import { toast } from '../components/ui/sonner';

interface CartContextType {
  cart: Cart | null;
  loading: boolean;
  addToCart: (eventId: number, quantity: number) => Promise<void>;
  updateCartItem: (cartItemId: number, quantity: number) => Promise<void>;
  removeFromCart: (cartItemId: number) => Promise<void>;
  checkout: (paymentOption: string) => Promise<void>;
  refreshCart: () => Promise<void>;
  getTotalItems: () => number;
  getTotalPrice: () => number;
}

const CartContext = createContext<CartContextType | undefined>(undefined);

export function useCart() {
  const context = useContext(CartContext);
  if (context === undefined) {
    throw new Error('useCart must be used within a CartProvider');
  }
  return context;
}

interface CartProviderProps {
  children: ReactNode;
}

export function CartProvider({ children }: CartProviderProps) {
  const [cart, setCart] = useState<Cart | null>(null);
  const [loading, setLoading] = useState(false);
  const { user } = useAuth();

  const refreshCart = async () => {
    if (!user) return;
    
    try {
      setLoading(true);
      const cartData = await apiService.getCart();
      setCart(cartData);
    } catch (error) {
      // Cart might not exist yet, that's okay
      setCart(null);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (user) {
      refreshCart();
    } else {
      setCart(null);
    }
  }, [user]);

  const addToCart = async (eventId: number, quantity: number) => {
    try {
      setLoading(true);
      await apiService.addToCart(eventId, quantity);
      await refreshCart();
      toast.success('Item added to cart!');
    } catch (error) {
      toast.error(error instanceof Error ? error.message : 'Failed to add item to cart');
      throw error;
    } finally {
      setLoading(false);
    }
  };

  const updateCartItem = async (cartItemId: number, quantity: number) => {
    try {
      setLoading(true);
      await apiService.updateCartItem(cartItemId, quantity);
      await refreshCart();
      toast.success('Cart updated!');
    } catch (error) {
      toast.error(error instanceof Error ? error.message : 'Failed to update cart');
      throw error;
    } finally {
      setLoading(false);
    }
  };

  const removeFromCart = async (cartItemId: number) => {
    try {
      setLoading(true);
      await apiService.removeFromCart(cartItemId);
      await refreshCart();
      toast.success('Item removed from cart!');
    } catch (error) {
      toast.error(error instanceof Error ? error.message : 'Failed to remove item');
      throw error;
    } finally {
      setLoading(false);
    }
  };

  const checkout = async (paymentOption: string) => {
    try {
      setLoading(true);
      await apiService.checkout(paymentOption);
      await refreshCart();
      toast.success('Checkout successful!');
    } catch (error) {
      toast.error(error instanceof Error ? error.message : 'Checkout failed');
      throw error;
    } finally {
      setLoading(false);
    }
  };

  const getTotalItems = (): number => {
    return cart?.items?.reduce((total, item) => total + item.quantity, 0) || 0;
  };

  const getTotalPrice = (): number => {
    return cart?.items?.reduce((total, item) => total + item.totalPrice, 0) || 0;
  };

  const value = {
    cart,
    loading,
    addToCart,
    updateCartItem,
    removeFromCart,
    checkout,
    refreshCart,
    getTotalItems,
    getTotalPrice,
  };

  return (
    <CartContext.Provider value={value}>
      {children}
    </CartContext.Provider>
  );
}