import { useAuthFetch } from "./http";

export interface Pizza {
  id: string;
  name: string;
  description?: string | null;
  images?: string[] | null;
  price: number;
}

export function usePizzaApi() {
  const authFetch = useAuthFetch();

  return {
    list: async (params?: {
      page?: number;
      size?: number;
      sort_by?: string;
      sort_dir?: "asc" | "desc";
    }): Promise<Pizza[]> => {
      const q = new URLSearchParams();
      if (params?.page != null) q.set("page", String(params.page));
      if (params?.size != null) q.set("size", String(params.size));
      if (params?.sort_by) q.set("sort_by", params.sort_by);
      if (params?.sort_dir) q.set("sort_dir", params.sort_dir);
      const res = await authFetch(`/pizza/${q.toString() ? `?${q.toString()}` : ""}`);
      if (!res.ok) throw new Error(`Failed to load pizzas: ${res.status}`);
      return res.json();
    },
    byName: async (name: string): Promise<Pizza | null> => {
      const res = await authFetch(`/pizza/${encodeURIComponent(name)}`);
      if (res.status === 404) return null;
      if (!res.ok) throw new Error(`Failed to load pizza ${name}: ${res.status}`);
      return res.json();
    },
    save: async (pizza: Omit<Pizza, "id">, files: File[]): Promise<Pizza> => {
      const form = new FormData();
      form.append("data", JSON.stringify(pizza));
      files.forEach((f) => form.append("images", f));
      const res = await authFetch(`/pizza`, { method: "POST", body: form });
      if (!res.ok) throw new Error(`Failed to save pizza: ${res.status}`);
      return res.json();
    },
  };
}
