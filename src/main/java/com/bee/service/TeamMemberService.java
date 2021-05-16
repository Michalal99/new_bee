package com.bee.service;

import com.bee.models.Team_member;
import com.bee.repository.TeamMemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamMemberService {
    @Autowired
    private TeamMemberRepo teamMemberRepo;

    public void addTeamMember(Team_member team_member) {
        teamMemberRepo.save(team_member);
    }

    public List<Team_member> findAllTeamMembers() {
//        return teamMemberRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return teamMemberRepo.findAll();
    }

    public Team_member findTeamMemberById(Long id){
        //TODO obsluga wyjatkow
        return teamMemberRepo.findTeam_memberById(id).orElseThrow();
    }

    public void deleteTeam(Team_member team_member){
        teamMemberRepo.delete(team_member);
    }
}
