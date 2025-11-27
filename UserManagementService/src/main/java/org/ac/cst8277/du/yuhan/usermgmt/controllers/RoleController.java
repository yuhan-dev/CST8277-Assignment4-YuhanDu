package org.ac.cst8277.du.yuhan.usermgmt.controllers;

import org.ac.cst8277.du.yuhan.usermgmt.entities.Role;
import org.ac.cst8277.du.yuhan.usermgmt.entities.User;
import org.ac.cst8277.du.yuhan.usermgmt.entities.UserRole;
import org.ac.cst8277.du.yuhan.usermgmt.repositories.RoleRepository;
import org.ac.cst8277.du.yuhan.usermgmt.repositories.UserRepository;
import org.ac.cst8277.du.yuhan.usermgmt.repositories.UserRoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public RoleController(RoleRepository roleRepository,
                          UserRepository userRepository,
                          UserRoleRepository userRoleRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @PostMapping("/assign")
    public ResponseEntity<String> assignRole(
            @RequestParam Long userId,
            @RequestParam String roleName) {

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Role role = roleRepository.findByRoleName(roleName);
        if (role == null) {
            return ResponseEntity.badRequest().body("Role not found");
        }

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRoleRepository.save(userRole);

        return ResponseEntity.ok("Role assigned successfully");
    }
}
