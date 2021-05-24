package com.bee.repository;

import com.bee.models.Team_member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepo extends JpaRepository<Team_member, Long> {
    Optional<Team_member> findTeam_memberById(Long id);
    List<Team_member> findTeam_membersByUser_id(Long id);
}