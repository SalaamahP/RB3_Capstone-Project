import * as React from "react";
import { cn } from "./utils";

interface PopoverContextType {
  isOpen: boolean;
  setIsOpen: (open: boolean) => void;
}

const PopoverContext = React.createContext<PopoverContextType | null>(null);

function Popover({ children }: { children: React.ReactNode }) {
  const [isOpen, setIsOpen] = React.useState(false);

  return (
    <PopoverContext.Provider value={{ isOpen, setIsOpen }}>
      <div className="relative">
        {children}
      </div>
    </PopoverContext.Provider>
  );
}

interface PopoverTriggerProps {
  children: React.ReactNode;
  className?: string;
  asChild?: boolean;
}

function PopoverTrigger({ children, className, asChild }: PopoverTriggerProps) {
  const context = React.useContext(PopoverContext);
  if (!context) throw new Error("PopoverTrigger must be used within Popover");

  if (asChild && React.isValidElement(children)) {
    return React.cloneElement(children, {
      onClick: () => context.setIsOpen(!context.isOpen),
    });
  }

  return (
    <button
      type="button"
      className={cn("cursor-pointer", className)}
      onClick={() => context.setIsOpen(!context.isOpen)}
    >
      {children}
    </button>
  );
}

interface PopoverContentProps {
  children: React.ReactNode;
  className?: string;
  align?: "start" | "center" | "end";
}

function PopoverContent({ children, className, align = "center" }: PopoverContentProps) {
  const context = React.useContext(PopoverContext);
  if (!context) throw new Error("PopoverContent must be used within Popover");

  React.useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      const target = event.target as Element;
      if (!target.closest('[data-popover-content]')) {
        context.setIsOpen(false);
      }
    };

    if (context.isOpen) {
      document.addEventListener('mousedown', handleClickOutside);
      return () => document.removeEventListener('mousedown', handleClickOutside);
    }
  }, [context.isOpen]);

  if (!context.isOpen) return null;

  const alignmentClasses = {
    start: "left-0",
    center: "left-1/2 transform -translate-x-1/2",
    end: "right-0",
  };

  return (
    <div
      data-popover-content
      className={cn(
        "absolute z-50 mt-1 w-72 rounded-md border border-gray-200 bg-white p-4 shadow-md",
        alignmentClasses[align],
        className
      )}
    >
      {children}
    </div>
  );
}

function PopoverAnchor({ children }: { children: React.ReactNode }) {
  return <>{children}</>;
}

export { Popover, PopoverTrigger, PopoverContent, PopoverAnchor };