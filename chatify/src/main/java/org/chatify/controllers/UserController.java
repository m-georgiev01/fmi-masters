package org.chatify.controllers;

import org.chatify.models.entities.User;
import org.chatify.http.AppResponse;
import org.chatify.models.requests.AddFriendRequest;
import org.chatify.services.FriendService;
import org.chatify.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserController {
    private final UserService userService;
    private final FriendService friendService;

    public UserController(UserService userService, FriendService friendService) {
        this.userService = userService;
        this.friendService = friendService;
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

    @PostMapping("/users/friends/add")
    public ResponseEntity<?> addFriend(@RequestBody AddFriendRequest request) {
        try {
            this.friendService.addFriend(request);

            return AppResponse.success().withCode(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return AppResponse.error().withCode(HttpStatus.BAD_REQUEST).withMessage(e.getMessage()).build();
        } catch (Exception e) {
            return AppResponse.error().withCode(HttpStatus.INTERNAL_SERVER_ERROR).withMessage(e.getMessage()).build();
        }
    }
}
