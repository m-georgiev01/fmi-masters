package org.example.p02solarparkapi.repositories;

import org.example.p02solarparkapi.entities.Customer;
import org.example.p02solarparkapi.entities.Project;
import org.example.p02solarparkapi.mappers.CustomerRowMapper;
import org.example.p02solarparkapi.mappers.ProjectRowMapper;
import org.example.p02solarparkapi.system.db.QueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepository {
    private final QueryBuilder<Project> db;

    public ProjectRepository(QueryBuilder<Project> db) {
        this.db = db;
    }

    public boolean create(Project project) {
        return db.into(Project.TABLE_NAME)
                .withValue(Project.columns.NAME, project.getName())
                .withValue(Project.columns.COST, project.getCost())
                .withValue(Project.columns.CUSTOMER_ID, project.getCustomerId())
                .insert();
    }

    public List<Project> fetchAll(int customerId) {
        return this.db.selectAll()
                .from(Project.TABLE_NAME)
                .where(Project.columns.IS_ACTIVE, 1)
                .andWhere(Project.columns.CUSTOMER_ID, customerId)
                .fetchAll(new ProjectRowMapper());
    }

    public Project fetch(int id) {
        return this.db.selectAll()
                .from(Project.TABLE_NAME)
                .where(Project.columns.IS_ACTIVE, 1)
                .andWhere(Project.columns.ID, id)
                .fetch(new ProjectRowMapper());
    }

    public boolean update(Project project) {
        int resultCount = this.db.updateTable(Project.TABLE_NAME)
                .set(Project.columns.NAME, project.getName())
                .set(Project.columns.COST, project.getCost())
                .set(Project.columns.CUSTOMER_ID, project.getCustomerId())
                .where(Project.columns.IS_ACTIVE, 1)
                .andWhere(Project.columns.ID, project.getId())
                .update();

        return resultCount == 1;
    }

    public boolean delete(int id){
        int resultCount = this.db.deleteTable(Project.TABLE_NAME)
                .where(Project.columns.ID, id)
                .delete();

        return resultCount == 1;
    }
}
