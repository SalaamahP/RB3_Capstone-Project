import React from 'react';

// Simple toast notification system
interface ToastState {
  id: string;
  message: string;
  type: 'success' | 'error' | 'info';
  duration?: number;
}

let toasts: ToastState[] = [];
let listeners: Array<(toasts: ToastState[]) => void> = [];

const addToast = (message: string, type: 'success' | 'error' | 'info' = 'info', duration = 5000) => {
  const id = Math.random().toString(36).substring(7);
  const toast: ToastState = { id, message, type, duration };
  
  toasts = [...toasts, toast];
  listeners.forEach(listener => listener(toasts));
  
  setTimeout(() => {
    toasts = toasts.filter(t => t.id !== id);
    listeners.forEach(listener => listener(toasts));
  }, duration);
  
  return id;
};

export const toast = {
  success: (message: string) => addToast(message, 'success'),
  error: (message: string) => addToast(message, 'error'),
  info: (message: string) => addToast(message, 'info'),
};

export function Toaster({ position = 'top-right' }: { position?: string }) {
  const [currentToasts, setCurrentToasts] = React.useState<ToastState[]>([]);

  React.useEffect(() => {
    const listener = (newToasts: ToastState[]) => {
      setCurrentToasts(newToasts);
    };
    
    listeners.push(listener);
    
    return () => {
      listeners = listeners.filter(l => l !== listener);
    };
  }, []);

  const positionClasses = {
    'top-right': 'fixed top-4 right-4 z-50',
    'top-left': 'fixed top-4 left-4 z-50',
    'bottom-right': 'fixed bottom-4 right-4 z-50',
    'bottom-left': 'fixed bottom-4 left-4 z-50',
  };

  return (
    <div className={positionClasses[position as keyof typeof positionClasses] || positionClasses['top-right']}>
      {currentToasts.map((toast) => (
        <div
          key={toast.id}
          className={`mb-2 px-4 py-2 rounded-md shadow-lg text-white transition-all duration-300 ${
            toast.type === 'success' ? 'bg-green-600' :
            toast.type === 'error' ? 'bg-red-600' :
            'bg-blue-600'
          }`}
        >
          {toast.message}
        </div>
      ))}
    </div>
  );
}