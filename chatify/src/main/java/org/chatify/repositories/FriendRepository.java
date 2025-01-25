package org.chatify.repositories;

import org.chatify.models.entities.Friend;
import org.chatify.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Integer> {
    boolean existsByFirstUserAndSecondUser(User firstUser, User secondUser);
}
