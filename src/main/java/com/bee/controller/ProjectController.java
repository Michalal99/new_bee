package com.bee.controller;

import com.bee.models.Comment;
import com.bee.models.Project;
import com.bee.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("/create")
    public String createProject(Model model) {
        Project project = new Project();
        model.addAttribute("project", project);
        return "Project/create";
    }

    @PostMapping
    public RedirectView storeProject(@ModelAttribute("project") Project project, Model model) {
        projectService.addProject(project);
        return new RedirectView("/projects");
    }

    @GetMapping
    public String indexProjects(Model model) {
        List<Project> projects = projectService.findAllProjects();
        model.addAttribute("projects", projects);
        return "Project/index";
    }

    @GetMapping("/{id}")
    public String showTeam(@PathVariable("id") Long id, Model model) {
        Project newProject = projectService.findProjectById(id);
        model.addAttribute("project", newProject);
        return "Project/show";
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteTeam(@PathVariable("id") Long id) {
        Project oldProject = projectService.findProjectById(id);
        projectService.deleteProject(oldProject);
        return new RedirectView("/projects");
    }

    @GetMapping("/edit/{id}")
    public String editTeam(@PathVariable("id") Long id, Model model) {
        Project project = projectService.findProjectById(id);
        model.addAttribute("project", project);
        return "Project/edit";
    }

    @GetMapping("/comments/{id}")
    public String showTeamProjects(@PathVariable("id") Long id, Model model) {
        List<Comment> comment = projectService.findProjectById(id).getComment();
        model.addAttribute("comments", comment);
        return "Comment/index";
    }

    @GetMapping("/comments/create/{id}")
    public String createProjectComment(Model model) {
        Comment comment = new Comment();
        model.addAttribute("comment", comment);
        return "Comment/create";
    }
}
