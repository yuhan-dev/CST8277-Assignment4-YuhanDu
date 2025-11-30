package org.ac.cst8277.du.yuhan.tweetservice.services;

import org.ac.cst8277.du.yuhan.tweetservice.entities.Subscription;
import org.ac.cst8277.du.yuhan.tweetservice.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Subscription subscribe(Long subscriberId, Long producerId) {
        return subscriptionRepository
                .findBySubscriberIdAndProducerId(subscriberId, producerId)
                .orElseGet(() -> subscriptionRepository.save(new Subscription(subscriberId, producerId)));
    }

    public void unsubscribe(Long subscriberId, Long producerId) {
        subscriptionRepository
                .findBySubscriberIdAndProducerId(subscriberId, producerId)
                .ifPresent(subscriptionRepository::delete);
    }

    public List<Subscription> getSubscriptionsForSubscriber(Long subscriberId) {
        return subscriptionRepository.findBySubscriberId(subscriberId);
    }

    public List<Subscription> getFollowers(Long producerId) {
        return subscriptionRepository.findByProducerId(producerId);
    }
}
