package org.ac.cst8277.du.yuhan.usermgmt.services;

import org.ac.cst8277.du.yuhan.usermgmt.entities.Token;
import org.ac.cst8277.du.yuhan.usermgmt.entities.User;
import org.ac.cst8277.du.yuhan.usermgmt.repositories.TokenRepository;
import org.ac.cst8277.du.yuhan.usermgmt.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public AuthService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }

        String token = UUID.randomUUID().toString();

        Token t = new Token();
        t.setTokenValue(token);
        t.setUser(user);

        tokenRepository.save(t);

        return token;
    }

    // NEW: returns userId
    public Long validateAndGetUserId(String token) {
        Token t = tokenRepository.findByTokenValue(token);
        if (t == null) {
            return null;
        }
        return t.getUser().getId();
    }
}
