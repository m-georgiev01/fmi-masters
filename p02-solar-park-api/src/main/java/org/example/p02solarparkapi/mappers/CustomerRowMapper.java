package org.example.p02solarparkapi.mappers;

import org.example.p02solarparkapi.entities.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setName(rs.getString("name"));
        customer.setId(rs.getInt("id"));
        customer.setNumberOfProjects(rs.getInt("number_of_projects"));

        return customer;
    }
}
