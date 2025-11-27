package org.ac.cst8277.du.yuhan.tweetservice.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Collections;
import java.util.Map;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final WebClient userManagementClient;

    public TokenInterceptor(WebClient userManagementClient) {
        this.userManagementClient = userManagementClient;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // 1. Read token from Authorization header
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null || token.isBlank()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 2. Call UserManagementService /auth/validate
        Map<String, Object> result =
                userManagementClient.post()
                        .uri("/auth/validate")
                        .bodyValue(Collections.singletonMap("token", token))
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                        // If the call fails (e.g. connection error), treat as invalid token
                        .onErrorReturn(Collections.singletonMap("valid", false))
                        .block();

        // 3. Check validation result
        if (result == null || !Boolean.TRUE.equals(result.get("valid"))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 4. Optionally store userId in request attributes for controllers
        Object userIdObj = result.get("userId");
        if (userIdObj instanceof Number) {
            long userId = ((Number) userIdObj).longValue();
            request.setAttribute("userId", userId);
        }

        return true;
    }
}
