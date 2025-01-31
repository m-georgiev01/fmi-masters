package org.chatify.repositories;

import org.chatify.models.dtos.UserDTO;
import org.chatify.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndIsActiveTrue(String username);
    ArrayList<UserDTO> findByUsernameContainingIgnoreCaseAndIsActiveTrue(String username);
    User findById(int id);
}
