package com.bee.controller;

import com.bee.models.Comment;
import com.bee.models.Project;
import com.bee.models.Team;
import com.bee.models.Team_member;
import com.bee.repository.TeamMemberRepo;
import com.bee.security.jwt.JwtUtils;
import com.bee.service.TeamMemberService;
import com.bee.service.TeamService;
import com.bee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamMemberService teamMemberService;
    @Autowired
    private UserService userService;
    @Autowired
    private TeamMemberRepo teamMemberRepo;
    @Autowired
    JwtUtils jwtUtils;

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
    public String indexTeams(Model model, HttpSession session) {
        var y = session.getAttribute("token");
        var userName = jwtUtils.getUserNameFromJwtToken((String)y);
        var user = userService.findUserByUserName(userName);
        model.addAttribute("user", user);
        var teamMembers = teamMemberRepo.findTeam_membersByUser_id(user.getId());
        List<Team> teams = new ArrayList<>();

        for(var teamMember : teamMembers){
            teams.add(teamMember.getTeam());
        }

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

    //-----------projects--------------//

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

    //---------------members--------------//


    @GetMapping("/{id}/members")
    public String showMembers(@PathVariable("id") Long id, Model model) {
        Team team = teamService.findTeamById(id);
        model.addAttribute("team", team);

        List<Team_member> team_members = teamService.findTeamById(id).getTeam_member();
        model.addAttribute("team_members", team_members);

        return "TeamMembers/index";
    }

    @GetMapping("/{id}/members/create")
    public String addMember(@PathVariable("id") Long id, Model model) {
        Team_member team_member = new Team_member();
        team_member.setTeam(teamService.findTeamById(id));
        model.addAttribute("team_member", team_member);
        return "TeamMembers/create";
    }

}
