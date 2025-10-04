import * as React from "react";
import { Check } from "lucide-react";
import { cn } from "./utils";

interface CheckboxProps {
  checked?: boolean;
  onCheckedChange?: (checked: boolean) => void;
  disabled?: boolean;
  className?: string;
}

function Checkbox({ 
  checked = false, 
  onCheckedChange, 
  disabled = false, 
  className 
}: CheckboxProps) {
  return (
    <button
      type="button"
      role="checkbox"
      aria-checked={checked}
      disabled={disabled}
      onClick={() => onCheckedChange?.(!checked)}
      className={cn(
        "h-4 w-4 shrink-0 rounded border border-gray-300 focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-blue-500 disabled:cursor-not-allowed disabled:opacity-50",
        checked 
          ? "bg-blue-600 border-blue-600 text-white" 
          : "bg-white",
        className
      )}
    >
      {checked && (
        <Check className="h-3 w-3" />
      )}
    </button>
  );
}

export { Checkbox };