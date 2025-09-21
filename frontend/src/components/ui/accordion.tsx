import * as React from "react";
import { ChevronDown } from "lucide-react";
import { cn } from "./utils";

interface AccordionContextType {
  value?: string | string[];
  onValueChange?: (value: string | string[]) => void;
  type?: "single" | "multiple";
}

const AccordionContext = React.createContext<AccordionContextType | null>(null);

function Accordion({ 
  type = "single", 
  value, 
  onValueChange, 
  className,
  children,
  ...props 
}: {
  type?: "single" | "multiple";
  value?: string | string[];
  onValueChange?: (value: string | string[]) => void;
  className?: string;
  children: React.ReactNode;
}) {
  return (
    <AccordionContext.Provider value={{ value, onValueChange, type }}>
      <div className={cn("space-y-2", className)} {...props}>
        {children}
      </div>
    </AccordionContext.Provider>
  );
}

function AccordionItem({
  className,
  value: itemValue,
  children,
  ...props
}: React.ComponentProps<"div"> & {
  value: string;
}) {
  const context = React.useContext(AccordionContext);
  if (!context) throw new Error("AccordionItem must be used within Accordion");

  const isOpen = React.useMemo(() => {
    if (Array.isArray(context.value)) {
      return context.value.includes(itemValue);
    }
    return context.value === itemValue;
  }, [context.value, itemValue]);

  const toggleItem = () => {
    if (context.type === "multiple") {
      const currentValue = Array.isArray(context.value) ? context.value : [];
      if (currentValue.includes(itemValue)) {
        context.onValueChange?.(currentValue.filter(v => v !== itemValue));
      } else {
        context.onValueChange?.([...currentValue, itemValue]);
      }
    } else {
      context.onValueChange?.(isOpen ? "" : itemValue);
    }
  };

  return (
    <div
      className={cn("border-b last:border-b-0", className)}
      data-state={isOpen ? "open" : "closed"}
      {...props}
    >
      <AccordionItemContext.Provider value={{ isOpen, toggleItem, value: itemValue }}>
        {children}
      </AccordionItemContext.Provider>
    </div>
  );
}

interface AccordionItemContextType {
  isOpen: boolean;
  toggleItem: () => void;
  value: string;
}

const AccordionItemContext = React.createContext<AccordionItemContextType | null>(null);

function AccordionTrigger({
  className,
  children,
  ...props
}: React.ComponentProps<"button">) {
  const context = React.useContext(AccordionItemContext);
  if (!context) throw new Error("AccordionTrigger must be used within AccordionItem");

  return (
    <div className="flex">
      <button
        type="button"
        className={cn(
          "flex flex-1 items-center justify-between gap-4 py-4 text-left text-sm font-medium transition-all hover:underline focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:pointer-events-none disabled:opacity-50",
          className,
        )}
        onClick={context.toggleItem}
        {...props}
      >
        {children}
        <ChevronDown 
          className={cn(
            "h-4 w-4 shrink-0 text-gray-500 transition-transform duration-200",
            context.isOpen && "rotate-180"
          )} 
        />
      </button>
    </div>
  );
}

function AccordionContent({
  className,
  children,
  ...props
}: React.ComponentProps<"div">) {
  const context = React.useContext(AccordionItemContext);
  if (!context) throw new Error("AccordionContent must be used within AccordionItem");

  if (!context.isOpen) return null;

  return (
    <div
      className="overflow-hidden text-sm transition-all duration-200"
      {...props}
    >
      <div className={cn("pb-4 pt-0", className)}>
        {children}
      </div>
    </div>
  );
}

export { Accordion, AccordionItem, AccordionTrigger, AccordionContent };