package com.bee.controller;

import com.bee.models.Comment;
import com.bee.models.Kanban;
import com.bee.models.Project;
import com.bee.models.Team;
import com.bee.service.CommentService;
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
    @Autowired
    private CommentService commentService;

    @GetMapping("/create")
    public String createProject(Model model) {
        Project project = new Project();
        model.addAttribute("project", project);
        return "Project/create";
    }

    @PostMapping
    public RedirectView storeProject(@ModelAttribute("project") Project project, Model model) {
        Long team_id = project.getTeam().getId();
        String path = String.format("/teams/%d", team_id);
        projectService.addProject(project);
        return new RedirectView(path);
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
        List<Comment> comments = commentService.findAllComments();
        model.addAttribute("comments", comments);
        return "Project/show";
    }

    @GetMapping("/{id}/delete")
    public RedirectView deleteTeam(@PathVariable("id") Long id) {
        Project oldProject = projectService.findProjectById(id);
        projectService.deleteProject(oldProject);
        return new RedirectView("/projects");
    }

    @GetMapping("/{id}/edit")
    public String editTeam(@PathVariable("id") Long id, Model model) {
        Project project = projectService.findProjectById(id);
        //System.out.println("Team_id jest rowny "+project.getTeam_id());
        model.addAttribute("project", project);
        return "Project/edit";
    }

    @GetMapping("/{id}/comments")
    public String showTeamProjects(@PathVariable("id") Long id, Model model) {
        List<Comment> comment = projectService.findProjectById(id).getComment();
        model.addAttribute("comments", comment);
        return "Comment/index";
    }

    @GetMapping("/{id}/comments/create")
    public String createProjectComment(@PathVariable("id") Long id, Model model) {
        Project project = projectService.findProjectById(id);
        Comment comment = new Comment();
        comment.setProject(project);
        model.addAttribute("comment", comment);
        return "Comment/create";
    }

    @GetMapping("/{id}/kanbans")
    public String showProjectKanbans(@PathVariable("id") Long id, Model model) {
        Kanban kanbans = projectService.findProjectById(id).getKanban();
        model.addAttribute("kanbans", kanbans);
        return "Kanban/index";
    }

    @GetMapping("/{id}/kanbans/create")
    public String createProjectKanban(@PathVariable("id") Long id, Model model) {
        Project project = projectService.findProjectById(id);
        Kanban kanban = new Kanban();
        kanban.setProject(project);
        model.addAttribute("kanban", kanban);
        return "Kanban/create";
    }
}
