/// <reference types="vite/client" />

declare global {
  interface Window {
    __TAURI__?: {
      event: {
        listen: (event: string, callback: (data: any) => void) => Promise<() => void>;
      };
    };
  }
}

export {};
