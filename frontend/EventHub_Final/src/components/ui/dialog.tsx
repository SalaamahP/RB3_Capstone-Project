import * as React from "react";
import { X } from "lucide-react";
import { cn } from "./utils";

interface DialogContextType {
  isOpen: boolean;
  setIsOpen: (open: boolean) => void;
}

const DialogContext = React.createContext<DialogContextType | null>(null);

function Dialog({ children, open, onOpenChange }: { 
  children: React.ReactNode; 
  open?: boolean; 
  onOpenChange?: (open: boolean) => void; 
}) {
  const [isOpen, setIsOpen] = React.useState(open || false);

  React.useEffect(() => {
    if (open !== undefined) {
      setIsOpen(open);
    }
  }, [open]);

  const handleOpenChange = (newOpen: boolean) => {
    setIsOpen(newOpen);
    onOpenChange?.(newOpen);
  };

  return (
    <DialogContext.Provider value={{ isOpen, setIsOpen: handleOpenChange }}>
      {children}
    </DialogContext.Provider>
  );
}

function DialogTrigger({ children, className }: { children: React.ReactNode; className?: string; }) {
  const context = React.useContext(DialogContext);
  if (!context) throw new Error("DialogTrigger must be used within Dialog");

  if (React.isValidElement(children)) {
    return React.cloneElement(children, {
      onClick: () => context.setIsOpen(true),
    });
  }

  return (
    <button
      type="button"
      className={cn("cursor-pointer", className)}
      onClick={() => context.setIsOpen(true)}
    >
      {children}
    </button>
  );
}

function DialogPortal({ children }: { children: React.ReactNode }) {
  return <>{children}</>;
}

function DialogClose({ children, className }: { children?: React.ReactNode; className?: string; }) {
  const context = React.useContext(DialogContext);
  if (!context) throw new Error("DialogClose must be used within Dialog");

  return (
    <button
      type="button"
      className={cn("cursor-pointer", className)}
      onClick={() => context.setIsOpen(false)}
    >
      {children}
    </button>
  );
}

function DialogOverlay({ className }: { className?: string }) {
  return (
    <div
      className={cn(
        "fixed inset-0 z-50 bg-black/50",
        className
      )}
    />
  );
}

function DialogContent({ 
  className, 
  children, 
  ...props 
}: React.ComponentProps<"div">) {
  const context = React.useContext(DialogContext);
  if (!context) throw new Error("DialogContent must be used within Dialog");

  React.useEffect(() => {
    const handleEscape = (e: KeyboardEvent) => {
      if (e.key === 'Escape') {
        context.setIsOpen(false);
      }
    };

    if (context.isOpen) {
      document.addEventListener('keydown', handleEscape);
      document.body.style.overflow = 'hidden';
      return () => {
        document.removeEventListener('keydown', handleEscape);
        document.body.style.overflow = 'unset';
      };
    }
  }, [context.isOpen]);

  if (!context.isOpen) return null;

  return (
    <DialogPortal>
      <DialogOverlay />
      <div
        className={cn(
          "fixed top-1/2 left-1/2 z-50 grid w-full max-w-lg transform -translate-x-1/2 -translate-y-1/2 gap-4 rounded-lg border border-gray-200 bg-white p-6 shadow-lg",
          className
        )}
        {...props}
      >
        {children}
        <DialogClose className="absolute top-4 right-4 rounded-sm opacity-70 hover:opacity-100 focus:outline-none focus:ring-2 focus:ring-blue-500">
          <X className="h-4 w-4" />
          <span className="sr-only">Close</span>
        </DialogClose>
      </div>
    </DialogPortal>
  );
}

function DialogHeader({ className, ...props }: React.ComponentProps<"div">) {
  return (
    <div
      className={cn("flex flex-col space-y-1.5 text-center sm:text-left", className)}
      {...props}
    />
  );
}

function DialogFooter({ className, ...props }: React.ComponentProps<"div">) {
  return (
    <div
      className={cn(
        "flex flex-col-reverse sm:flex-row sm:justify-end sm:space-x-2",
        className,
      )}
      {...props}
    />
  );
}

function DialogTitle({ className, ...props }: React.ComponentProps<"h2">) {
  return (
    <h2
      className={cn("text-lg font-semibold leading-none tracking-tight", className)}
      {...props}
    />
  );
}

function DialogDescription({ className, ...props }: React.ComponentProps<"p">) {
  return (
    <p
      className={cn("text-sm text-gray-600", className)}
      {...props}
    />
  );
}

export {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogOverlay,
  DialogPortal,
  DialogTitle,
  DialogTrigger,
};