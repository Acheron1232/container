/* eslint react-refresh/only-export-components: off */
import { createContext, PropsWithChildren, useContext, useEffect, useMemo, useState } from "react";

export interface CartItem {
  pizzaId: string;
  name: string;
  price: number;
  quantity: number;
}

interface CartContextValue {
  items: CartItem[];
  addItem: (item: Omit<CartItem, "quantity">, quantity?: number) => void;
  removeItem: (pizzaId: string) => void;
  updateQuantity: (pizzaId: string, quantity: number) => void;
  clear: () => void;
  count: number; // total items count
  total: number; // total price
}

const CartContext = createContext<CartContextValue | undefined>(undefined);

const STORAGE_KEY = "pizza_cart_v1";

export function CartProvider({ children }: PropsWithChildren) {
  const [items, setItems] = useState<CartItem[]>(() => {
    try {
      const raw = localStorage.getItem(STORAGE_KEY);
      return raw ? (JSON.parse(raw) as CartItem[]) : [];
    } catch {
      /* ignore corrupted storage */
      return [];
    }
  });

  useEffect(() => {
    try {
      localStorage.setItem(STORAGE_KEY, JSON.stringify(items));
    } catch {
      /* ignore quota/storage errors */
    }
  }, [items]);

  const addItem: CartContextValue["addItem"] = (item, quantity = 1) => {
    setItems((prev) => {
      const idx = prev.findIndex((i) => i.pizzaId === item.pizzaId);
      if (idx >= 0) {
        const copy = [...prev];
        copy[idx] = { ...copy[idx], quantity: copy[idx].quantity + quantity };
        return copy;
      }
      return [...prev, { ...item, quantity }];
    });
  };

  const removeItem: CartContextValue["removeItem"] = (pizzaId) => {
    setItems((prev) => prev.filter((i) => i.pizzaId !== pizzaId));
  };

  const updateQuantity: CartContextValue["updateQuantity"] = (pizzaId, quantity) => {
    setItems((prev) => prev.map((i) => (i.pizzaId === pizzaId ? { ...i, quantity } : i)).filter((i) => i.quantity > 0));
  };

  const clear = () => setItems([]);

  const value = useMemo<CartContextValue>(() => {
    const count = items.reduce((sum, i) => sum + i.quantity, 0);
    const total = items.reduce((sum, i) => sum + i.quantity * i.price, 0);
    return { items, addItem, removeItem, updateQuantity, clear, count, total };
  }, [items]);

  return <CartContext.Provider value={value}>{children}</CartContext.Provider>;
}

export function useCart() {
  const ctx = useContext(CartContext);
  if (!ctx) throw new Error("useCart must be used within CartProvider");
  return ctx;
}
