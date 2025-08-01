// src/pages/Home.js
import { useAuth } from "react-oidc-context";
import { useNavigate } from "react-router-dom";

export default function Home() {
    const auth = useAuth();
    const navigate = useNavigate();

    const fetchProtected = async () => {
        try {
            const res = await fetch("http://localhost:8080/books", {
                headers: {
                    Authorization: `Bearer ${auth.user?.access_token}`,
                },
                credentials: "include"
            });
            const data = await res.text();
            console.log(data);
        } catch (e) {
            console.error("Failed to fetch", e);
        }
    };

    const handleLogout = async () => {
        await fetch(auth.settings.authority + "/spa/logout", {
            headers: {
                Authorization: `Bearer ${auth.user?.access_token}`,
            },
            credentials: "include",
        });
        await auth.removeUser();
        navigate("/login");
    };

    if (!auth.isAuthenticated) {
        navigate("/login");
        return null;
    }

    return (
        <div>
            Hello {auth.user?.profile.sub}{" "}
            <button onClick={handleLogout}>Log out</button>
            <button onClick={fetchProtected}>Fetch protected resource</button>
        </div>
    );
}
