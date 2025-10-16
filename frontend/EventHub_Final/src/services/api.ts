// Extend ImportMeta to include 'env' for Vite
interface ImportMetaEnv {
  VITE_API_URL?: string;
  DEV?: boolean;
  [key: string]: any;
}

declare global {
  interface ImportMeta {
    readonly env: ImportMetaEnv;
  }
}

// API service layer for SpringBoot backend integration
const API_BASE_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8080/SEMS';

if (import.meta.env.DEV) {
  console.log('✅ API_BASE_URL:', API_BASE_URL);
}

// Centralized API paths for easy maintenance
const API_PATHS = {
  AUTH: '/api/auth',
  EVENTS: '/api/event',
  VENUES: '/venue',
  CART: '/cart',
  CART_ITEMS: '/cartItem',
  BOOKINGS: '/bookings',
  RSVP: '/api/rsvp',
  NOTIFICATIONS: '/notifications',
  ADMIN: '/api/admin',
  ORGANIZER: '/api/organizer'
};

// ✅ Interfaces
export interface User {
  id: number;
  userid: string;
  name: string;
  surname: string;
  email: string;
  phone: string;
  roles: Role[];
}

export interface Role {
  roleName: string;
}

export interface Event {
  eventId: number;
  eventName: string;
  eventDescription: string;
  eventCategory: string;
  dateTime: string;
  venueId: number;
  venue?: Venue;
  ticketPrice: number;
  availableTickets?: number;
}

export interface Venue {
  venueId: number;
  venueName: string;
  venueLocation: string;
  capacity: number;
}

export interface Cart {
  cartId: number;
  id: number;
  paymentOption: string;
  bookingDate: string;
  items: CartItem[];
}

export interface CartItem {
  cartItemId: number;
  cartId: number;
  eventId: number;
  event?: Event;
  quantity: number;
  ticketPrice: number;
  totalPrice: number;
}

export interface TicketBooking {
  bookingId: number;
  id: number;
  eventId: number;
  event?: Event;
  quantity: number;
  numberOfTickets: number;
  total: number;
  bookingDate: string;
  paymentSelection: string;
  status: string;
}

export interface RSVP {
  rsvpId: number;
  studentNumber: string;
  eventId: number;
  event?: Event;
  status: string;
}

export interface Notification {
  notificationId: number;
  message: string;
  id: number;
  eventId?: number;
  timestamp: string;
  read?: boolean;
}

class ApiService {
  private getAuthHeaders() {
    const token = localStorage.getItem('authToken');
    return {
      'Content-Type': 'application/json',
      ...(token && { Authorization: `Bearer ${token}` }),
    };
  }

  private async handleResponse(response: Response) {
    if (!response.ok) {
      const error = await response.text();
      throw new Error(error || `HTTP error! status: ${response.status}`);
    }
    return response.json();
  }

