import { useAuth } from "react-oidc-context";

export const API_BASE = "http://localhost:8080";

export function useAuthFetch() {
  const auth = useAuth();
  return async (input: string, init: RequestInit = {}) => {
    const headers = new Headers(init.headers);
    if (auth.user?.access_token) {
      headers.set("Authorization", `Bearer ${auth.user.access_token}`);
    }
    return fetch(`${API_BASE}${input}`, {
      ...init,
      headers,
      credentials: "include",
    });
  };
}
