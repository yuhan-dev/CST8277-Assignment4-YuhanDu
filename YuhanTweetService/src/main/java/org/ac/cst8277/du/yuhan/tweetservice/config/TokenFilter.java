package org.ac.cst8277.du.yuhan.tweetservice.config;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class TokenFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        System.out.println(">>> TokenFilter TRIGGERED, path = " +
                exchange.getRequest().getURI().getPath());

        return chain.filter(exchange);
    }
}
