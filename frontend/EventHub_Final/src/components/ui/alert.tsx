import * as React from "react";
import { cn } from "./utils";

interface AlertProps extends React.ComponentProps<"div"> {
  variant?: "default" | "destructive";
}

function Alert({ className, variant = "default", ...props }: AlertProps) {
  const variantClasses = {
    default: "bg-white text-gray-900 border-gray-200",
    destructive: "bg-red-50 text-red-900 border-red-200",
  };

  return (
    <div
      role="alert"
      className={cn(
        "relative w-full rounded-lg border px-4 py-3 text-sm",
        variantClasses[variant],
        className
      )}
      {...props}
    />
  );
}

function AlertTitle({ className, ...props }: React.ComponentProps<"div">) {
  return (
    <div
      className={cn("font-medium tracking-tight", className)}
      {...props}
    />
  );
}

function AlertDescription({ className, ...props }: React.ComponentProps<"div">) {
  return (
    <div
      className={cn("text-sm text-gray-600 mt-1", className)}
      {...props}
    />
  );
}

export { Alert, AlertTitle, AlertDescription };