package org.ac.cst8277.du.yuhan.usermgmt.repositories;

import org.ac.cst8277.du.yuhan.usermgmt.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}

