package com.bee.service;

import com.bee.models.Kanban;
import com.bee.models.Kanban_todo;
import com.bee.repository.KanbanRepository;
import com.bee.repository.Kanban_todoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KanbanService {
    @Autowired
    private KanbanRepository kanbanRepository;

    public void addKanban(Kanban kanban) {
        kanbanRepository.save(kanban);
    }

    public List<Kanban> findAllKanbans() { return kanbanRepository.findAll(Sort.by(Sort.Direction.ASC, "id")); }

    public Kanban findKanbanById(Long id){
        //TODO obsluga wyjatkow
        return kanbanRepository.findKanbanById(id).orElseThrow();
    }

    public void deleteKanban(Kanban kanban){
        kanbanRepository.delete(kanban);
    }
}