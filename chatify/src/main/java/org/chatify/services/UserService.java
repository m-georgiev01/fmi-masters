package org.chatify.services;

import org.chatify.models.dtos.UserDTO;
import org.chatify.models.entities.User;
import org.chatify.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ArrayList<UserDTO> getUsersByPartOfUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCaseAndIsActiveTrue(username);
    }

    public UserDTO registerUser(String username, String password) {
        var existingUser = userRepository.findByUsernameAndIsActiveTrue(username);
        if (existingUser != null) {
            throw new IllegalArgumentException("User not found");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setActive(true);

        userRepository.save(newUser);
        return new UserDTO(newUser.getId(), newUser.getUsername(), newUser.isActive());
    }

    public UserDTO loginUser(String username, String password) {
        var user = userRepository.findByUsernameAndIsActiveTrue(username);

        if (user == null) {
            throw new IllegalArgumentException("Username not found");
        }

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Wrong password");
        }

        return new UserDTO(user.getId(), user.getUsername(), user.isActive());
    }

    public User getUserById(int id) {
        var user = userRepository.findById(id);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        return user;
    }
}
