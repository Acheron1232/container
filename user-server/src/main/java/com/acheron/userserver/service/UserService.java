package com.acheron.userserver.service;

import com.acheron.userserver.dto.UserCreateDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.acheron.userserver.entity.User;
import com.acheron.userserver.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Optional<User> findByName(String name) {
        return userRepository.findByUsername(name);
    }

//    @Transactional
//    public User save(User user) {
//        if(user.getPassword()!=null){
//
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//        }
//        return userRepository.save(user);
//    }

    //save from oauth
    @Transactional
    public void save(UserCreateDto user) {
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(new User(null, user.getEmail(), user.getUsername(), user.getDisplayName(), null,user.getImage(), user.isEmailVerified(), User.AuthMethod.valueOf(user.getAuthMethod()), User.Role.valueOf(user.getRole()), user.getPassword()));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

//    public UserDetails loadOidcUser(DefaultOidcUser oidcUser){
//        String email = oidcUser.getEmail();
//        if(!existsByEmail(email)){
//            return save(new User(null,email,oidcUser.getFullName(),oidcUser.getNickName(),oidcUser.getProfile(),null,false, User.AuthMethod.DEFAULT, User.Role.USER,null));
//        }else return findByEmail(email).orElseThrow(()->new UsernameNotFoundException(email));
//    }
//    @SneakyThrows
//    public UserDetails loadOAuthUser(DefaultOAuth2User oAuth2User)  {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!(authentication instanceof OAuth2AuthenticationToken oauthToken)) {
//            throw new IllegalStateException("Not an OAuth2 authentication");
//        }
//
//        String email = getPrimaryEmailForGitHub(oauthToken);
//        if (!existsByEmail(email)) {
//            return save(new User(
//                    null,
//                    email,
//                    oAuth2User.getAttribute("name"),
//                    oAuth2User.getAttribute("login"),
//                    oAuth2User.getAttribute("avatar_url"),
//                    null,
//                    false,
//                    User.AuthMethod.DEFAULT,
//                    User.Role.USER,
//                    null
//            ));
//        } else {
//            return findByEmail(email)
//                    .orElseThrow(() -> new UsernameNotFoundException(email));
//        }
//    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

//    public ResponseEntity<String> confirmEmail(Principal principal) {
//        User user = userService.findByUsername(principal.getName());
//        if (user.getIsEmailVerified()){
//            return ResponseEntity.ok("Email verified");
//        }
//        LoginToken token = confirmationTokenService.generateConfirmationToken(user);
//        String html = """
//                <div>
//                      <style>
//                         html {
//                            box-sizing: border-box;
//                         }
//                         *,
//                         *::before,
//                         *::after {
//                            padding: 0;
//                            margin: 0;
//                            box-sizing: inherit;
//                         }
//                         .crad-wrapper {
//                            display: flex;
//                            justify-content: center;
//                            align-items: center;
//                            height: 350px;
//                            width: 100%;
//                            background: #111113;
//                            font-family: sans-serif;
//                            color: #edeef0;
//                         }
//                         .card {
//                            border-radius: 8px;
//                            border: 1px solid #363a3e;
//                            background: #18191b;
//                            padding: 12px;
//                            display: flex;
//                            flex-direction: column;
//                            gap: 0.5rem;
//                         }
//
//                         .card-gui {
//                            display: flex;
//                            justify-content: center;
//                            align-items: center;
//                         }
//
//                         .card-gui button {
//                            background: #3c9eff;
//                            color: #edeef0;
//                            padding: 0.25rem 1rem;
//                            border-radius: 4px;
//                            border: 1px solid #1879db;
//                            cursor: pointer;
//                            font-size: 1.25rem;
//                         }
//
//                         .card-gui button:hover {
//                            background: #1f7fdf;
//                         }
//                      </style>
//                      <div class="crad-wrapper">
//                         <div class="card">
//                            <h1>Sabaody <b style="color: #3c9eff">Space</b></h1>
//                            <p>Click this button to confirm your email</p>
//                            <div class="card-gui">
//                               <a href="urll">
//                                  <button>Confirm email</button>
//                               </a>
//                            </div>
//                         </div>
//                      </div>
//                   </div>
//                """.replaceFirst("urll",serverDomain+"/confirm?token="+token.getToken());
//        emailService.sendMessage(user.getEmail(), html,"Confirm email");
//        return ResponseEntity.ok("Email sent successfully");
//    }
//
//    public ResponseEntity<String> confirm(String token) {
//        confirmationTokenService.getToken(token).ifPresentOrElse(
//                (confirmationToken)->{
//                    if (!confirmationToken.getTokenType().equals(Token.CONFIRM)){
//                        throw new BadCredentialsException("Token is not confirm");
//                    }
//                    if(confirmationToken.getExpiredAt().isAfter(LocalDateTime.now())){
//                        User user = confirmationToken.getUser();
//                        user.setIsEmailVerified(true);
//                        userService.update(user);
//                        confirmationTokenService.delete(confirmationToken);
//                    }else {
//                        throw new BadCredentialsException("Token expired");
//                    }
//                },()->{
//                    throw new BadCredentialsException("Invalid token");
//                }
//        );
//        return ResponseEntity.ok("Email confirmed");
//    }
//
//    public ResponseEntity<String> resetPassword(String email) {
//        boolean b = userService.existsByEmail(email);
//        if (!b){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email does not register");
//        }
//        User user = userService.findByEmailOptional(email).orElseThrow();
//        LoginToken token = confirmationTokenService.generateResetToken(user);
//        String html = """
//                <div>
//                      <style>
//                         html {
//                            box-sizing: border-box;
//                         }
//                         *,
//                         *::before,
//                         *::after {
//                            padding: 0;
//                            margin: 0;
//                            box-sizing: inherit;
//                         }
//                         .crad-wrapper {
//                            display: flex;
//                            justify-content: center;
//                            align-items: center;
//                            height: 350px;
//                            width: 100%;
//                            background: #111113;
//                            font-family: sans-serif;
//                            color: #edeef0;
//                         }
//                         .card {
//                            border-radius: 8px;
//                            border: 1px solid #363a3e;
//                            background: #18191b;
//                            padding: 12px;
//                            display: flex;
//                            flex-direction: column;
//                            gap: 0.5rem;
//                         }
//
//                         .card-gui {
//                            display: flex;
//                            justify-content: center;
//                            align-items: center;
//                         }
//
//                         .card-gui button {
//                            background: #3c9eff;
//                            color: #edeef0;
//                            padding: 0.25rem 1rem;
//                            border-radius: 4px;
//                            border: 1px solid #1879db;
//                            cursor: pointer;
//                            font-size: 1.25rem;
//                         }
//
//                         .card-gui button:hover {
//                            background: #1f7fdf;
//                         }
//                      </style>
//                      <div class="crad-wrapper">
//                         <div class="card">
//                            <h1>Sabaody <b style="color: #3c9eff">Space</b></h1>
//                            <p>Click this button to reset your password</p>
//                            <div class="card-gui">
//                               <a href="urll">
//                                  <button>Reset password</button>
//                               </a>
//                            </div>
//                         </div>
//                      </div>
//                   </div>
//                """.replaceFirst("urll",domain+"/resetPassword?token="+token.getToken());
//        emailService.sendMessage(user.getEmail(), html,"Reset password");
//        return ResponseEntity.ok("Reset password sent successfully");
//    }
//
//    public ResponseEntity<String> reset(String token) {
//        AtomicReference<String> newPassword = new AtomicReference<>();
//        confirmationTokenService.getToken(token).ifPresentOrElse(
//                (confirmationToken)->{
//                    if (!confirmationToken.getTokenType().equals(Token.RESET)){
//                        throw new BadCredentialsException("Token is not reset");
//                    }
//                    if(confirmationToken.getExpiredAt().isAfter(LocalDateTime.now())){
//                        String substring = UUID.randomUUID().toString().substring(0, 10);
//                        String password = passwordEncoder.encode(substring);
//                        User user = confirmationToken.getUser();
//                        user.setPassword(password);
//                        userService.update(user);
//                        confirmationTokenService.delete(confirmationToken);
//                        newPassword.set(substring);
//
//                    }else {
//                        throw new BadCredentialsException("Token expired");
//                    }
//                },()->{
//                    throw new BadCredentialsException("Invalid token");
//                }
//        );
//        return ResponseEntity.ok(newPassword.get());
//    }
}
