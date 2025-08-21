import { useAuth } from "react-oidc-context";

export default function Home() {
  const auth = useAuth();
    return (
        <div className="page">
            <h1>Pizza Service</h1>
            {auth.isAuthenticated ? (
                <p>Welcome! You are logged in.</p>
            ) : (
                <>
                    <p>Please login to view menu and place orders.</p>
                    <button onClick={() => auth.signinRedirect()}>Log in</button>
                </>
            )}
        </div>
    );

}
