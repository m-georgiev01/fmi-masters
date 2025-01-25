package org.chatify.models.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "channels")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_direct_message", nullable = false)
    private boolean isDirectMessage = false;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @OneToMany(mappedBy = "channel")
    private Set<ChannelUser> participants = new HashSet<>();

    public Channel() {}

    public Channel(int id, String name, boolean isDirectMessage, boolean isActive, Set<ChannelUser> participants) {
        this.id = id;
        this.name = name;
        this.isDirectMessage = isDirectMessage;
        this.isActive = isActive;
        this.participants = participants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDirectMessage() {
        return isDirectMessage;
    }

    public void setDirectMessage(boolean directMessage) {
        isDirectMessage = directMessage;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Set<ChannelUser> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<ChannelUser> participants) {
        this.participants = participants;
    }
}
