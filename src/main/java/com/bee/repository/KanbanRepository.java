package com.bee.repository;

import com.bee.models.Kanban;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KanbanRepository extends JpaRepository<Kanban, Long> {
    Optional<Kanban> findKanbanById(Long id);
}
