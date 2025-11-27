package org.ac.cst8277.du.yuhan.usermgmt.repositories;

import org.ac.cst8277.du.yuhan.usermgmt.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByTokenValue(String tokenValue);
}

