import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import apiService, { User } from '../services/api';
import { toast } from '../components/ui/sonner';

interface AuthContextType {
  user: User | null;
  loading: boolean;
  login: (userid: string, password: string) => Promise<void>;
  register: (userData: Omit<User, 'id' | 'roles'> & { password: string }) => Promise<void>;
  logout: () => void;
  hasRole: (roleName: string) => boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function useAuth() {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}

interface AuthProviderProps {
  children: ReactNode;
}

export function AuthProvider({ children }: AuthProviderProps) {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('authToken');
    if (token) {
      // Try to get current user
      apiService.getCurrentUser()
        .then(setUser)
        .catch(() => {
          // Token might be invalid
          localStorage.removeItem('authToken');
        })
        .finally(() => setLoading(false));
    } else {
      setLoading(false);
    }
  }, []);

  const login = async (userid: string, password: string) => {
    try {
      const { user: userData, token } = await apiService.login(userid, password);
      localStorage.setItem('authToken', token);
      setUser(userData);
      toast.success('Successfully logged in!');
    } catch (error) {
      toast.error(error instanceof Error ? error.message : 'Login failed');
      throw error;
    }
  };

  const register = async (userData: Omit<User, 'id' | 'roles'> & { password: string }) => {
    try {
      const newUser = await apiService.register(userData);
      toast.success('Registration successful! Please log in.');
    } catch (error) {
      toast.error(error instanceof Error ? error.message : 'Registration failed');
      throw error;
    }
  };

  const logout = () => {
    localStorage.removeItem('authToken');
    setUser(null);
    toast.success('Successfully logged out');
  };

  const hasRole = (roleName: string): boolean => {
    return user?.roles?.some(role => role.roleName === roleName) || false;
  };

  const value = {
    user,
    loading,
    login,
    register,
    logout,
    hasRole,
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
}