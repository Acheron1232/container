package org.acheron.authserver.config;

import lombok.RequiredArgsConstructor;
import org.acheron.authserver.dto.UserDto;
import org.acheron.authserver.service.UserService;
import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;


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
                UserDto user = null;
                if (principal instanceof UserDetails) { // form login.html
                    user = userService.findByUsername(((UserDetails) principal).getUsername());
                } else if (principal instanceof DefaultOAuth2User oidcUser) { // oauth2 login.html
                    // fetch user by email to obtain User object when principal is not already a User object
                    user = userService.findByUsername(oidcUser.getAttribute("login")==null?oidcUser.getAttribute("name"):oidcUser.getAttribute("login"));
                }

                if (user == null) return;
//                Set<String> roles = AuthorityUtils.authorityListToSet(user.getAuthorities()).stream().map(c -> c.replaceFirst("^ROLE_", "")).collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
                claims.put("roles", user.getRole());
                claims.put("name", user.getUsername());
            });
        }
    }
}