package org.ac.cst8277.du.yuhan.tweetservice.controllers;

import org.ac.cst8277.du.yuhan.tweetservice.entities.Subscription;
import org.ac.cst8277.du.yuhan.tweetservice.services.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/{subscriberId}/follow/{producerId}")
    public ResponseEntity<?> subscribe(
            @PathVariable Long subscriberId,
            @PathVariable Long producerId) {
        Subscription sub = subscriptionService.subscribe(subscriberId, producerId);
        return ResponseEntity.ok(sub);
    }

    @DeleteMapping("/{subscriberId}/unfollow/{producerId}")
    public ResponseEntity<?> unsubscribe(
            @PathVariable Long subscriberId,
            @PathVariable Long producerId) {
        subscriptionService.unsubscribe(subscriberId, producerId);
        return ResponseEntity.ok("Unsubscribed");
    }

    @GetMapping("/{subscriberId}")
    public ResponseEntity<List<Subscription>> getSubscriptions(
            @PathVariable Long subscriberId) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionsForSubscriber(subscriberId));
    }

    @GetMapping("/followers/{producerId}")
    public ResponseEntity<List<Subscription>> getFollowers(
            @PathVariable Long producerId) {
        return ResponseEntity.ok(subscriptionService.getFollowers(producerId));
    }
}
