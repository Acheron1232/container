import { useAuthFetch } from "./http";

export interface PizzaQuantity {
  pizzaId: string;
  quantity: number;
}

export interface CreateOrderRequest {
  orderTime: string; // ISO string
  pizzas: PizzaQuantity[];
}

export interface OrderDto {
  id: string;
  orderTime: string;
  status: string;
  pizzas: { pizzaId: string; name: string; price: number; quantity: number }[];
}

export function useOrderApi() {
  const authFetch = useAuthFetch();
  return {
    create: async (order: CreateOrderRequest): Promise<OrderDto> => {
      const res = await authFetch(`/pizza/order`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(order),
      });
      if (!res.ok) throw new Error(`Failed to create order: ${res.status}`);
      return res.json();
    },
    list: async (params?: {
      page?: number;
      size?: number;
      sort_by?: string;
      sort_dir?: "asc" | "desc";
      order_status?: string; // all | NEW | ...
    }): Promise<OrderDto[]> => {
      const q = new URLSearchParams();
      if (params?.page != null) q.set("page", String(params.page));
      if (params?.size != null) q.set("size", String(params.size));
      if (params?.sort_by) q.set("sort_by", params.sort_by);
      if (params?.sort_dir) q.set("sort_dir", params.sort_dir);
      if (params?.order_status) q.set("order_status", params.order_status);
      const res = await authFetch(`/pizza/order${q.toString() ? `?${q.toString()}` : ""}`);
      if (!res.ok) throw new Error(`Failed to load orders: ${res.status}`);
      return res.json();
    },
  };
}
