package org.acheron.authserver.controller;

import lombok.RequiredArgsConstructor;
import org.acheron.authserver.user.User;
import org.acheron.authserver.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestC {
    private final UserService userService;
    @GetMapping("/")
    public String test(){
        userService.save(new User(null,"asd@asd.asd","asdadsasd", User.Role.USER,"zxczxczxc"));
        return "test";
    }
}
