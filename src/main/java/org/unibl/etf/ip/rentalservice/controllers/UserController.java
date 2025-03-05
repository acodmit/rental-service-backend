package org.unibl.etf.ip.rentalservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.rentalservice.core.CrudController;
import org.unibl.etf.ip.rentalservice.model.dto.User;
import org.unibl.etf.ip.rentalservice.model.entities.UserEntity;
import org.unibl.etf.ip.rentalservice.model.requests.UserRequest;
import org.unibl.etf.ip.rentalservice.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController extends CrudController<Integer, UserRequest, User> {

    private final UserService userService;

    public UserController(UserService userService) {
        super(User.class, userService);
        this.userService = userService;
    }

    // Endpoint to get user info by username
    @GetMapping("/user-info/{username}")
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        User user = userService.getUserInfo(username);
        return ResponseEntity.ok(user);
    }
}

