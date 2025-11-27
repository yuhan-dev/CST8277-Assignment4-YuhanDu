package org.ac.cst8277.du.yuhan.tweetservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tweets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long producerId;

    @Column(nullable = false)
    private String content;
}
