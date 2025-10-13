import * as React from "react";
import { X } from "lucide-react";
import { cn } from "./utils";

interface SheetContextType {
  isOpen: boolean;
  setIsOpen: (open: boolean) => void;
}

const SheetContext = React.createContext<SheetContextType | null>(null);

function Sheet({ 
  children, 
  open, 
  onOpenChange 
}: { 
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
    <SheetContext.Provider value={{ isOpen, setIsOpen: handleOpenChange }}>
      {children}
    </SheetContext.Provider>
  );
}

function SheetTrigger({ children, className }: { children: React.ReactNode; className?: string; }) {
  const context = React.useContext(SheetContext);
  if (!context) throw new Error("SheetTrigger must be used within Sheet");

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

function SheetClose({ children, className }: { children?: React.ReactNode; className?: string; }) {
  const context = React.useContext(SheetContext);
  if (!context) throw new Error("SheetClose must be used within Sheet");

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

function SheetPortal({ children }: { children: React.ReactNode }) {
  return <>{children}</>;
}

function SheetOverlay({ className }: { className?: string }) {
  return (
    <div
      className={cn("fixed inset-0 z-50 bg-black/50", className)}
    />
  );
}

function SheetContent({
  className,
  children,
  side = "right",
  ...props
}: React.ComponentProps<"div"> & {
  side?: "top" | "right" | "bottom" | "left";
}) {
  const context = React.useContext(SheetContext);
  if (!context) throw new Error("SheetContent must be used within Sheet");

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

  const sideClasses = {
    right: "inset-y-0 right-0 h-full w-3/4 border-l sm:max-w-sm",
    left: "inset-y-0 left-0 h-full w-3/4 border-r sm:max-w-sm",
    top: "inset-x-0 top-0 h-auto border-b",
    bottom: "inset-x-0 bottom-0 h-auto border-t",
  };

  return (
    <SheetPortal>
      <SheetOverlay />
      <div
        className={cn(
          "fixed z-50 flex flex-col gap-4 bg-white shadow-lg transition-all duration-300 ease-in-out",
          sideClasses[side],
          className
        )}
        {...props}
      >
        {children}
        <SheetClose className="absolute top-4 right-4 rounded-sm opacity-70 hover:opacity-100 focus:outline-none focus:ring-2 focus:ring-blue-500">
          <X className="h-4 w-4" />
          <span className="sr-only">Close</span>
        </SheetClose>
      </div>
    </SheetPortal>
  );
}

function SheetHeader({ className, ...props }: React.ComponentProps<"div">) {
  return (
    <div
      className={cn("flex flex-col space-y-1.5 p-4", className)}
      {...props}
    />
  );
}

function SheetFooter({ className, ...props }: React.ComponentProps<"div">) {
  return (
    <div
      className={cn("mt-auto flex flex-col gap-2 p-4", className)}
      {...props}
    />
  );
}

function SheetTitle({ className, ...props }: React.ComponentProps<"h2">) {
  return (
    <h2
      className={cn("text-lg font-semibold text-gray-900", className)}
      {...props}
    />
  );
}

function SheetDescription({ className, ...props }: React.ComponentProps<"p">) {
  return (
    <p
      className={cn("text-sm text-gray-600", className)}
      {...props}
    />
  );
}

export {
  Sheet,
  SheetTrigger,
  SheetClose,
  SheetContent,
  SheetHeader,
  SheetFooter,
  SheetTitle,
  SheetDescription,
};