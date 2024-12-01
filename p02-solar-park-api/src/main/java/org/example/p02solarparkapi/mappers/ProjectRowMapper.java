package org.example.p02solarparkapi.mappers;

import org.example.p02solarparkapi.entities.Project;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectRowMapper implements RowMapper<Project> {
    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
        Project project = new Project();
        project.setId(rs.getInt(Project.columns.ID));
        project.setName(rs.getString(Project.columns.NAME));
        project.setCustomerId(rs.getInt(Project.columns.CUSTOMER_ID));
        project.setCost(rs.getDouble(Project.columns.COST));

        return project;
    }
}
