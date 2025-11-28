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

    /**
     * LOGIN endpoint.
     * Validates username and password, then generates a JWT.
     *
     * Body example:
     * {
     *   "username": "yuhan",
     *   "password": "123456"
     * }
     */
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

    /**
     * VALIDATE TOKEN endpoint.
     * Used by other microservices (e.g., TweetService) to validate JWTs.
     *
     * Request body example:
     * {
     *   "token": "eyJhbGciOiJIUzI1NiJ9..."
     * }
     */
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");

        try {
            // Validate token (throws exception if invalid or expired)
            JwtUtil.validateToken(token);
            return ResponseEntity.ok("VALID");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("INVALID");
        }
    }

}
