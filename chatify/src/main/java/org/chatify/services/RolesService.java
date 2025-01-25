package org.chatify.services;

import org.chatify.models.entities.Role;
import org.chatify.repositories.RolesRepository;
import org.springframework.stereotype.Service;

@Service
public class RolesService {
    private final RolesRepository rolesRepository;

    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public Role getRole(String roleName) {
        return rolesRepository.findByNameIgnoreCase(roleName);
    }
}
