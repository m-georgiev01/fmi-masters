package org.chatify.repositories;

import org.chatify.dtos.UserDTO;
import org.chatify.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    ArrayList<UserDTO> findByUsernameContainingIgnoreCase(String username);
}
