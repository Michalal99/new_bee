package com.bee.controller;

import com.bee.models.Team_member;
import com.bee.service.TeamMemberService;
import com.bee.service.TeamService;
import com.bee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/members")
public class TeamMemberController {
    @Autowired
    private TeamMemberService teamMemberService;

    @PostMapping
    public RedirectView storeMember(@ModelAttribute("team_member") Team_member team_member, Model model) {
        teamMemberService.addTeamMember(team_member);
        return new RedirectView("/teams");
    }
}
