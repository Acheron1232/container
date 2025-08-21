import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Menu from "./pages/Menu";
import Orders from "./pages/Orders";
import Navbar from "./components/Navbar";
import CallbackPage from "./pages/Callback";
import LogoutPage from "./pages/Logout";
import CartPage from "./pages/Cart";
import { useAuth } from "react-oidc-context";

function App() {
  const auth = useAuth();

  if (auth.activeNavigator === "signinSilent") return <div>Signing you in...</div>;
  if (auth.activeNavigator === "signoutRedirect") return <div>Signing you out...</div>;
  if (auth.isLoading) return <div>Loading...</div>;
  if (auth.error) return <div>Oops... {auth.error.message}</div>;

  return (
    <Router>
      <Navbar />
      <div className="p-4">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/menu" element={<Menu />} />
          <Route path="/orders" element={<Orders />} />
          <Route path="/cart" element={<CartPage />} />
          <Route path="/callback" element={<CallbackPage />} />
          <Route path="/logout" element={<LogoutPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
