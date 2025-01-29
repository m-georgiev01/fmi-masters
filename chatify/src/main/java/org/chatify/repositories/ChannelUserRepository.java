package org.chatify.repositories;

import org.chatify.models.entities.ChannelUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ChannelUserRepository extends JpaRepository<ChannelUser, Integer> {
    ChannelUser findByUserIdAndChannelId(int userId, int channelId);
    ArrayList<ChannelUser> findByChannelId(int channelId);
}
