package org.acheron.authserver.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomResolver implements OAuth2AuthorizationRequestResolver {

    private final DefaultOAuth2AuthorizationRequestResolver defaultResolver;

    public CustomResolver(ClientRegistrationRepository clients) {
        // /oauth2/authorize — endpoint для Spring Security OAuth2
        this.defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(clients, "/oauth2/authorize");
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        OAuth2AuthorizationRequest authRequest = defaultResolver.resolve(request);
        return handleNonClientRequest(authRequest);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        OAuth2AuthorizationRequest authRequest = defaultResolver.resolve(request, clientRegistrationId);
        return handleNonClientRequest(authRequest);
    }

    private OAuth2AuthorizationRequest handleNonClientRequest(OAuth2AuthorizationRequest request) {
        if (request == null) {
            // Якщо клієнт не переданий або не знайдений → редіректимо на дефолтну login сторінку
            return OAuth2AuthorizationRequest.authorizationCode()
                    .clientId("public-client")       // дефолтний Authorization Server client
                    .authorizationUri("/login")      // тут ми редіректимо на login page
                    .redirectUri("/login")           // redirect-uri після авторизації
                    .build();
        }
        return request;
    }
}
