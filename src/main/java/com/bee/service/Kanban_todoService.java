package com.bee.service;

import com.bee.models.Kanban_todo;
import com.bee.repository.Kanban_todoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Kanban_todoService {
    @Autowired
    private Kanban_todoRepository kanban_todoRepository;

    public void addKanban_todo(Kanban_todo kanban_todo) {
        kanban_todoRepository.save(kanban_todo);
    }

    public List<Kanban_todo> findAllKanbans_todo() { return kanban_todoRepository.findAll(Sort.by(Sort.Direction.ASC, "id")); }

    public Kanban_todo findKanban_todoById(Long id){
        //TODO obsluga wyjatkow
        return kanban_todoRepository.findKanban_todoById(id).orElseThrow();
    }

    public void deleteKanban_todo(Kanban_todo kanban_todo){
        kanban_todoRepository.delete(kanban_todo);
    }
}
