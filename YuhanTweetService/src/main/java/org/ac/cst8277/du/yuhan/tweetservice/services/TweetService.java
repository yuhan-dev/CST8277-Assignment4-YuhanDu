package org.ac.cst8277.du.yuhan.tweetservice.services;

import org.ac.cst8277.du.yuhan.tweetservice.entities.Tweet;
import org.ac.cst8277.du.yuhan.tweetservice.repositories.TweetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;

    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public Tweet createTweet(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    public List<Tweet> getTweetsByProducer(Long producerId) {
        return tweetRepository.findByProducerId(producerId);
    }

    public List<Tweet> getAllTweets() {
        return tweetRepository.findAll();
    }
}
