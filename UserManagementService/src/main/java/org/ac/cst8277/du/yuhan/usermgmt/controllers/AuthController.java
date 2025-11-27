package org.ac.cst8277.du.yuhan.usermgmt.controllers;

import org.ac.cst8277.du.yuhan.usermgmt.entities.User;
import org.ac.cst8277.du.yuhan.usermgmt.repositories.UserRepository;
import org.ac.cst8277.du.yuhan.usermgmt.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    /**
     * LOGIN
     * Body example:
     * {
     *   "username": "yuhan",
     *   "password": "123"
     * }
     * Response: token string
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User req) {
        String token = authService.login(req.getUsername(), req.getPassword());
        if (token == null) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        // 这里直接返回 token 字符串，方便前端或 Postman 使用
        return ResponseEntity.ok(token);
    }

    /**
     * REGISTER USER
     * Body example:
     * {
     *   "username": "newUser",
     *   "password": "123"
     * }
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User saved = userRepository.save(user);
            return ResponseEntity.ok(saved);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            return ResponseEntity.status(400).body("Username already exists.");
        }
    }
}
