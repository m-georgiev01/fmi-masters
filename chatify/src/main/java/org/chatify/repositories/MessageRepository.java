package org.chatify.repositories;

import org.chatify.models.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    ArrayList<Message> findByChannelIdOrderBySentAtAsc(int channelId);
}
