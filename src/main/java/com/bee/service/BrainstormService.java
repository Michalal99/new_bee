package com.bee.service;

import com.bee.models.Brainstorm;
import com.bee.models.Comment;
import com.bee.repository.BrainstormRepo;
import com.bee.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrainstormService {
    @Autowired
    private BrainstormRepo brainstormRepo;

    public void addBrainstorm(Brainstorm brainstorm) {
        brainstormRepo.save(brainstorm);
    }

    public List<Brainstorm> findAllBrainstorms() {
        return brainstormRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Brainstorm findBrainstormById(Long id){
        //TODO obsluga wyjatkow
        return brainstormRepo.findBrainstormById(id).orElseThrow();
    }

    public void deleteBrainstorm(Brainstorm brainstorm){
        brainstormRepo.delete(brainstorm);
    }
}