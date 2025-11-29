package org.ac.cst8277.du.yuhan.usermgmt.controllers;

import org.ac.cst8277.du.yuhan.usermgmt.entities.User;
import org.ac.cst8277.du.yuhan.usermgmt.repositories.UserRepository;
import org.ac.cst8277.du.yuhan.usermgmt.security.JwtUtil;
import org.ac.cst8277.du.yuhan.usermgmt.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User req) {

        // Validate user credentials using existing AuthService logic
        boolean success = authService.login(req.getUsername(), req.getPassword()) != null;
        if (!success) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // Generate JWT token
        String jwt = JwtUtil.generateToken(req.getUsername());

        // Prepare JSON response
        Map<String, Object> response = new HashMap<>();
        response.put("username", req.getUsername());
        response.put("token", jwt);

        // Return token + username for client use
        return ResponseEntity.ok(response);
    }

    /**
     * REGISTER endpoint.
     * Creates a new user record.
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

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token) {
        try {
            // test token
            JwtUtil.validateToken(token);

            // getting the user name from token
            String username = JwtUtil.extractUsername(token);

            Map<String, String> result = new HashMap<>();
            result.put("username", username);

            return ResponseEntity.ok(result);   // 返回 {"username": "yuhan"}
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }


}
