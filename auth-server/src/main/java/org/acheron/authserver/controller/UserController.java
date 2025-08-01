package org.acheron.authserver.controller;

import lombok.RequiredArgsConstructor;
import org.acheron.authserver.dto.UserGetDto;
import org.acheron.authserver.user.User;
import org.acheron.authserver.user.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/current")
    public UserGetDto getCurrentUser(Principal principal){
        User user = userService.findByName(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(principal.getName()));
        return new UserGetDto(user.getUsername(),user.getEmail(),user.getRole());
    }
}
