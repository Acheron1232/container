package org.acheron.authserver.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acheron.authserver.entity.Token;
import org.acheron.authserver.entity.User;
import org.acheron.authserver.repo.TokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public Token generateConfirnationToken(User user) {
        Token token = new Token(null, UUID.randomUUID().toString(), user, LocalDateTime.now().plusHours(24), Token.TokenType.CONFIRM);
        log.info("Generating confirmation token for {}", user.getUsername());
        return tokenRepository.save(token);
    }

    public Optional<Token> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public Token generateResetToken(User user) {
        Token token = new Token(null, UUID.randomUUID().toString(), user, LocalDateTime.now().plusHours(24), Token.TokenType.RESET);
        log.info("Generating reset token for {}", user.getUsername());
        return tokenRepository.save(token);
    }

    public void delete(Token token) {
        tokenRepository.delete(token);
    }
}
