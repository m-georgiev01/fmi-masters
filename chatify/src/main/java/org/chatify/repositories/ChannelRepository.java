package org.chatify.repositories;

import org.chatify.models.dtos.ChannelDTO;
import org.chatify.models.entities.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {
    @Query("SELECT new org.chatify.models.dtos.ChannelDTO(c.id, c.name) FROM Channel c JOIN c.participants p WHERE c.isDirectMessage = true AND c.isActive = true AND p.user.id = :userId")
    ArrayList<ChannelDTO> findDirectMessageChannelsByUserId(int userId);

    @Query("SELECT new org.chatify.models.dtos.ChannelDTO(c.id, c.name) FROM Channel c JOIN c.participants p WHERE c.isDirectMessage = false AND c.isActive = true AND p.user.id = :userId")
    ArrayList<ChannelDTO> findGroupChannelsByUserId(int userId);

    Channel findByIdAndIsActiveTrue(int id);
}
