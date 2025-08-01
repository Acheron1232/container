package org.acheron.resourceserver.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BookResource {

    @GetMapping("/books")
    public ResponseEntity<String> getBooks(Authentication authentication, HttpServletRequest request) {
        assert authentication instanceof JwtAuthenticationToken;
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;

        String username = authentication.getName();
        String jwtString = jwtAuthenticationToken.getToken().getTokenValue();

        // Prepare headers with Authorization Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtString); // Shortcut for "Authorization: Bearer <token>"
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        // Send GET request with token
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(
                "http://127.0.0.1:9000/user/current",
                HttpMethod.GET,
                httpEntity,
                Map.class
        );
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setBearerAuth(jwtString); // Shortcut for "Authorization: Bearer <token>"
        headers1.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> httpEntity1 = new HttpEntity<>(headers1);

        // Send GET request with token
        RestTemplate restTemplate1 = new RestTemplate();
        ResponseEntity<Map> response1 = restTemplate.exchange(
                "http://127.0.0.1:9000/userinfo",
                HttpMethod.GET,
                httpEntity1,
                Map.class
        );
        System.out.println("zxc"+response1.getBody());
        Map body = response.getBody();
        System.out.println("zxc1"+body);
        return ResponseEntity.ok("Hi " + body + ", here are some books [book1, book2],\n"
                + "Also here is your jwt: " + jwtString
                + "\nAnd call to /user/current returned: " + response.getBody());
    }
}
