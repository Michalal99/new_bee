package com.bee.controller;

import com.bee.models.Comment;
import com.bee.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/create")
    public String createProject(Model model) {
        Comment comment = new Comment();
        model.addAttribute("comment", comment);
        return "Comment/create";
    }

    @PostMapping
    public RedirectView storeComment(@ModelAttribute("comment") Comment comment, Model model) {
        commentService.addComment(comment);
        return new RedirectView("/comments");
    }

    @GetMapping
    public String indexComments(Model model) {
        List<Comment> comments = commentService.findAllComments();
        model.addAttribute("comments", comments);
        return "Comment/index";
    }

    @GetMapping("/{id}")
    public String showComment(@PathVariable("id") Long id, Model model) {
        Comment newComment = commentService.findCommentById(id);
        model.addAttribute("comment", newComment);
        return "Comment/show";
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteComment(@PathVariable("id") Long id) {
        Comment oldComment = commentService.findCommentById(id);
        commentService.deleteComment(oldComment);
        return new RedirectView("/comments");
    }

    @GetMapping("/edit/{id}")
    public String editComment(@PathVariable("id") Long id, Model model) {
        Comment comment = commentService.findCommentById(id);
        model.addAttribute("comment", comment);
        return "Comment/edit";
    }
}
