package com.bee.controller;

import com.bee.models.Project;
import com.bee.models.Team;
import com.bee.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/create")
    public String createTeam(Model model) {
        Team team = new Team();
        model.addAttribute("team", team);
        return "Team/create";
    }

    @PostMapping
    public RedirectView storeTeam(@ModelAttribute("team") Team team, Model model) {
        teamService.addTeam(team);
        return new RedirectView("/teams");
    }

    @GetMapping
    public String indexTeams(Model model) {
        List<Team> teams = teamService.findAllTeams();
        model.addAttribute("teams", teams);
        return "Team/index";
    }

    @GetMapping("/{id}")
    public String showTeam(@PathVariable("id") Long id, Model model) {
        Team newTeam = teamService.findTeamById(id);
        model.addAttribute("team", newTeam);
        return "Team/show";
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteTeam(@PathVariable("id") Long id) {
        Team oldTeam = teamService.findTeamById(id);
        teamService.deleteTeam(oldTeam);
        return new RedirectView("/teams");
    }

    @GetMapping("/edit/{id}")
    public String editTeam(@PathVariable("id") Long id, Model model) {
        Team team = teamService.findTeamById(id);
        model.addAttribute("team", team);
        return "Team/edit";
    }

    @GetMapping("/projects/{id}")
    public String showTeamProjects(@PathVariable("id") Long id, Model model) {
        List<Project> project = teamService.findTeamById(id).getProject();
        model.addAttribute("projects", project);
        return "Project/index";
    }

    @GetMapping("/projects/create/{id}")
    public String createTeamProject(Model model) {
        Project project = new Project();
        model.addAttribute("project", project);
        return "Project/create";
    }
}
