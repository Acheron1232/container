import { useEffect, useState } from "react";
import { useAuth } from "react-oidc-context";
import { type Pizza, usePizzaApi } from "../api/pizza";
import PizzaCard from "./PIzzaCard.tsx";
import "./Menu.css";
import { useCart } from "../cart/CartContext";

export default function Menu() {
    const auth = useAuth();
    const { list } = usePizzaApi();
    const { addItem } = useCart();
    const [pizzas, setPizzas] = useState<Pizza[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        (async () => {
            try {
                const data = await list({ size: 100 });
                setPizzas(data);
            } catch (e: unknown) {
                const msg = e instanceof Error ? e.message : String(e);
                setError(msg);
            } finally {
                setLoading(false);
            }
        })();
    }, []);

    const addToCart = (pizza: Pizza) => {
        addItem({ pizzaId: pizza.id, name: pizza.name, price: pizza.price }, 1);
    };

    if (!auth.isAuthenticated) {
        return (
            <div>
                <p>You must be logged in to view the menu.</p>
                <button onClick={() => auth.signinRedirect()}>Log in</button>
            </div>
        );
    }

    if (loading) return <p>Loading menu...</p>;
    if (error) return <p>Error: {error}</p>;

    return (
        <div className="page">
            <h2>Menu</h2>
            <div className="pizza-list">
                {pizzas.map((p) => (
                    <PizzaCard key={p.id} pizza={p} onAddToCart={() => addToCart(p)} />
                ))}
            </div>
        </div>
    );
}
