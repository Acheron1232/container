import { useEffect, useState } from "react";
import { useAuth } from "react-oidc-context";
import { type Pizza, usePizzaApi } from "../api/pizza";
import PizzaCard from "./PizzaCard.tsx";
import "./Menu.css";
import { useCart } from "../cart/CartContext";

export default function Menu() {
    const auth = useAuth();
    const { list } = usePizzaApi();
    const { addItem } = useCart();

    const [pizzas, setPizzas] = useState<Pizza[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const [page, setPage] = useState(0);
    const [size, setSize] = useState(5);
    const [sortBy, setSortBy] = useState("name");
    const [sortDir, setSortDir] = useState<"asc" | "desc">("asc");

    useEffect(() => {
        if (!auth.isAuthenticated) return;

        setLoading(true);
        setError(null);

        (async () => {
            try {
                const data = await list({ page, size, sort_by: sortBy, sort_dir: sortDir });
                setPizzas(data);
            } catch (e: unknown) {
                const msg = e instanceof Error ? e.message : String(e);
                setError(msg);
            } finally {
                setLoading(false);
            }
        })();
    }, [page, size, sortBy, sortDir, auth.isAuthenticated]);

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

            {/* Sorting controls */}
            <div className="controls">
                <label>
                    Sort by:
                    <select value={sortBy} onChange={(e) => setSortBy(e.target.value)}>
                        <option value="name">Name</option>
                        <option value="price">Price</option>
                    </select>
                </label>
                <label>
                    Direction:
                    <select value={sortDir} onChange={(e) => setSortDir(e.target.value as "asc" | "desc")}>
                        <option value="asc">Ascending</option>
                        <option value="desc">Descending</option>
                    </select>
                </label>
                <label>
                    Items per page:
                    <select value={size} onChange={(e) => setSize(parseInt(e.target.value))}>
                        <option value={5}>5</option>
                        <option value={10}>10</option>
                        <option value={20}>20</option>
                    </select>
                </label>
            </div>

            <div className="pizza-list">
                {pizzas.map((p) => (
                    <PizzaCard key={p.id} pizza={p} onAddToCart={() => addToCart(p)} />
                ))}
            </div>

            {/* Pagination controls */}
            <div className="pagination">
                <button onClick={() => setPage((prev) => Math.max(prev - 1, 0))} disabled={page === 0}>
                    Previous
                </button>
                <span>Page {page + 1}</span>
                <button
                    onClick={() => setPage((prev) => prev + 1)}
                    disabled={pizzas.length < size} // якщо остання сторінка, кнопка Next блокується
                >
                    Next
                </button>
            </div>
        </div>
    );
}
