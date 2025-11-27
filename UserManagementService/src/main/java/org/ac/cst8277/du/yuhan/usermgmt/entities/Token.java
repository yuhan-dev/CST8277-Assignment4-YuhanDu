package org.ac.cst8277.du.yuhan.usermgmt.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tokenValue;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
