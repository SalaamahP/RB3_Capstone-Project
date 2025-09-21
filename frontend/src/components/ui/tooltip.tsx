import * as React from "react";
import { cn } from "./utils";

interface TooltipContextType {
  isOpen: boolean;
  setIsOpen: (open: boolean) => void;
}

const TooltipContext = React.createContext<TooltipContextType | null>(null);

function TooltipProvider({ children }: { children: React.ReactNode }) {
  return <>{children}</>;
}

function Tooltip({ children }: { children: React.ReactNode }) {
  const [isOpen, setIsOpen] = React.useState(false);

  return (
    <TooltipContext.Provider value={{ isOpen, setIsOpen }}>
      {children}
    </TooltipContext.Provider>
  );
}

interface TooltipTriggerProps {
  children: React.ReactNode;
  className?: string;
}

function TooltipTrigger({ children, className }: TooltipTriggerProps) {
  const context = React.useContext(TooltipContext);
  if (!context) throw new Error("TooltipTrigger must be used within Tooltip");

  return (
    <div
      className={cn("cursor-pointer", className)}
      onMouseEnter={() => context.setIsOpen(true)}
      onMouseLeave={() => context.setIsOpen(false)}
    >
      {children}
    </div>
  );
}

interface TooltipContentProps {
  children: React.ReactNode;
  className?: string;
}

function TooltipContent({ children, className }: TooltipContentProps) {
  const context = React.useContext(TooltipContext);
  if (!context) throw new Error("TooltipContent must be used within Tooltip");

  if (!context.isOpen) return null;

  return (
    <div
      className={cn(
        "absolute z-50 rounded-md bg-gray-900 px-3 py-1.5 text-xs text-white shadow-md",
        className
      )}
    >
      {children}
    </div>
  );
}

export { Tooltip, TooltipTrigger, TooltipContent, TooltipProvider };