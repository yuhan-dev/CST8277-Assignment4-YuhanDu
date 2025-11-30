package org.ac.cst8277.du.yuhan.tweetservice.repositories;

import org.ac.cst8277.du.yuhan.tweetservice.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findBySubscriberId(Long subscriberId);

    List<Subscription> findByProducerId(Long producerId);

    Optional<Subscription> findBySubscriberIdAndProducerId(Long subscriberId, Long producerId);

}
