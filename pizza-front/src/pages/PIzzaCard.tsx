import { useState } from "react";
import { type Pizza } from "../api/pizza";
import "./Menu.css";

export default function PizzaCard({
                                      pizza,
                                      onAddToCart,
                                  }: {
    pizza: Pizza;
    onAddToCart: () => void;
}) {
    const [index, setIndex] = useState(0);

    const hasImages = pizza.images && pizza.images.length > 0;
    const hasMultiple = pizza.images && pizza.images.length > 1;

    const next = () =>
        setIndex((i) =>
            i === pizza.images!.length - 1 ? 0 : i + 1
        );
    const prev = () =>
        setIndex((i) =>
            i === 0 ? pizza.images!.length - 1 : i - 1
        );

    return (
        <div className="pizza-card">
            {hasImages && (
                <div className="pizza-images">
                    <img
                        src={pizza.images![index]}
                        alt={pizza.name}
                        className="pizza-img"
                    />
                    {hasMultiple && (
                        <>
                            <button className="nav-btn left" onClick={prev}>
                                ◀
                            </button>
                            <button className="nav-btn right" onClick={next}>
                                ▶
                            </button>
                            <div className="dots">
                                {pizza.images!.map((_, i) => (
                                    <span
                                        key={i}
                                        className={`dot ${i === index ? "active" : ""}`}
                                        onClick={() => setIndex(i)}
                                    />
                                ))}
                            </div>
                        </>
                    )}
                </div>
            )}

            <div className="pizza-info">
                <strong>{pizza.name}</strong> — ${pizza.price.toFixed(2)}
                {pizza.description && <p>{pizza.description}</p>}
                <button onClick={onAddToCart}>Add to cart</button>
            </div>
        </div>
    );
}
