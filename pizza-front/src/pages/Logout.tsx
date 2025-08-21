import { useEffect } from "react";
import { useAuth } from "react-oidc-context";
import { useNavigate } from "react-router-dom";

export default function LogoutPage() {
  const auth = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (!auth.isLoading) {
        localStorage.setItem("pizza_cart_v1",JSON.stringify([]));
      navigate("/");
    }
  }, [auth.isLoading, navigate]);

  return <p>Loading...</p>;
}
