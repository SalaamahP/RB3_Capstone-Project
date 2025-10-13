import * as React from "react";
import { cn } from "./utils";
import { Label } from "./label";

// Simple form context for basic form functionality
interface FormContextType {
  errors?: Record<string, string>;
  setError?: (name: string, message: string) => void;
  clearError?: (name: string) => void;
}

const FormContext = React.createContext<FormContextType>({});

function Form({ 
  children, 
  className,
  ...props 
}: React.ComponentProps<"form">) {
  const [errors, setErrors] = React.useState<Record<string, string>>({});

  const setError = (name: string, message: string) => {
    setErrors(prev => ({ ...prev, [name]: message }));
  };

  const clearError = (name: string) => {
    setErrors(prev => {
      const newErrors = { ...prev };
      delete newErrors[name];
      return newErrors;
    });
  };

  return (
    <FormContext.Provider value={{ errors, setError, clearError }}>
      <form className={cn("space-y-4", className)} {...props}>
        {children}
      </form>
    </FormContext.Provider>
  );
}

// Simple form field context
interface FormFieldContextType {
  name: string;
  id: string;
}

const FormFieldContext = React.createContext<FormFieldContextType | null>(null);

function FormField({ 
  name, 
  children 
}: { 
  name: string; 
  children: React.ReactNode; 
}) {
  const id = React.useId();
  
  return (
    <FormFieldContext.Provider value={{ name, id }}>
      {children}
    </FormFieldContext.Provider>
  );
}

function FormItem({ className, ...props }: React.ComponentProps<"div">) {
  return (
    <div className={cn("space-y-2", className)} {...props} />
  );
}

function FormLabel({ className, ...props }: React.ComponentProps<"label">) {
  const fieldContext = React.useContext(FormFieldContext);
  const formContext = React.useContext(FormContext);
  
  const hasError = fieldContext && formContext.errors?.[fieldContext.name];

  return (
    <Label
      className={cn(
        hasError && "text-red-600",
        className
      )}
      htmlFor={fieldContext?.id}
      {...props}
    />
  );
}

function FormControl({ children }: { children: React.ReactNode }) {
  const fieldContext = React.useContext(FormFieldContext);
  const formContext = React.useContext(FormContext);
  
  const hasError = fieldContext && formContext.errors?.[fieldContext.name];

  if (React.isValidElement(children)) {
    return React.cloneElement(children, {
      id: fieldContext?.id,
      'aria-invalid': hasError ? 'true' : 'false',
    });
  }

  return <>{children}</>;
}

function FormDescription({ className, ...props }: React.ComponentProps<"p">) {
  return (
    <p className={cn("text-sm text-gray-600", className)} {...props} />
  );
}

function FormMessage({ className, children, ...props }: React.ComponentProps<"p">) {
  const fieldContext = React.useContext(FormFieldContext);
  const formContext = React.useContext(FormContext);
  
  const errorMessage = fieldContext && formContext.errors?.[fieldContext.name];
  const body = errorMessage || children;

  if (!body) {
    return null;
  }

  return (
    <p className={cn("text-sm text-red-600", className)} {...props}>
      {body}
    </p>
  );
}

export {
  Form,
  FormItem,
  FormLabel,
  FormControl,
  FormDescription,
  FormMessage,
  FormField,
};