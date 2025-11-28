package org.ac.cst8277.du.yuhan.tweetservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TokenFilter implements WebFilter {

    @Autowired
    private WebClient userMgmtClient;

    /**
     * This WebFilter intercepts every incoming WebFlux request.
     * It extracts the Authorization token, sends a synchronous (blocking)
     * validation request to UserManagementService, and allows or rejects
     * the request based on the validation result.
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        // Extract Authorization header
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (token == null || token.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        try {
            // Validate token by calling UserManagementService
            Boolean isValid = userMgmtClient.get()
                    .uri("/auth/validate?token=" + token)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .onErrorResume(e -> Mono.just(false)) // if service fails
                    .block(); // REQUIRED by Assignment 4 (synchronous)

            if (Boolean.TRUE.equals(isValid)) {
                return chain.filter(exchange); // allow request to proceed
            } else {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}

