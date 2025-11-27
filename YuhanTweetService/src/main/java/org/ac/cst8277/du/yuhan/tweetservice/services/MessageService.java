package org.ac.cst8277.du.yuhan.tweetservice.services;

import org.ac.cst8277.du.yuhan.tweetservice.entities.Message;
import org.ac.cst8277.du.yuhan.tweetservice.repositories.MessageRepository;
import org.ac.cst8277.du.yuhan.tweetservice.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final SubscriptionRepository subscriptionRepository;

    public MessageService(MessageRepository mr, SubscriptionRepository sr) {
        this.messageRepository = mr;
        this.subscriptionRepository = sr;
    }

    public Message create(Long producerId, String content) {
        return messageRepository.save(new Message(producerId, content));
    }

    public List<Message> getAll() {
        return messageRepository.findAll();
    }

    public List<Message> getByProducer(Long producerId) {
        return messageRepository.findByProducerId(producerId);
    }

    public List<Message> getForSubscriber(Long subscriberId) {
        var subscriptions = subscriptionRepository.findBySubscriberId(subscriberId);
        var producerIds = subscriptions.stream().map(s -> s.getProducerId()).toList();
        return producerIds.isEmpty() ? List.of() : messageRepository.findAll()
                .stream()
                .filter(m -> producerIds.contains(m.getProducerId()))
                .toList();
    }
}
