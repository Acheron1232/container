package org.acheron.authserver.config;

import lombok.RequiredArgsConstructor;
import org.acheron.authserver.entity.User;
import org.acheron.authserver.service.UserService;
import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
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
        System.out.println(context.getTokenType().toString());
        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
            context.getClaims().claims(claims -> {
                Object principal = context.getPrincipal().getPrincipal();
                User user = null;
                if (principal instanceof UserDetails) { // form login.html
                    user = (User) principal;
                } else if (principal instanceof DefaultOAuth2User oidcUser) { // oauth2 login.html
                    // fetch user by email to obtain User object when principal is not already a User object
                    user = (User) userService.findByName(oidcUser.getAttribute("login")==null?oidcUser.getAttribute("name"):oidcUser.getAttribute("login")).orElseThrow(()-> new UsernameNotFoundException("User not found"));
                }

                if (user == null) return;
                Set<String> roles = AuthorityUtils.authorityListToSet(user.getAuthorities()).stream().map(c -> c.replaceFirst("^ROLE_", "")).collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
                claims.put("roles", roles);
                claims.put("username", user.getUsername());
            });
        }
    }
}