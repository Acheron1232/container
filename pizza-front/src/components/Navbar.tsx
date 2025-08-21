import { Link } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import { useCart } from "../cart/CartContext";

export default function Navbar() {
  const auth = useAuth();
  const { count } = useCart();
  return (
    <nav style={{ display: "flex", gap: 16, padding: 12, borderBottom: "1px solid #ccc" }}>
      <Link to="/">Home</Link>
      <Link to="/menu">Menu</Link>
      <Link to="/orders">Orders</Link>
      <Link to="/cart">Cart ({count})</Link>
      <div style={{ marginLeft: "auto" }}>
        {auth.isAuthenticated ? (
          <button onClick={() => auth.signoutRedirect({ post_logout_redirect_uri: window.location.origin + "/logout" })}>Logout</button>
        ) : (
          <button onClick={() => auth.signinRedirect()}>Login</button>
        )}
      </div>
    </nav>
  );
}
