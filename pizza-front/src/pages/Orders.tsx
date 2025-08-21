import { useEffect, useState } from "react";
import { useAuth } from "react-oidc-context";
import {type OrderDto, useOrderApi } from "../api/order";

export default function Orders() {
  const auth = useAuth();
  const { list } = useOrderApi();
  const [orders, setOrders] = useState<OrderDto[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    (async () => {
      try {
        const data = await list({ size: 50, sort_dir: "desc" });
          console.log(data);
        setOrders(data);
      } catch (e) {
        const msg = e instanceof Error ? e.message : String(e);
        setError(msg);
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  if (!auth.isAuthenticated) {
    return (
      <div>
        <p>You must be logged in to view orders.</p>
        <button onClick={() => auth.signinRedirect()}>Log in</button>
      </div>
    );
  }

  if (loading) return <p>Loading orders...</p>;
  if (error) return <p>Error: {error}</p>;

    return (
        <div className="page">
            <h2>Your Orders</h2>
            {orders.length === 0 && <p>No orders yet.</p>}
            <div>
                {orders.map((o) => (
                    <div key={o.id} className="order-card">
                        <h3>{new Date(o.orderTime).toLocaleString()} — {o.status}</h3>
                        <ul className="order-items">
                            {o.pizzas.map((pp, idx) => (
                                <li key={idx}>
                                    {pp.name} × {pp.quantity} — ${(pp.price * pp.quantity).toFixed(2)}
                                </li>
                            ))}
                        </ul>
                    </div>
                ))}
            </div>
        </div>
    );

}
