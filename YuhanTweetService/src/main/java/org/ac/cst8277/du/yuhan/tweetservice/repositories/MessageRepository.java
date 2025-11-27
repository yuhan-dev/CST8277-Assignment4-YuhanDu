package org.ac.cst8277.du.yuhan.tweetservice.repositories;

import org.ac.cst8277.du.yuhan.tweetservice.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByProducerId(Long producerId);
}

