/// <reference types="vite/client" />

// Extend ImportMetaEnv with your custom VITE_* variables
interface ImportMetaEnv {
  readonly VITE_API_URL: string;
  readonly env: ImportMetaEnv;
  // Add other VITE_* variables here if needed:
  // readonly VITE_APP_NAME: string;
  // readonly VITE_STRIPE_KEY: string;
}