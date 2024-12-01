package org.example.p02solarparkapi.controllers;

import org.example.p02solarparkapi.entities.Project;
import org.example.p02solarparkapi.http.AppResponse;
import org.example.p02solarparkapi.services.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class ProjectController {
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/projects")
    public ResponseEntity<?> createNewProject(@RequestBody Project project) {

        HashMap<String, Object> response = new HashMap<>();

        if(this.projectService.createProject(project)) {

            return AppResponse.success()
                    .withMessage("Project created successfully")
                    .build();
        }

        return AppResponse.error()
                .withMessage("Project could not be created")
                .build();
    }

    @GetMapping("customers/{id}/projects")
    public ResponseEntity<?> fetchAllProjects(@PathVariable int id) {

        ArrayList<Project> collection = (ArrayList<Project>) this.projectService.getAllProjectsByCustomerId(id);

        return AppResponse.success()
                .withData(collection)
                .build();
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<?> fetchProject(@PathVariable int id) {
        Project project = this.projectService.GetById(id);

        if(project == null) {
            return AppResponse.error()
                    .withMessage("Project not found!")
                    .build();
        }

        return AppResponse.success()
                .withDataAsArray(project)
                .build();
    }

    @PutMapping("/projects")
    public ResponseEntity<?> updateProject(@RequestBody Project project) {
        boolean isUpdateSuccessful = this.projectService.updateProject(project);

        if(!isUpdateSuccessful) {
            return AppResponse.error()
                    .withMessage("Project not found!")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Update successful!")
                .build();
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable int id) {
        boolean isUpdateSuccessful = this.projectService.removeProject(id);

        if(!isUpdateSuccessful) {
            return AppResponse.error()
                    .withMessage("Project not found!")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Remove successful!")
                .build();
    }
}
