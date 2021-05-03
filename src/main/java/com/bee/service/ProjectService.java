package com.bee.service;

import com.bee.models.Project;
import com.bee.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public void addProject(Project project) {
        projectRepository.save(project);
    }

    public List<Project> findAllProjects() {
        return projectRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Project findProjectById(Long id){
        //TODO obsluga wyjatkow
        return projectRepository.findProjectById(id).orElseThrow();
    }

    public void deleteProject(Project project){
        projectRepository.delete(project);
    }
}
