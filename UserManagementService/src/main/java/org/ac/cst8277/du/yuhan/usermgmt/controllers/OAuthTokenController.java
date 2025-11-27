package org.ac.cst8277.du.yuhan.usermgmt.controllers;

import org.ac.cst8277.du.yuhan.usermgmt.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class OAuthTokenController {

    private final AuthService authService;

    public OAuthTokenController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * VALIDATE TOKEN
     * Body:
     * {
     *   "token": "xxxx-xxxx-uuid"
     * }
     * Response:
     * {
     *   "valid": true/false,
     *   "userId": 1   // only when valid = true
     * }
     */
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody TokenValidationRequest request) {
        Long userId = authService.validateAndGetUserId(request.getToken());
        if (userId == null) {
            return ResponseEntity.status(401).body(Map.of("valid", false));
        }
        return ResponseEntity.ok(
                Map.of(
                        "valid", true,
                        "userId", userId
                )
        );
    }
}
