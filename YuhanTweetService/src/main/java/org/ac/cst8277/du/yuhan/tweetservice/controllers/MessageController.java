package org.ac.cst8277.du.yuhan.tweetservice.controllers;

import org.ac.cst8277.du.yuhan.tweetservice.entities.Message;
import org.ac.cst8277.du.yuhan.tweetservice.services.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/{producerId}")
    public Message createMessage(
            @PathVariable Long producerId,
            @RequestBody String content) {
        return messageService.create(producerId, content);
    }

    @GetMapping
    public List<Message> getAll() {
        return messageService.getAll();
    }

    @GetMapping("/producer/{producerId}")
    public List<Message> getByProducer(@PathVariable Long producerId) {
        return messageService.getByProducer(producerId);
    }

    @GetMapping("/subscriber/{subscriberId}")
    public List<Message> getForSubscriber(@PathVariable Long subscriberId) {
        return messageService.getForSubscriber(subscriberId);
    }
}
