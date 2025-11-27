package org.ac.cst8277.du.yuhan.usermgmt.controllers;

import org.ac.cst8277.du.yuhan.usermgmt.entities.User;
import org.ac.cst8277.du.yuhan.usermgmt.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
