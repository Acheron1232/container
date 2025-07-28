import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import {type UserManagerSettings, WebStorageStateStore} from "oidc-client-ts";
import {AuthProvider} from "react-oidc-context";

const CLIENT_ID = "public-client";
const AUTHSERVERURL = "http://127.0.0.1:9000";
const HOST_URL = window.location.origin;

const oidcConfig: UserManagerSettings = {
    authority: AUTHSERVERURL,
    client_id: CLIENT_ID,
    redirect_uri: HOST_URL + "/callback",
    response_type: "code",
    scope: "openid profile email",
    userStore: new WebStorageStateStore({store: window.localStorage})
};

createRoot(document.getElementById("root")!).render(
    <StrictMode>
        <AuthProvider {...oidcConfig}>
            <App />
        </AuthProvider>
    </StrictMode>,
);