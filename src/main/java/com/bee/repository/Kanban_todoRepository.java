package com.bee.repository;

import com.bee.models.Kanban_todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Kanban_todoRepository extends JpaRepository<Kanban_todo, Long> {
    Optional<Kanban_todo> findKanban_todoById(Long id);
}
