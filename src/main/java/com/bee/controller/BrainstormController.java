package com.bee.controller;

import com.bee.models.Brainstorm;
import com.bee.models.Comment;
import com.bee.models.Project;
import com.bee.service.BrainstormService;
import com.bee.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/projects/{id}/brainstorms")
public class BrainstormController {

    @Autowired
    BrainstormService brainstormService;
    @Autowired
    ProjectService projectService;

    @GetMapping("create")
    public String createProjectComment(@PathVariable("id") Long id, Model model) {
        Project project = projectService.findProjectById(id);
        var brainstorm = new Brainstorm();
        brainstorm.setProject(project);
        model.addAttribute("brainstorm", brainstorm);
        model.addAttribute("project", project);
        return "Brainstorm/create";
    }

    @PostMapping
    public RedirectView storeComment(@ModelAttribute("brainstorm") Brainstorm brainstorm, Model model) {
        Long project_id = brainstorm.getProject().getId();
        String path = String.format("/projects/%d", project_id);
//        commentService.addComment(comment);
        brainstormService.addBrainstorm(brainstorm);
        return new RedirectView(path);
    }
}
