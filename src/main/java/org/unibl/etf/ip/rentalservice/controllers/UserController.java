package org.unibl.etf.ip.rentalservice.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.rentalservice.core.CrudController;
import org.unibl.etf.ip.rentalservice.model.dto.User;
import org.unibl.etf.ip.rentalservice.model.requests.UserRequest;
import org.unibl.etf.ip.rentalservice.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController extends CrudController<Integer, UserRequest, User> {

    public UserController(UserService userService) {
        super(User.class, userService);
    }

}

