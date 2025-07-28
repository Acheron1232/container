
import './App.css'

import { useAuth } from "react-oidc-context";

function App() {
    const auth = useAuth();

    const fetchProtected = async () => {
        try {
            const res = await fetch("http://localhost:8081/books", {
                headers: {
                    Authorization: `Bearer ${auth.user?.access_token}`,
                },
                credentials: "include"
            });
            const data = await res.text();
            console.log(auth.user?.access_token)
            console.log(data);
        } catch (e) {
            console.error("Failed to fetch", e);
        }
    };


    switch (auth.activeNavigator) {
        case "signinSilent":
            return <div>Signing you in...</div>;
        case "signoutRedirect":
            return <div>Signing you out...</div>;
    }

    if (auth.isLoading) {
        return <div>Loading...</div>;
    }

    if (auth.error) {
        return <div>Oops... {auth.error.message}</div>;
    }

    if (auth.isAuthenticated) {
        return (
            <div>
                Hello {auth.user?.profile.sub}{" "}
                <button onClick={() => void auth.removeUser()}>Log out</button>
                <button onClick={fetchProtected}>Fetch protected resource</button>
            </div>
        );
    }

    return <button onClick={() => void auth.signinRedirect()}>Log in</button>;
}

export default App;