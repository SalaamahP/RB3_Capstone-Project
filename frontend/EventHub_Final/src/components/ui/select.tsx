import * as React from "react";
import { ChevronDown, Check } from "lucide-react";
import { cn } from "./utils";

interface SelectProps {
  value?: string;
  onValueChange?: (value: string) => void;
  children: React.ReactNode;
}

interface SelectContextType {
  value?: string;
  onValueChange?: (value: string) => void;
  isOpen: boolean;
  setIsOpen: (open: boolean) => void;
}

const SelectContext = React.createContext<SelectContextType | null>(null);

function Select({ value, onValueChange, children }: SelectProps) {
  const [isOpen, setIsOpen] = React.useState(false);

  return (
    <SelectContext.Provider value={{ value, onValueChange, isOpen, setIsOpen }}>
      <div className="relative">
        {children}
      </div>
    </SelectContext.Provider>
  );
}

function SelectTrigger({ className, children, ...props }: React.ComponentProps<"button">) {
  const context = React.useContext(SelectContext);
  if (!context) throw new Error("SelectTrigger must be used within Select");

  return (
    <button
      type="button"
      className={cn(
        "flex h-9 w-full items-center justify-between rounded-md border border-gray-300 bg-white px-3 py-2 text-sm placeholder:text-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 disabled:cursor-not-allowed disabled:opacity-50",
        className
      )}
      onClick={() => context.setIsOpen(!context.isOpen)}
      {...props}
    >
      {children}
      <ChevronDown className="h-4 w-4 opacity-50" />
    </button>
  );
}

function SelectValue({ placeholder }: { placeholder?: string }) {
  const context = React.useContext(SelectContext);
  if (!context) throw new Error("SelectValue must be used within Select");

  return (
    <span className="text-left">
      {context.value || placeholder}
    </span>
  );
}

function SelectContent({ className, children }: { className?: string; children: React.ReactNode }) {
  const context = React.useContext(SelectContext);
  if (!context) throw new Error("SelectContent must be used within Select");

  React.useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      const target = event.target as Element;
      if (!target.closest('[data-select-content]')) {
        context.setIsOpen(false);
      }
    };

    if (context.isOpen) {
      document.addEventListener('mousedown', handleClickOutside);
      return () => document.removeEventListener('mousedown', handleClickOutside);
    }
  }, [context.isOpen]);

  if (!context.isOpen) return null;

  return (
    <div
      data-select-content
      className={cn(
        "absolute z-50 w-full mt-1 bg-white border border-gray-200 rounded-md shadow-lg max-h-60 overflow-auto",
        className
      )}
    >
      <div className="p-1">
        {children}
      </div>
    </div>
  );
}

function SelectItem({ 
  value, 
  children, 
  className,
  ...props 
}: { 
  value: string;
  children: React.ReactNode;
  className?: string;
}) {
  const context = React.useContext(SelectContext);
  if (!context) throw new Error("SelectItem must be used within Select");

  const isSelected = context.value === value;

  return (
    <div
      className={cn(
        "relative flex cursor-pointer select-none items-center rounded-sm px-2 py-1.5 text-sm outline-none hover:bg-gray-100 focus:bg-gray-100",
        isSelected && "bg-blue-100",
        className
      )}
      onClick={() => {
        context.onValueChange?.(value);
        context.setIsOpen(false);
      }}
      {...props}
    >
      {children}
      {isSelected && (
        <Check className="absolute right-2 h-4 w-4" />
      )}
    </div>
  );
}

export {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
};