package org.rentacarapi.repositories;

import org.rentacarapi.mappers.ClientRowMapper;
import org.rentacarapi.models.entities.Client;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class ClientRepository {
    private final JdbcTemplate db;

    public ClientRepository(JdbcTemplate db) {
        this.db = db;
    }

    public Client fetch(int id) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT id, name, has_accidents ")
                .append("FROM td_clients ")
                .append("WHERE is_deleted = FALSE AND id = ?");

        ArrayList<Client> collection = (ArrayList<Client>) this.db.query(query.toString(), new Object[]{id}, new ClientRowMapper());
        if (collection.isEmpty()) {
            return null;
        }

        return collection.get(0);
    }
}
