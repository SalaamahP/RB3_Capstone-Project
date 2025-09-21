// API service layer for SpringBoot backend integration
const API_BASE_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8001/SEMS';

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
    // Mock response for demo purposes
    if (userid === 'admin' && password === 'admin123') {
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

    // ✅ ACTUAL API CALL — FIXED: Added credentials: 'include'
    const response = await fetch(`${API_BASE_URL}${API_PATHS.AUTH}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ userid, password }),
      credentials: 'include', // ✅ Required for CORS with credentials
    });
    return this.handleResponse(response);
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
        eventName: 'Tech Conference 2024',
        eventDescription: 'Annual technology conference featuring latest innovations',
        eventCategory: 'Conference',
        dateTime: '2024-12-15T10:00:00',
        venueId: 1,
        ticketPrice: 50.00
      },
      {
        eventId: 2,
        eventName: 'Student Art Exhibition',
        eventDescription: 'Showcase of student artwork from various departments',
        eventCategory: 'Cultural',
        dateTime: '2024-12-20T14:00:00',
        venueId: 2,
        ticketPrice: 15.00
      },
      {
        eventId: 3,
        eventName: 'Basketball Championship',
        eventDescription: 'Inter-university basketball championship finals',
        eventCategory: 'Sports',
        dateTime: '2024-12-22T18:00:00',
        venueId: 3,
        ticketPrice: 25.00
      }
    ];

    try {
      const response = await fetch(`${API_BASE_URL}${API_PATHS.EVENTS}`, {
        headers: this.getAuthHeaders(),
      });
      return this.handleResponse(response);
    } catch (error) {
      return mockEvents;
    }
  }

  async getEvent(id: number): Promise<Event> {
    const mockEvents: Event[] = [
      {
        eventId: 1,
        eventName: 'Tech Conference 2024',
        eventDescription: 'Annual technology conference featuring latest innovations in software development, AI, and emerging technologies. Join industry leaders and experts for inspiring talks and networking.',
        eventCategory: 'Conference',
        dateTime: '2024-12-15T10:00:00',
        venueId: 1,
        ticketPrice: 50.00
      },
      {
        eventId: 2,
        eventName: 'Student Art Exhibition',
        eventDescription: 'Showcase of student artwork from various departments including digital art, sculpture, painting, and mixed media. Celebrating creativity and artistic expression.',
        eventCategory: 'Cultural',
        dateTime: '2024-12-20T14:00:00',
        venueId: 2,
        ticketPrice: 15.00
      },
      {
        eventId: 3,
        eventName: 'Basketball Championship',
        eventDescription: 'Inter-university basketball championship finals. Watch the best teams compete for the title in an exciting game.',
        eventCategory: 'Sports',
        dateTime: '2024-12-22T18:00:00',
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
      const event = mockEvents.find(e => e.eventId === id);
      if (event) return event;
      throw new Error('Event not found');
    }
  }

  async createEvent(event: Omit<Event, 'eventId'>): Promise<Event> {
    const response = await fetch(`${API_BASE_URL}${API_PATHS.EVENTS}`, {
      method: 'POST',
      headers: this.getAuthHeaders(),
      body: JSON.stringify(event),
    });
    return this.handleResponse(response);
  }

  async updateEvent(id: number, event: Partial<Event>): Promise<Event> {
    const response = await fetch(`${API_BASE_URL}${API_PATHS.EVENTS}`, {
      method: 'PUT',
      headers: this.getAuthHeaders(),
      body: JSON.stringify({ ...event, eventId: id }),
    });
    return this.handleResponse(response);
  }

  async deleteEvent(id: number): Promise<void> {
    const response = await fetch(`${API_BASE_URL}${API_PATHS.EVENTS}/${id}`, {
      method: 'DELETE',
      headers: this.getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error(`Failed to delete event: ${response.status}`);
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
      const response = await fetch(`${API_BASE_URL}${API_PATHS.VENUES}`, {
        headers: this.getAuthHeaders(),
      });
      return this.handleResponse(response);
    } catch (error) {
      return mockVenues;
    }
  }

  async createVenue(venue: Omit<Venue, 'venueId'>): Promise<Venue> {
    const response = await fetch(`${API_BASE_URL}${API_PATHS.VENUES}`, {
      method: 'POST',
      headers: this.getAuthHeaders(),
      body: JSON.stringify(venue),
    });
    return this.handleResponse(response);
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
      const response = await fetch(`${API_BASE_URL}${API_PATHS.CART}`, {
        headers: this.getAuthHeaders(),
      });
      return this.handleResponse(response);
    } catch (error) {
      return mockCart;
    }
  }

  async addToCart(eventId: number, quantity: number): Promise<CartItem> {
    const mockCartItem: CartItem = {
      cartItemId: Date.now(),
      cartId: 1,
      eventId: eventId,
      quantity: quantity,
      ticketPrice: 50.00,
      totalPrice: 50.00 * quantity
    };

    try {
      const response = await fetch(`${API_BASE_URL}${API_PATHS.CART_ITEMS}`, {
        method: 'POST',
        headers: this.getAuthHeaders(),
        body: JSON.stringify({ eventId, quantity }),
      });
      return this.handleResponse(response);
    } catch (error) {
      return mockCartItem;
    }
  }

  async updateCartItem(cartItemId: number, quantity: number): Promise<CartItem> {
    const response = await fetch(`${API_BASE_URL}${API_PATHS.CART_ITEMS}/${cartItemId}`, {
      method: 'PUT',
      headers: this.getAuthHeaders(),
      body: JSON.stringify({ quantity }),
    });
    return this.handleResponse(response);
  }

  async removeFromCart(cartItemId: number): Promise<void> {
    const response = await fetch(`${API_BASE_URL}${API_PATHS.CART_ITEMS}/${cartItemId}`, {
      method: 'DELETE',
      headers: this.getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error(`Failed to remove item from cart: ${response.status}`);
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