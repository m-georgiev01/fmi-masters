package org.chatify.controllers;

import org.chatify.models.entities.User;
import org.chatify.http.AppResponse;
import org.chatify.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5174")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users/search")
    public ResponseEntity<?> searchUsers(@RequestParam String username) {
        var users =  userService.getUsersByPartOfUsername(username);

        if (users.isEmpty()) {
            return AppResponse.error().withCode(HttpStatus.NOT_FOUND).build();
        }

        return AppResponse.success().withCode(HttpStatus.OK).withData(users).build();
    }

    @PostMapping("/users/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            var newUser = userService.registerUser(user.getUsername(), user.getPassword());

            return AppResponse.success().withCode(HttpStatus.CREATED).withData(newUser).build();
        }catch (IllegalArgumentException e) {
            return AppResponse.error().withCode(HttpStatus.BAD_REQUEST).withMessage(e.getMessage()).build();
        }
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            var userDb = userService.loginUser(user.getUsername(), user.getPassword());

            return AppResponse.success().withCode(HttpStatus.OK).withData(userDb).build();
        } catch (IllegalArgumentException e) {
            return AppResponse.error().withCode(HttpStatus.BAD_REQUEST).withMessage(e.getMessage()).build();
        }
    }
}
