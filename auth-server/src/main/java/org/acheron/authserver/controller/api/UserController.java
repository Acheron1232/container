package org.acheron.authserver.controller.api;

import lombok.RequiredArgsConstructor;
import org.acheron.authserver.dto.UserChangeDto;
import org.acheron.authserver.dto.UserDto;
import org.acheron.authserver.entity.User;
import org.acheron.authserver.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/userinfo")
    public UserDto getCurrentUser(@AuthenticationPrincipal Jwt jwt){
        User user = userService.findByName((String) jwt.getClaims().get("username")).orElseThrow(() -> new UsernameNotFoundException((String) jwt.getClaims().get("username")));
        return new UserDto(user.getUsername(),user.getEmail(),user.getRole());
    }
    @PatchMapping("/userinfo")
    public UserChangeDto userChange(Principal principal){
        return null;
    }

//    @PostMapping("/confirmEmail")
//    public ResponseEntity<String> confirmEmail(Principal principal) {
//        return authService.confirmEmail(principal);
//    }
//    @GetMapping("/confirm")
//    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
//        return authService.confirm(token);
//    }
//
//    @PostMapping("/resetPassword")
//    public ResponseEntity<String> resetPassword(@RequestBody String email) {
//        return authService.resetPassword(email);
//    }
//
//    @GetMapping("/reset")
//    public ResponseEntity<String> reset(@RequestParam("token") String token) {
//        return authService.reset(token);
//    }

}
