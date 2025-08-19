package org.acheron.authserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.acheron.authserver.dto.UserCreateDto;
import org.acheron.authserver.entity.User;
import org.acheron.authserver.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    @ResponseBody
    public void registrationApi(@RequestParam Map<String,String> params) {
        userService.save(new UserCreateDto(params.get("username"),params.get("email"),params.get("password"),
                false, User.Role.USER.toString(), User.AuthMethod.DEFAULT.toString()));
    }

    @GetMapping("/spa/logout")
    @ResponseBody
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(true); // не створюємо нову
        if (session != null) {
            session.invalidate(); // інвалідовуємо тільки існуючу
        }
        SecurityContextHolder.clearContext();
    }


    @GetMapping("/.well-known/appspecific/com.chrome.devtools.json")
    @ResponseBody
    public Map<String, Object> chromeDevToolsJson() {
        return Map.of();
    }
}