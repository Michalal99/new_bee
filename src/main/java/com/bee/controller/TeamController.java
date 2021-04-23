package com.bee.controller;

import com.bee.models.Team;
import com.bee.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamController {
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<Team> addEmployee(@RequestBody Team team) {
        Team newTeam = teamService.addTeam(team);
        return new ResponseEntity<>(newTeam, HttpStatus.CREATED);
    }

    @GetMapping
    public String getAllEmployees (Model model) {
        List<Team> teams = teamService.findAllTeams();
        model.addAttribute("teams", teams);
        return "Team/index";
    }

    @GetMapping("/{id}")
    public String getEmployeeById (@PathVariable("id") Long id, Model model) {
        Team newTeam = teamService.findTeamById(id);
        model.addAttribute("team", newTeam);
        return "Team/show";
    }

}
