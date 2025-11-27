package org.ac.cst8277.du.yuhan.usermgmt.repositories;

import org.ac.cst8277.du.yuhan.usermgmt.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
