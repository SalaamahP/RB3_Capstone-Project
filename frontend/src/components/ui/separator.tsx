import * as React from "react";
import { cn } from "./utils";

interface SeparatorProps extends React.ComponentProps<"div"> {
  orientation?: "horizontal" | "vertical";
}

function Separator({ 
  className, 
  orientation = "horizontal", 
  ...props 
}: SeparatorProps) {
  return (
    <div
      className={cn(
        "bg-gray-200 shrink-0",
        orientation === "horizontal" ? "h-px w-full" : "h-full w-px",
        className
      )}
      {...props}
    />
  );
}

export { Separator };