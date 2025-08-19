package com.acheron.userserver.api;

import com.acheron.userserver.dto.UserCreateDto;
import com.acheron.userserver.entity.User;
import lombok.RequiredArgsConstructor;
import com.acheron.userserver.dto.UserChangeDto;
import com.acheron.userserver.dto.UserDto;
import com.acheron.userserver.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
//@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    public void save(@RequestBody UserCreateDto userDto) {
        userService.save(userDto);
    }


    @GetMapping("/userinfo")
    public UserDto getCurrentUser(@AuthenticationPrincipal Jwt jwt){
        User user = userService.findByName((String) jwt.getClaims().get("name")).orElseThrow(() -> new UsernameNotFoundException((String) jwt.getClaims().get("username")));
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
