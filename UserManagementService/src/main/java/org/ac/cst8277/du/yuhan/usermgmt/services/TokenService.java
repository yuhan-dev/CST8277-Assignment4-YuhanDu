package org.ac.cst8277.du.yuhan.usermgmt.services;

import org.ac.cst8277.du.yuhan.usermgmt.entities.User;
import org.ac.cst8277.du.yuhan.usermgmt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TokenService {

    // Simple in-memory token store
    private Map<String, Long> tokenStore = new HashMap<>();

    @Autowired
    private UserRepository userRepository;

    public String generateTokenForUser(User user) {
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, user.getId());
        return token;
    }

    public Optional<User> findUserByToken(String token) {
        Long userId = tokenStore.get(token);
        if (userId == null) {
            return Optional.empty();
        }
        return userRepository.findById(userId);
    }
}
