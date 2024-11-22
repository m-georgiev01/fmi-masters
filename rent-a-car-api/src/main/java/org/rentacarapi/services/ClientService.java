package org.rentacarapi.services;

import org.rentacarapi.models.entities.Client;
import org.rentacarapi.repositories.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client getById(int id) {
        return this.clientRepository.fetch(id);
    }
}
