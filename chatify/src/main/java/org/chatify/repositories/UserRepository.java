package org.chatify.repositories;

import org.chatify.models.dtos.UserDTO;
import org.chatify.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    ArrayList<UserDTO> findByUsernameContainingIgnoreCase(String username);
    User findById(int id);
}
