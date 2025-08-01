package org.acheron.authserver.config;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.acheron.authserver.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.OidcBackChannelServerLogoutHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.server.session.InMemoryReactiveOidcSessionRegistry;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final Oauth2AccessTokenCustomizer oauth2AccessTokenCustomizer;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
//        private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient webClient = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId("gateway-client")
                .clientSecret(passwordEncoder.encode("secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                //                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://localhost:8080/login/oauth2/code/gateway-client")
                .postLogoutRedirectUri("http://localhost:8080/logout")
                .scope(OidcScopes.OPENID)  // openid scope is mandatory for authentication
                .scope(OidcScopes.PROFILE)
                .scope(OidcScopes.EMAIL)
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofHours(4)).build())
                .build();

        RegisteredClient webClient2 = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId("zxc-client")
                .clientSecret(passwordEncoder.encode("secret1"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                //                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://localhost:8090/login/oauth2/code/zxc-client")
                .postLogoutRedirectUri("http://localhost:8090/logout")
                .scope(OidcScopes.OPENID)  // openid scope is mandatory for authentication
                .scope(OidcScopes.PROFILE)
                .scope(OidcScopes.EMAIL)
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofSeconds(100))
                        //                        .refreshTokenTimeToLive(Duration.ofSeconds(1))
                        .build())
                .build();

        RegisteredClient publicWebClient = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId("public-client")
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE) // authentication method set to 'NONE'
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://localhost:5173/callback")
                .postLogoutRedirectUri("http://localhost:5173/logout")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope(OidcScopes.EMAIL)
                .tokenSettings(
                        TokenSettings.builder()
                                .accessTokenTimeToLive(Duration.ofSeconds(200))
                                .reuseRefreshTokens(false)
                                .build()
                )
                .clientSettings(ClientSettings.builder().requireProofKey(true).build()) // enable PKCE
                .build();

        return new InMemoryRegisteredClientRepository(webClient, webClient2, publicWebClient);
    }

    @Bean
    @Order(1) // security filter chain for the authorization server
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {

        // applyDefaultSecurity method deprecated as of spring security 6.4.2, so we replace it with below code block
        // -- STARTS HERE
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                OAuth2AuthorizationServerConfigurer.authorizationServer();

        http
//                .sessionManagement(s-> s.se)
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, authorizationServer ->
                        authorizationServer
                                .clientAuthentication(clientAuthenticationConfigurer ->
                                        clientAuthenticationConfigurer
                                                .authenticationConverter(new PublicClientRefreshTokenAuthenticationConverter())
                                                .authenticationProvider(
                                                        new PublicClientRefreshTokenAuthenticationProvider(
                                                                registeredClientRepository(),
                                                                new InMemoryOAuth2AuthorizationService() // replace with your AuthorizationService implementation if you have one
                                                        )
                                                )
                                )
                                .oidc(Customizer.withDefaults()) // enable openid connect
                )
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll().requestMatchers("/spa/logout","/login.html").permitAll().anyRequest().authenticated());
        // -- ENDS HERE
        http.oidcLogout((logout) -> logout
                .backChannel(Customizer.withDefaults())
        );

        http
                .exceptionHandling((exceptions) -> // If any errors occur redirect user to login.html page
                        exceptions.defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                )
                // enable auth server to accept JWT for endpoints such as /userinfo
                .oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()));

        http.userDetailsService(userService);
        http.cors(Customizer.withDefaults());

//            http.cors(e->e.disable());
        return http.build();
    }

    @Bean
    @Order(2) // security filter chain for the rest of your application and any custom endpoints you may have
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()));
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(formLogin -> formLogin.loginPage("/login").permitAll()) // Enable form login.html
                .oauth2Login(oauth2Login -> oauth2Login.loginPage("/login").permitAll())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/","/spa/logout","/login.html")
                        .permitAll().requestMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated());
//            http.cors(e->e.disable());
        http.cors(Customizer.withDefaults());
//
        return http.build();
    }


    @Bean
    OAuth2TokenGenerator<OAuth2Token> tokenGenerator(JWKSource<SecurityContext> jwkSource) {
        JwtEncoder jwtEncoder = new NimbusJwtEncoder(jwkSource);
        JwtGenerator jwtAccessTokenGenerator = new JwtGenerator(jwtEncoder);
        jwtAccessTokenGenerator.setJwtCustomizer(oauth2AccessTokenCustomizer); // jwt customizer from part 1 (optional)

        return new DelegatingOAuth2TokenGenerator(
                jwtAccessTokenGenerator,
                new OAuth2PublicClientRefreshTokenGenerator() // add customized refresh token generator
        );
    }

}