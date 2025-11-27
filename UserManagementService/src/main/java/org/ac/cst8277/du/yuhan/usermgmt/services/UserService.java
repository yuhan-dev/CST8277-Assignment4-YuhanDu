package org.ac.cst8277.du.yuhan.usermgmt.services;

import org.ac.cst8277.du.yuhan.usermgmt.entities.User;
import org.ac.cst8277.du.yuhan.usermgmt.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Token store: token -> username
    private static final Map<String, String> tokenStore = new ConcurrentHashMap<>();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Login: check username + password, generate token
     */
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Wrong password");
        }

        // Generate token
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, username);

        return token;
    }

    /**
     * Validate token (TweetService uses this)
     */
    public boolean validateToken(String token) {
        return tokenStore.containsKey(token);
    }
}


