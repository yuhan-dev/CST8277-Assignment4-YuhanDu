package org.ac.cst8277.du.yuhan.usermgmt.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TweetClient {

    private final WebClient webClient;

    public TweetClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8082")
                .build();
    }

    public Object getTweetsByProducer(Long producerId) {
        return webClient.get()
                .uri("/tweets/producer/{id}", producerId)
                .retrieve()
                .bodyToMono(Object.class)
                .block(); // block() turns reactive to normal response
    }
}
