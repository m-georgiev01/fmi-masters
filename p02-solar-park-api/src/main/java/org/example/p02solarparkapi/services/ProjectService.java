package org.example.p02solarparkapi.services;

import org.example.p02solarparkapi.entities.Project;
import org.example.p02solarparkapi.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final CustomerService customerService;

    public ProjectService(ProjectRepository projectRepository, CustomerService customerService) {
        this.projectRepository = projectRepository;
        this.customerService = customerService;
    }

    public boolean createProject(Project project) {
        return this.projectRepository.create(project);
    }

    public List<Project> getAllProjectsByCustomerId(int customerId) {
        return this.projectRepository.fetchAll(customerId);
    }

    public Project GetById(int id) {
        return this.projectRepository.fetch(id);
    }

    public boolean updateProject(Project project) {
        return projectRepository.update(project);
    }

    public boolean removeProject(int id){
        return this.projectRepository.delete(id);
    }
}
