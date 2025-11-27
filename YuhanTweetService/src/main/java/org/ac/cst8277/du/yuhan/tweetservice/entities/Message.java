package org.ac.cst8277.du.yuhan.tweetservice.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column(nullable = false)
    private Long producerId;

    @Column(nullable = false)
    private String content;

    public Message() {}

    public Message(Long producerId, String content) {
        this.producerId = producerId;
        this.content = content;
    }

    public Long getMessageId() {
        return messageId;
    }

    public Long getProducerId() {
        return producerId;
    }

    public void setProducerId(Long producerId) {
        this.producerId = producerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

