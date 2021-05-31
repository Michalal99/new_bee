package com.bee.repository;

import com.bee.models.Kanban_inprogress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Kanban_inprogressRepository extends JpaRepository<Kanban_inprogress, Long> {
    Optional<Kanban_inprogress> findKanban_inprogressById(Long id);
}
