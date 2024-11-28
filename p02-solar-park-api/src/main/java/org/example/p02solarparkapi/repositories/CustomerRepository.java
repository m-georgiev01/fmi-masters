package org.example.p02solarparkapi.repositories;

import org.example.p02solarparkapi.entities.Customer;
import org.example.p02solarparkapi.mappers.CustomerRowMapper;
import org.example.p02solarparkapi.system.db.QueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository {
    private final QueryBuilder<Customer> db;

    public CustomerRepository(QueryBuilder<Customer> db) {
        this.db = db;
    }

    public boolean create(Customer customer) {
        return db.into(Customer.TABLE_NAME)
                .withValue(Customer.columns.NAME, customer.getName())
                .insert();
    }

    public List<Customer> fetchAll() {
        return this.db.selectAll()
                .from(Customer.TABLE_NAME)
                .where(Customer.columns.IS_ACTIVE, 1)
                .fetchAll(new CustomerRowMapper());
    }

    public Customer fetch(int id) {
        return this.db.selectAll()
                .from(Customer.TABLE_NAME)
                .where(Customer.columns.IS_ACTIVE, 1)
                .andWhere(Customer.columns.ID, id)
                .fetch(new CustomerRowMapper());
    }

    public boolean update(Customer customer) {
        int resultCount = this.db.updateTable(Customer.TABLE_NAME)
                .set(Customer.columns.NAME, customer.getName())
                .set(Customer.columns.NUMBER_OF_PROJECTS, customer.getNumberOfProjects())
                .where(Customer.columns.IS_ACTIVE, 1)
                .andWhere(Customer.columns.ID, customer.getId())
                .update();

        return resultCount == 1;
    }

    public boolean delete(int id){
        int resultCount = this.db.deleteTable(Customer.TABLE_NAME)
                .where(Customer.columns.ID, id)
                .delete();

        return resultCount == 1;
    }
}
