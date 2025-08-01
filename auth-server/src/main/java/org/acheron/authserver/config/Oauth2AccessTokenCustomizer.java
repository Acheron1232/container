package org.acheron.authserver.config;

import lombok.RequiredArgsConstructor;
import org.acheron.authserver.user.User;
import org.acheron.authserver.user.UserService;
import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class Oauth2AccessTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    private final UserService userService;
 
    @Override
    public void customize(JwtEncodingContext context) {
        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
            context.getClaims().claims(claims -> {
                Object principal = context.getPrincipal().getPrincipal();
                User user = null;
                if (principal instanceof UserDetails) { // form login.html
                    user = (User) principal;
                } else if (principal instanceof DefaultOidcUser oidcUser) { // oauth2 login.html
                    // fetch user by email to obtain User object when principal is not already a User object
                    user = (User) userService.loadOAuthUser(oidcUser);
                }

                if (user == null) return;
                Set<String> roles = AuthorityUtils.authorityListToSet(user.getAuthorities()).stream().map(c -> c.replaceFirst("^ROLE_", "")).collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
                claims.put("roles", roles);
                claims.put("username", user.getUsername());

                // I have only added the roles to the JWT here as I am using the limited fields
                // on the UserDetails object, but you can add many other important fields by
                // using your applications User class (as shown below)
 
                // claims.put("email", user.getEmail());
                // claims.put("sub", user.getId());
            });
        }
    }
}