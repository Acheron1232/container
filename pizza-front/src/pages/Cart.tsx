import { useNavigate } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import { useState } from "react";
import { useCart } from "../cart/CartContext";
import { useOrderApi } from "../api/order";

export default function CartPage() {
  const { items, updateQuantity, removeItem, total, count, clear } = useCart();
  const auth = useAuth();
  const { create } = useOrderApi();
  const navigate = useNavigate();

  // initialize to current local datetime (without seconds) suitable for input[type="datetime-local"]
  const initLocal = () => {
    const d = new Date();
    d.setSeconds(0, 0);
    const pad = (n: number) => String(n).padStart(2, "0");
    const local = `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}T${pad(d.getHours())}:${pad(d.getMinutes())}`;
    return local;
  };
  const [orderTimeLocal, setOrderTimeLocal] = useState<string>(initLocal());
  const [timeError, setTimeError] = useState<string | null>(null);

  const validateTime = (value: string) => {
    if (!value) return "Please select date and time";
    // Compare with now; treat input as local time
    const selected = new Date(value);
    const now = new Date();
    if (selected.getTime() < now.getTime() - 60_000) {
      return "Selected time is in the past";
    }
    return null;
  };

  const placeOrder = async () => {
    if (!auth.isAuthenticated) {
      await auth.signinRedirect();
      return;
    }
    if (items.length === 0) return;

    const err = validateTime(orderTimeLocal);
    setTimeError(err);
    if (err) return;

    try {
      // Backend expects LocalDateTime without timezone. Ensure we send seconds.
      const isoLocal = orderTimeLocal.length === 16 ? `${orderTimeLocal}:00` : orderTimeLocal;
      const order = {
        orderTime: isoLocal,
        pizzas: items.map((i) => ({ pizzaId: i.pizzaId, quantity: i.quantity })),
      };
      await create(order);
      clear();
      navigate("/orders");
    } catch (e: unknown) {
      const msg = e instanceof Error ? e.message : String(e);
      alert("Failed to place order: " + msg);
    }
  };

  return (
    <div className="page">
      <h2>Your Cart</h2>
      {items.length === 0 ? (
        <p>Your cart is empty.</p>
      ) : (
        <div className="cart">
          <ul>
            {items.map((i) => (
              <li key={i.pizzaId} style={{ marginBottom: 12 }}>
                <div style={{ display: "flex", alignItems: "center", gap: 12 }}>
                  <div style={{ minWidth: 180 }}>
                    <strong>{i.name}</strong> — ${i.price.toFixed(2)}
                  </div>
                  <div>
                    <button onClick={() => updateQuantity(i.pizzaId, i.quantity - 1)}>-</button>
                    <input
                      type="number"
                      min={1}
                      value={i.quantity}
                      onChange={(e) => updateQuantity(i.pizzaId, Math.max(1, Number(e.target.value) || 1))}
                      style={{ width: 50, textAlign: "center", margin: "0 8px" }}
                    />
                    <button onClick={() => updateQuantity(i.pizzaId, i.quantity + 1)}>+</button>
                  </div>
                  <div style={{ marginLeft: "auto" }}>
                    ${(i.price * i.quantity).toFixed(2)}
                  </div>
                  <button onClick={() => removeItem(i.pizzaId)}>Remove</button>
                </div>
              </li>
            ))}
          </ul>

          <div style={{ marginTop: 16, display: "flex", gap: 12, alignItems: "center", flexWrap: "wrap" }}>
            <label>
              Pick delivery time:
              <input
                type="datetime-local"
                value={orderTimeLocal}
                onChange={(e) => {
                  setOrderTimeLocal(e.target.value);
                  setTimeError(null);
                }}
                style={{ marginLeft: 8 }}
              />
            </label>
            {timeError && <span style={{ color: "red" }}>{timeError}</span>}
          </div>

          <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginTop: 16 }}>
            <div>
              Items: {count} | Total: ${total.toFixed(2)}
            </div>
            <div style={{ display: "flex", gap: 8 }}>
              <button onClick={() => clear()}>Clear</button>
              <button onClick={placeOrder} disabled={items.length === 0}>Place order</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
