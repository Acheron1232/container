package org.acheron.authserver.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acheron.authserver.entity.User;
import org.acheron.authserver.service.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        if (!(authentication instanceof OAuth2AuthenticationToken oAuth2Token)) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        String provider = oAuth2Token.getAuthorizedClientRegistrationId();
        if (!"github".equals(provider) && !"google".equals(provider)) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = new HashMap<>(principal.getAttributes());

        String email;
        try {
            if ("github".equals(provider)) {
                email = ((String)attributes.getOrDefault("email",""));
                if (email == null) {
                    // Якщо email відсутній у GitHub атрибутах, то можна запросити через API (як у твоєму сервісі)
                    // Але для простоти припустимо, що email є. Якщо ні — кидаємо помилку.
                    email = getPrimaryEmailForGitHub(authentication);
                }
            } else {
                // Google зазвичай дає email прямо
                email = attributes.getOrDefault("email", "").toString();
                if (email.isEmpty()) {
                    throw new IllegalStateException("Email not found in Google attributes");
                }
            }
        } catch (Exception e) {
            log.error("Failed to get email from OAuth2 provider", e);
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        AtomicReference<User> userRef = new AtomicReference<>();

        String finalEmail = email;
        userService.findByEmail(email).ifPresentOrElse(user -> {
            userRef.set(user);
        }, () -> {
            String username = (String) attributes.get("name");
            User newUser = new User();
            newUser.setEmail(finalEmail);
            newUser.setUsername(username);
            newUser.setDisplayName((String) attributes.getOrDefault("name", username));
            newUser.setImage((String) attributes.getOrDefault("avatar_url", attributes.getOrDefault("picture", "")));
            newUser.setIsEmailVerified(true);
            newUser.setRole(User.Role.USER);
            newUser.setAuthMethod("github".equals(provider) ? User.AuthMethod.GITHUB : User.AuthMethod.GOOGLE);
            userRef.set(userService.save(newUser));
        });

        log.info("User logged in via {}: {}", provider, userRef.get().getEmail());

        super.onAuthenticationSuccess(request, response, authentication);
    }

//    private String generateUsernameFromAttributes(Map<String, Object> attributes, String provider) {
//        String baseUsername;
//        if ("github".equals(provider)) {
//            baseUsername = (String) attributes.getOrDefault("login", "user");
//        } else {
//            baseUsername = ((String) attributes.getOrDefault("name", "user")).replaceAll("\\s+", "").toLowerCase();
//        }
//
//        String username = baseUsername;
//        int attempt = 0;
//        while (userService.existsByUsername(username)) {
//            attempt++;
//            username = baseUsername + attempt;
//        }
//        return username;
//    }

    private String getPrimaryEmailForGitHub(Authentication authentication) throws JsonProcessingException {
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId(),
                        authentication.getName());
        String accessToken = client.getAccessToken().getTokenValue();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "https://api.github.com/user/emails";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String json = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> emails = objectMapper.readValue(json, new TypeReference<>() {
        });
        return emails.stream()
                .filter(email -> Boolean.TRUE.equals(email.get("primary")))
                .map(email -> (String) email.get("email"))
                .findFirst()
                .orElseThrow();
    }
}
