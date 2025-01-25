package org.chatify.repositories;

import org.chatify.models.entities.ChannelUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelUserRepository extends JpaRepository<ChannelUser, Integer> {
    ChannelUser findByUserIdAndChannelId(int userId, int channelId);
}
