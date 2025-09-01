package org.acheron.authserver.service;

import org.acheron.authserver.dto.UserCreateDto;
import org.acheron.authserver.dto.UserCreateOauthDto;
import org.acheron.authserver.dto.UserDto;
import org.acheron.authserver.entity.User;
import org.acheron.authserver.repo.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final RestClient restClient;
    private final UserRepository userRepository;
    private final UserGrpcClient userGrpcClient;
    private final OAuth2AuthorizedClientManager manager;

    public UserService(@Qualifier("rest-client-with-auth") RestClient restClient, UserRepository userRepository, UserGrpcClient userGrpcClient, OAuth2AuthorizedClientManager manager) {
        this.restClient = restClient;
        this.userRepository = userRepository;
        this.userGrpcClient = userGrpcClient;
        this.manager = manager;
    }


    public void save(UserCreateOauthDto user) {
        restClient.post().uri("/").contentType(MediaType.APPLICATION_JSON)
                .body(user).retrieve().toBodilessEntity();
    }
    public void save(UserCreateDto user) {
//        restClient.post().uri("/").contentType(MediaType.APPLICATION_JSON)
//                .body(user).retrieve().toBodilessEntity();
        OAuth2AuthorizeRequest request1 = OAuth2AuthorizeRequest
                .withClientRegistrationId("auth-server-service")
                .principal("auth-server")
                .build();
        OAuth2AuthorizedClient authorize = manager.authorize(request1);
        userGrpcClient.saveUser(user,authorize.getAccessToken().getTokenValue());
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserDto findByUsername(String username) {
        User byUsername = userRepository.findByUsername(username);

        return new UserDto(byUsername.getUsername(), byUsername.getEmail(), byUsername.getRole().toString());
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
