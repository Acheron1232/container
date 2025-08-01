package org.acheron.authserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acheron.authserver.user.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetDto {
    private String username;
    private String email;
    private User.Role role;
}
