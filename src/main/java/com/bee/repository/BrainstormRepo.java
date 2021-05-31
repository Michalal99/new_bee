package com.bee.repository;

import com.bee.models.Brainstorm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrainstormRepo extends JpaRepository<Brainstorm, Long>{
    Optional<Brainstorm> findBrainstormById(Long id);
}