  // Authentication
  async login(userid: string, password: string): Promise<{ user: User; token: string }> {
    console.log('🔄 Attempting login for:', userid);
    
    // Mock response for demo purposes
    if (userid === 'admin' && password === 'admin123') {
      console.log('✅ Using mock admin credentials');
      return {
        user: {
          id: 1,
          userid: 'admin',
          name: 'Admin',
          surname: 'User',
          email: 'admin@university.edu',
          phone: '+1234567890',
          roles: [{ roleName: 'ADMIN' }]
        },
        token: 'mock-admin-token'
      };
    } else if (userid === 'student001' && password === 'password') {
      console.log('✅ Using mock student credentials');
      return {
        user: {
          id: 2,
          userid: 'student001',
          name: 'John',
          surname: 'Doe',
          email: 'john.doe@university.edu',
          phone: '+1234567891',
          roles: [{ roleName: 'STUDENT' }]
        },
        token: 'mock-student-token'
      };
    }

    // Try actual API call for other credentials
    try {
      console.log('🔄 Attempting real API login:', `${API_BASE_URL}${API_PATHS.AUTH}/login`);
      const response = await fetch(`${API_BASE_URL}${API_PATHS.AUTH}/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userid, password }),
        credentials: 'include', // ✅ Required for CORS with credentials
      });
      const result = await this.handleResponse(response);
      console.log('✅ Successfully logged in via API');
      return result;
    } catch (error) {
      console.warn('⚠️ Backend not available for login:', error);
      throw new Error('Invalid credentials. Try: admin/admin123 or student001/password (Demo mode - backend not running)');
    }
  }

  async register(userData: Omit<User, 'id' | 'roles'> & { password: string }): Promise<User> {
    // Mock implementation
    const newUser: User = {
      id: Date.now(),
      userid: userData.userid,
      name: userData.name,
      surname: userData.surname,
      email: userData.email,
      phone: userData.phone,
      roles: [{ roleName: 'STUDENT' }]
    };

    try {
      const response = await fetch(`${API_BASE_URL}${API_PATHS.AUTH}/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(userData),
      });
      return this.handleResponse(response);
    } catch (error) {
      return newUser;
    }
  }

  async getCurrentUser(): Promise<User> {
    const token = localStorage.getItem('authToken');

    // Mock responses
    if (token === 'mock-admin-token') {
      return {
        id: 1,
        userid: 'admin',
        name: 'Admin',
        surname: 'User',
        email: 'admin@university.edu',
        phone: '+1234567890',
        roles: [{ roleName: 'ADMIN' }]
      };
    } else if (token === 'mock-student-token') {
      return {
        id: 2,
        userid: 'student001',
        name: 'John',
        surname: 'Doe',
        email: 'john.doe@university.edu',
        phone: '+1234567891',
        roles: [{ roleName: 'STUDENT' }]
      };
    }

    // Actual API call
    const response = await fetch(`${API_BASE_URL}${API_PATHS.AUTH}/me`, {
      headers: this.getAuthHeaders(),
    });
    return this.handleResponse(response);
  }

  // Events
  async getEvents(): Promise<Event[]> {
    const mockEvents: Event[] = [
      {
        eventId: 1,
        eventName: 'Tech Conference 2025',
        eventDescription: 'Annual technology conference featuring latest innovations',
        eventCategory: 'Conference',
        dateTime: '2025-11-15T10:00:00',
        venueId: 1,
        ticketPrice: 50.00
      },
      {
        eventId: 2,
        eventName: 'Student Art Exhibition',
        eventDescription: 'Showcase of student artwork from various departments',
        eventCategory: 'Cultural',
        dateTime: '2025-11-20T14:00:00',
        venueId: 2,
        ticketPrice: 15.00
      },
      {
        eventId: 3,
        eventName: 'Basketball Championship',
        eventDescription: 'Inter-university basketball championship finals',
        eventCategory: 'Sports',
        dateTime: '2025-11-22T18:00:00',
        venueId: 3,
        ticketPrice: 25.00
      }
    ];

    try {
      console.log('🔄 Attempting to get events from API:', `${API_BASE_URL}${API_PATHS.EVENTS}`);
      const response = await fetch(`${API_BASE_URL}${API_PATHS.EVENTS}`, {
        headers: this.getAuthHeaders(),
      });
      const result = await this.handleResponse(response);
      console.log('✅ Successfully retrieved events from API:', result);
      return result;
    } catch (error) {
      console.warn('⚠️ Backend not available, using mock events:', error);
      
      // Include any custom events created and stored in localStorage
      const customEvents = JSON.parse(localStorage.getItem('mockEvents') || '[]');
      const allEvents = [...mockEvents, ...customEvents];
      
      console.log('📝 Mock events (including custom):', allEvents);
      return allEvents;
    }
  }

  async getEvent(id: number): Promise<Event> {
    const mockEvents: Event[] = [
      {
        eventId: 1,
        eventName: 'Tech Conference 2025',
        eventDescription: 'Annual technology conference featuring latest innovations in software development, AI, and emerging technologies. Join industry leaders and experts for inspiring talks and networking.',
        eventCategory: 'Conference',
        dateTime: '2025-11-15T10:00:00',
        venueId: 1,
        ticketPrice: 50.00
      },
      {
        eventId: 2,
        eventName: 'Student Art Exhibition',
        eventDescription: 'Showcase of student artwork from various departments including digital art, sculpture, painting, and mixed media. Celebrating creativity and artistic expression.',
        eventCategory: 'Cultural',
        dateTime: '2025-11-20T14:00:00',
        venueId: 2,
        ticketPrice: 15.00
      },
      {
        eventId: 3,
        eventName: 'Basketball Championship',
        eventDescription: 'Inter-university basketball championship finals. Watch the best teams compete for the title in an exciting game.',
        eventCategory: 'Sports',
        dateTime: '2025-11-22T18:00:00',
        venueId: 3,
        ticketPrice: 25.00
      }
    ];

    try {
      const response = await fetch(`${API_BASE_URL}${API_PATHS.EVENTS}/${id}`, {
        headers: this.getAuthHeaders(),
      });
      return this.handleResponse(response);
    } catch (error) {
      // Check default mock events first
      const event = mockEvents.find(e => e.eventId === id);
      if (event) return event;
      
      // Check custom events in localStorage
      const customEvents = JSON.parse(localStorage.getItem('mockEvents') || '[]');
      const customEvent = customEvents.find((e: Event) => e.eventId === id);
      if (customEvent) return customEvent;
      
      throw new Error('Event not found');
    }
  }

  async createEvent(event: Omit<Event, 'eventId'>): Promise<Event> {
    const mockEvent: Event = {
      eventId: Date.now(),
      ...event
    };

    try {
      console.log('🔄 Attempting to create event via API:', event);
      const response = await fetch(`${API_BASE_URL}${API_PATHS.EVENTS}`, {
        method: 'POST',
        headers: this.getAuthHeaders(),
        body: JSON.stringify(event),
      });
      const result = await this.handleResponse(response);
      console.log('✅ Successfully created event via API:', result);
      return result;
    } catch (error) {
      console.warn('⚠️ Backend not available, using mock event creation:', error);
      console.log('📝 Mock event created:', mockEvent);
      
      // Store in localStorage for persistence during development
      const existingEvents = JSON.parse(localStorage.getItem('mockEvents') || '[]');
      existingEvents.push(mockEvent);
      localStorage.setItem('mockEvents', JSON.stringify(existingEvents));
      
      return mockEvent;
    }
  }

  async updateEvent(id: number, event: Partial<Event>): Promise<Event> {
    const mockUpdatedEvent: Event = {
      eventId: id,
      eventName: event.eventName || 'Updated Event',
      eventDescription: event.eventDescription || 'Updated description',
      eventCategory: event.eventCategory || 'Updated',
      dateTime: event.dateTime || new Date().toISOString(),
      venueId: event.venueId || 1,
      ticketPrice: event.ticketPrice || 0
    };

    try {
      console.log('🔄 Attempting to update event via API:', { id, event });
      const response = await fetch(`${API_BASE_URL}${API_PATHS.EVENTS}`, {
        method: 'PUT',
        headers: this.getAuthHeaders(),
        body: JSON.stringify({ ...event, eventId: id }),
      });
      const result = await this.handleResponse(response);
      console.log('✅ Successfully updated event via API:', result);
      return result;
    } catch (error) {
      console.warn('⚠️ Backend not available, returning mock updated event:', error);
      console.log('📝 Mock event update:', mockUpdatedEvent);
      throw new Error('Event updates are only available when backend is running. Demo mode active.');
    }
  }

  async deleteEvent(id: number): Promise<void> {
    try {
      console.log('🔄 Attempting to delete event via API:', id);
      const response = await fetch(`${API_BASE_URL}${API_PATHS.EVENTS}/${id}`, {
        method: 'DELETE',
        headers: this.getAuthHeaders(),
      });
      if (!response.ok) {
        throw new Error(`Failed to delete event: ${response.status}`);
      }
      console.log('✅ Successfully deleted event via API');
    } catch (error) {
      console.warn('⚠️ Backend not available, cannot delete event:', error);
      throw new Error('Event deletion is only available when backend is running. Demo mode active.');
    }
  }

  // Venues
  async getVenues(): Promise<Venue[]> {
    const mockVenues: Venue[] = [
      {
        venueId: 1,
        venueName: 'Main Auditorium',
        venueLocation: '123 University Ave, Campus Building A',
        capacity: 500
      },
      {
        venueId: 2,
        venueName: 'Art Gallery',
        venueLocation: '456 Arts Building, 2nd Floor',
        capacity: 150
      },
      {
        venueId: 3,
        venueName: 'Sports Arena',
        venueLocation: '789 Athletic Complex',
        capacity: 2000
      }
    ];

    try {
      console.log('🔄 Attempting to get venues from API:', `${API_BASE_URL}${API_PATHS.VENUES}/getAll`);
      const response = await fetch(`${API_BASE_URL}${API_PATHS.VENUES}/getAll`, {
        headers: this.getAuthHeaders(),
      });
      const result = await this.handleResponse(response);
      console.log('✅ Successfully retrieved venues from API:', result);
      return result;
    } catch (error) {
      console.warn('⚠️ Backend not available, using mock venues:', error);
      console.log('📝 Mock venues:', mockVenues);
      return mockVenues;
    }
  }

  async getVenue(id: number): Promise<Venue> {
    const mockVenues: Venue[] = [
      {
        venueId: 1,
        venueName: 'Main Auditorium',
        venueLocation: '123 University Ave, Campus Building A',
        capacity: 500
      },
      {
        venueId: 2,
        venueName: 'Art Gallery',
        venueLocation: '456 Arts Building, 2nd Floor',
        capacity: 150
      },
      {
        venueId: 3,
        venueName: 'Sports Arena',
        venueLocation: '789 Athletic Complex',
        capacity: 2000
      }
    ];

    try {
      console.log('🔄 Attempting to get venue from API:', `${API_BASE_URL}${API_PATHS.VENUES}/read/${id}`);
      const response = await fetch(`${API_BASE_URL}${API_PATHS.VENUES}/read/${id}`, {
        headers: this.getAuthHeaders(),
      });
      const result = await this.handleResponse(response);
      console.log('✅ Successfully retrieved venue from API:', result);
      return result;
    } catch (error) {
      console.warn('⚠️ Backend not available, using mock venue:', error);
      const venue = mockVenues.find(v => v.venueId === id);
      if (venue) {
        console.log('📝 Mock venue:', venue);
        return venue;
      }
      throw new Error('Venue not found');
    }
  }

  async createVenue(venue: Omit<Venue, 'venueId'>): Promise<Venue> {
    const mockVenue: Venue = {
      venueId: Date.now(),
      ...venue
    };

    try {
      console.log('🔄 Attempting to create venue via API:', venue);
      const response = await fetch(`${API_BASE_URL}${API_PATHS.VENUES}/create`, {
        method: 'POST',
        headers: this.getAuthHeaders(),
        body: JSON.stringify(venue),
      });
      const result = await this.handleResponse(response);
      console.log('✅ Successfully created venue via API:', result);
      return result;
    } catch (error) {
      console.warn('⚠️ Backend not available, returning mock venue:', error);
      console.log('📝 Mock venue created:', mockVenue);
      throw new Error('Venue creation is only available when backend is running. Demo mode active.');
    }
  }

  async updateVenue(id: number, data: { venueName: String; venueLocation: string; capacity: number}): Promise<Venue> {
    const mockUpdatedVenue: Venue = {
      venueId: id,
      venueName: data.venueName.toString(),
      venueLocation: data.venueLocation,
      capacity: data.capacity
    };

    try {
      console.log('🔄 Attempting to update venue via API:', { id, data });
      const response = await fetch(`${API_BASE_URL}${API_PATHS.VENUES}/update`, {
        method: 'PUT',
        headers: {
        ...this.getAuthHeaders(),
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({venueId: id, ...data}),
    });
      const result = await this.handleResponse(response);
      console.log('✅ Successfully updated venue via API:', result);
      return result;
    } catch (error) {
      console.warn('⚠️ Backend not available, returning mock updated venue:', error);
      console.log('📝 Mock venue update:', mockUpdatedVenue);
      throw new Error('Venue updates are only available when backend is running. Demo mode active.');
    }
  }

  async deleteVenue(id: number): Promise<boolean> {
    try {
      console.log('🔄 Attempting to delete venue via API:', id);
      const response = await fetch(`${API_BASE_URL}${API_PATHS.VENUES}/delete/${id}`, {
        method: 'DELETE',
        headers: this.getAuthHeaders(),
      });
      const result = await this.handleResponse(response);
      console.log('✅ Successfully deleted venue via API');
      return result;
    } catch (error) {
      console.warn('⚠️ Backend not available, cannot delete venue:', error);
      throw new Error('Venue deletion is only available when backend is running. Demo mode active.');
    }
  }

  // Cart
  async getCart(): Promise<Cart> {
    const mockCart: Cart = {
      cartId: 1,
      id: 1,
      paymentOption: '',
      bookingDate: new Date().toISOString(),
      items: []
    };

    try {
      console.log('🔄 Attempting to get cart from API:', `${API_BASE_URL}${API_PATHS.CART}`);
      const response = await fetch(`${API_BASE_URL}${API_PATHS.CART}`, {
        headers: this.getAuthHeaders(),
      });
      const result = await this.handleResponse(response);
      console.log('✅ Successfully retrieved cart from API:', result);
      return result;
    } catch (error) {
      console.warn('⚠️ Backend not available, using mock data from localStorage:', error);
      // Get mock cart from localStorage
      const storedCart = JSON.parse(localStorage.getItem('mockCart') || JSON.stringify(mockCart));
      console.log('📝 Mock cart from localStorage:', storedCart);
      return storedCart;
    }
  }

  async addToCart(eventId: number, quantity: number): Promise<CartItem> {
    // Get event details for accurate pricing
    let eventDetails: Event | null = null;
    try {
      eventDetails = await this.getEvent(eventId);
    } catch (error) {
      console.warn('Could not fetch event details for cart item');
    }

    const mockCartItem: CartItem = {
      cartItemId: Date.now(),
      cartId: 1,
      eventId: eventId,
      event: eventDetails || undefined,
      quantity: quantity,
      ticketPrice: eventDetails?.ticketPrice || 50.00,
      totalPrice: (eventDetails?.ticketPrice || 50.00) * quantity
    };

    try {
      console.log('🔄 Attempting to add to cart:', { eventId, quantity, url: `${API_BASE_URL}${API_PATHS.CART_ITEMS}` });
      const response = await fetch(`${API_BASE_URL}${API_PATHS.CART_ITEMS}`, {
        method: 'POST',
        headers: this.getAuthHeaders(),
        body: JSON.stringify({ eventId, quantity }),
      });
      const result = await this.handleResponse(response);
      console.log('✅ Successfully added to cart via API:', result);
      return result;
    } catch (error) {
      console.warn('⚠️ Backend not available, using mock data:', error);
      console.log('📝 Mock cart item:', mockCartItem);
      // Store in localStorage for persistence during development
      const existingCart = JSON.parse(localStorage.getItem('mockCart') || '{"items": []}');
      existingCart.items.push(mockCartItem);
      localStorage.setItem('mockCart', JSON.stringify(existingCart));
      return mockCartItem;
    }
  }

  async updateCartItem(cartItemId: number, quantity: number): Promise<CartItem> {
    try {
      console.log('🔄 Attempting to update cart item via API:', { cartItemId, quantity });
      const response = await fetch(`${API_BASE_URL}${API_PATHS.CART_ITEMS}/${cartItemId}`, {
        method: 'PUT',
        headers: this.getAuthHeaders(),
        body: JSON.stringify({ quantity }),
      });
      const result = await this.handleResponse(response);
      console.log('✅ Successfully updated cart item via API:', result);
      return result;
    } catch (error) {
      console.warn('⚠️ Backend not available, updating localStorage:', error);
      // Update in localStorage mock cart
      const existingCart = JSON.parse(localStorage.getItem('mockCart') || '{"items": []}');
      const itemIndex = existingCart.items.findIndex((item: CartItem) => item.cartItemId === cartItemId);
      if (itemIndex !== -1) {
        existingCart.items[itemIndex].quantity = quantity;
        existingCart.items[itemIndex].totalPrice = existingCart.items[itemIndex].ticketPrice * quantity;
        localStorage.setItem('mockCart', JSON.stringify(existingCart));
        console.log('📝 Updated mock cart item:', existingCart.items[itemIndex]);
        return existingCart.items[itemIndex];
      }
      throw new Error('Cart item not found');
    }
  }

  async removeFromCart(cartItemId: number): Promise<void> {
    try {
      console.log('🔄 Attempting to remove from cart via API:', cartItemId);
      const response = await fetch(`${API_BASE_URL}${API_PATHS.CART_ITEMS}/${cartItemId}`, {
        method: 'DELETE',
        headers: this.getAuthHeaders(),
      });
      if (!response.ok) {
        throw new Error(`Failed to remove item from cart: ${response.status}`);
      }
      console.log('✅ Successfully removed from cart via API');
    } catch (error) {
      console.warn('⚠️ Backend not available, removing from localStorage:', error);
      // Remove from localStorage mock cart
      const existingCart = JSON.parse(localStorage.getItem('mockCart') || '{"items": []}');
      existingCart.items = existingCart.items.filter((item: CartItem) => item.cartItemId !== cartItemId);
      localStorage.setItem('mockCart', JSON.stringify(existingCart));
      console.log('📝 Updated mock cart after removal:', existingCart);
    }
  }

  async checkout(paymentOption: string): Promise<TicketBooking[]> {
    const response = await fetch(`${API_BASE_URL}${API_PATHS.CART}/checkout`, {
      method: 'POST',
      headers: this.getAuthHeaders(),
      body: JSON.stringify({ paymentOption }),
    });
    return this.handleResponse(response);
  }

  // Bookings
  async getBookings(): Promise<TicketBooking[]> {
    const mockBookings: TicketBooking[] = [];
    try {
      const response = await fetch(`${API_BASE_URL}${API_PATHS.BOOKINGS}`, {
        headers: this.getAuthHeaders(),
      });
      return this.handleResponse(response);
    } catch (error) {
      return mockBookings;
    }
  }

  // RSVP
  async getRSVPs(): Promise<RSVP[]> {
    const mockRSVPs: RSVP[] = [];
    try {
      const response = await fetch(`${API_BASE_URL}${API_PATHS.RSVP}`, {
        headers: this.getAuthHeaders(),
      });
      return this.handleResponse(response);
    } catch (error) {
      return mockRSVPs;
    }
  }

  async createRSVP(eventId: number, studentNumber: string): Promise<RSVP> {
    const response = await fetch(`${API_BASE_URL}${API_PATHS.RSVP}`, {
      method: 'POST',
      headers: this.getAuthHeaders(),
      body: JSON.stringify({ eventId, studentNumber, status: 'PENDING' }),
    });
    return this.handleResponse(response);
  }

  async updateRSVP(rsvpId: number, status: string): Promise<RSVP> {
    const response = await fetch(`${API_BASE_URL}${API_PATHS.RSVP}/${rsvpId}`, {
      method: 'PUT',
      headers: this.getAuthHeaders(),
      body: JSON.stringify({ status }),
    });
    return this.handleResponse(response);
  }

  // Notifications
  async getNotifications(): Promise<Notification[]> {
    const mockNotifications: Notification[] = [
      {
        notificationId: 1,
        message: 'Welcome to the Student Event Management System!',
        id: 1,
        timestamp: new Date().toISOString(),
        read: false
      }
    ];

    try {
      const response = await fetch(`${API_BASE_URL}${API_PATHS.NOTIFICATIONS}`, {
        headers: this.getAuthHeaders(),
      });
      return this.handleResponse(response);
    } catch (error) {
      return mockNotifications;
    }
  }

  async markNotificationAsRead(notificationId: number): Promise<void> {
    const response = await fetch(`${API_BASE_URL}${API_PATHS.NOTIFICATIONS}/${notificationId}/read`, {
      method: 'PUT',
      headers: this.getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error(`Failed to mark notification as read: ${response.status}`);
    }
  }
}

export default new ApiService();