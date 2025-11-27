package org.ac.cst8277.du.yuhan.tweetservice.services;

import org.ac.cst8277.du.yuhan.tweetservice.entities.Subscription;
import org.ac.cst8277.du.yuhan.tweetservice.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Subscription subscribe(Long subscriberId, Long producerId) {
        // 防止重复订阅
        subscriptionRepository.findBySubscriberIdAndProducerId(subscriberId, producerId)
                .ifPresent(s -> {
                    throw new RuntimeException("Already subscribed.");
                });

        Subscription sub = new Subscription();
        sub.setSubscriberId(subscriberId);
        sub.setProducerId(producerId);
        sub.setCreatedAt(LocalDateTime.now());

        return subscriptionRepository.save(sub);
    }

    public void unsubscribe(Long subscriberId, Long producerId) {
        subscriptionRepository.deleteBySubscriberIdAndProducerId(subscriberId, producerId);
    }

    public List<Subscription> getSubscriptionsForSubscriber(Long subscriberId) {
        return subscriptionRepository.findBySubscriberId(subscriberId);
    }

    public List<Subscription> getFollowers(Long producerId) {
        return subscriptionRepository.findByProducerId(producerId);
    }
}

