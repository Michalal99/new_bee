package com.bee.service;

import com.bee.models.Kanban_inprogress;
import com.bee.repository.Kanban_inprogressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Kanban_inprogressService {
    @Autowired
    private Kanban_inprogressRepository kanban_inprogressRepository;

    public void addKanban_inprogress(Kanban_inprogress kanban_inprogress) {
        kanban_inprogressRepository.save(kanban_inprogress);
    }

    public List<Kanban_inprogress> findAllKanbans_inprogress() { return kanban_inprogressRepository.findAll(Sort.by(Sort.Direction.ASC, "id")); }

    public Kanban_inprogress findKanban_inprogressById(Long id){
        //TODO obsluga wyjatkow
        return kanban_inprogressRepository.findKanban_inprogressById(id).orElseThrow();
    }

    public void deleteKanban_inprogress(Kanban_inprogress kanban_inprogress){
        kanban_inprogressRepository.delete(kanban_inprogress);
    }
}
