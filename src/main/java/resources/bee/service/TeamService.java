package resources.bee.service;

import resources.bee.models.Team;
import resources.bee.repository.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamRepo teamRepo;

    public void addTeam(Team team) {
        teamRepo.save(team);
    }

    public List<Team> findAllTeams() {
        return teamRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Team findTeamById(Long id){
        //TODO obsluga wyjatkow
        return teamRepo.findTeamById(id).orElseThrow();
    }

    public void deleteTeam(Team team){
        teamRepo.delete(team);
    }
}
