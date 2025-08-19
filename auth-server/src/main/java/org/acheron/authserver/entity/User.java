package org.acheron.authserver.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    private Boolean isEmailVerified;
    @Enumerated(EnumType.STRING)
    private AuthMethod authMethod;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public enum Role implements GrantedAuthority {
        USER, ADMIN;
        @Override
        public String getAuthority() {
            return name();
        }
    }

    public enum AuthMethod {
        DEFAULT, GOOGLE, GITHUB

    }
}
