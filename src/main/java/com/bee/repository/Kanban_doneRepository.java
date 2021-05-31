package com.bee.repository;

import com.bee.models.Kanban_done;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Kanban_doneRepository extends JpaRepository<Kanban_done, Long> {
    Optional<Kanban_done> findKanban_doneById(Long id);
}
