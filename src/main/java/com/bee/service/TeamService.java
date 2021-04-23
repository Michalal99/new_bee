package com.bee.service;

import com.bee.models.Team;
import com.bee.repository.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    private final TeamRepo teamRepo;

    @Autowired
    public TeamService(TeamRepo teamRepo) {
        this.teamRepo = teamRepo;
    }

    public Team addTeam(Team team) {
        return teamRepo.save(team);
    }

    public List<Team> findAllTeams() {
        return teamRepo.findAll();
    }

    public Team findTeamById(Long id){
        //TODO obsluga wyjatkow
        return teamRepo.findTeamById(id).orElseThrow();
    }
}
