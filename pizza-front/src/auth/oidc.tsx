import { AuthProvider } from "react-oidc-context";
import { type UserManagerSettings, WebStorageStateStore } from "oidc-client-ts";
import type {PropsWithChildren} from "react";

const CLIENT_ID = "pizza-service";
const AUTHSERVERURL = "http://127.0.0.1:9000";
const HOST_URL = window.location.origin;

const oidcConfig: UserManagerSettings = {
  authority: AUTHSERVERURL,
  client_id: CLIENT_ID,
  redirect_uri: HOST_URL + "/callback",
  response_type: "code",
  scope: "openid profile email",
  userStore: new WebStorageStateStore({ store: window.localStorage }),
  extraQueryParams: { prompt: "login" },
};

export default function OidcProvider({ children }: PropsWithChildren) {
  return <AuthProvider {...oidcConfig}>{children}</AuthProvider>;
}
