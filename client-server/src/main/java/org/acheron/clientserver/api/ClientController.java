package org.acheron.clientserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


  @RestController
public class ClientController {

      @GetMapping("/me")
      public OAuth2User getUser(@AuthenticationPrincipal OAuth2User principal) {
          return principal;
      }

      @GetMapping("/zxc")
      public String asd() {
          return "greeting";
      }
  }
