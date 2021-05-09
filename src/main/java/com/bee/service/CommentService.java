package com.bee.service;

import com.bee.models.Comment;
import com.bee.models.Project;
import com.bee.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> findAllComments() { return commentRepository.findAll(Sort.by(Sort.Direction.ASC, "id")); }

    public Comment findCommentById(Long id){
        //TODO obsluga wyjatkow
        return commentRepository.findCommentById(id).orElseThrow();
    }

    public void deleteComment(Comment comment){
        commentRepository.delete(comment);
    }
}
