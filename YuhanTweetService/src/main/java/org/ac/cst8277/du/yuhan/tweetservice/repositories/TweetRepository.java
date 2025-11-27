package org.ac.cst8277.du.yuhan.tweetservice.repositories;

import org.ac.cst8277.du.yuhan.tweetservice.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
    List<Tweet> findByProducerId(Long producerId);
}
