package org.ac.cst8277.du.yuhan.tweetservice.controllers;

import org.ac.cst8277.du.yuhan.tweetservice.dto.CreateTweetRequest;
import org.ac.cst8277.du.yuhan.tweetservice.entities.Tweet;
import org.ac.cst8277.du.yuhan.tweetservice.repositories.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    @Autowired
    private WebClient userManagementClient;

    @Autowired
    private TweetRepository tweetRepository;

    // ---------------------------
    // POST /tweets (Create Tweet)
    // ---------------------------
    @PostMapping
    public ResponseEntity<?> createTweet(@RequestBody CreateTweetRequest request) {

        // 1. Validate token with UserManagementService (8081)
        Long userId = userManagementClient
                .post()
                .uri("/auth/validate")
                .bodyValue(request.getToken())
                .retrieve()
                .bodyToMono(Long.class)
                .block();

        if (userId == null) {
            return ResponseEntity.status(401).body("Invalid token");
        }

        // 2. Save Tweet
        Tweet tweet = new Tweet();
        tweet.setProducerId(userId);
        tweet.setContent(request.getContent());

        Tweet savedTweet = tweetRepository.save(tweet);

        return ResponseEntity.ok(savedTweet);
    }

    // ---------------------------
    // GET /tweets
    // ---------------------------
    @GetMapping
    public List<Tweet> getAllTweets() {
        return tweetRepository.findAll();
    }

    // ---------------------------
    // GET /tweets/producer/{producerId}
    // ---------------------------
    @GetMapping("/producer/{producerId}")
    public List<Tweet> getTweetsByProducer(@PathVariable Long producerId) {
        return tweetRepository.findByProducerId(producerId);
    }
}