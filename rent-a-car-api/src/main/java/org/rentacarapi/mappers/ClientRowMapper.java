package org.rentacarapi.mappers;

import org.rentacarapi.models.entities.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRowMapper implements RowMapper<Client> {
    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        Client client = new Client();

        client.setId(rs.getInt("id"));
        client.setName(rs.getString("name"));
        client.setHasAccidents(rs.getBoolean("has_accidents"));

        return client;
    }
}
