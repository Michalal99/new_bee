package com.bee.service;

import com.bee.models.Kanban_done;
import com.bee.repository.Kanban_doneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Kanban_doneService {
    @Autowired
    private Kanban_doneRepository kanban_doneRepository;

    public void addKanban_done(Kanban_done kanban_done) {
        kanban_doneRepository.save(kanban_done);
    }

    public List<Kanban_done> findAllKanbans_done() { return kanban_doneRepository.findAll(Sort.by(Sort.Direction.ASC, "id")); }

    public Kanban_done findKanban_doneById(Long id){
        //TODO obsluga wyjatkow
        return kanban_doneRepository.findKanban_doneById(id).orElseThrow();
    }

    public void deleteKanban_done(Kanban_done kanban_done){
        kanban_doneRepository.delete(kanban_done);
    }
}
