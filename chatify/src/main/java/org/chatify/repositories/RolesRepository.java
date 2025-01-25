package org.chatify.repositories;

import org.chatify.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, Integer> {
    Role findByNameIgnoreCase(String name);
}
