package com.bee.controller;

import com.bee.models.Kanban;
import com.bee.models.Kanban_todo;
import com.bee.service.KanbanService;
import com.bee.service.Kanban_todoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/kanbans/todo")
public class Kanban_todoController {
    @Autowired
    private Kanban_todoService kanban_todoService;

    @GetMapping("/create")
    public String createKanban_todo(Model model) {
        Kanban_todo kanban_todo = new Kanban_todo();
        model.addAttribute("kanban_todo", kanban_todo);
        return "Kanban_todo/create";
    }

    @PostMapping
    public RedirectView storeKanban_todo(@ModelAttribute("kanban_todo") Kanban_todo kanban_todo, Model model) {
        kanban_todoService.addKanban_todo(kanban_todo);
        return new RedirectView("/kanbans/todo");
    }

    @GetMapping
    public String indexKanbans(Model model) {
        List<Kanban_todo> kanbans_todos = kanban_todoService.findAllKanbans_todo();
        model.addAttribute("kanbans_todos", kanbans_todos);
        return "Kanban_todo/index";
    }

    @GetMapping("/{id}")
    public String showKanban_todo(@PathVariable("id") Long id, Model model) {
        Kanban_todo newKanban_todo = kanban_todoService.findKanban_todoById(id);
        model.addAttribute("kanban_todo", newKanban_todo);
        return "Kanban_todo/show";
    }

    @GetMapping("/{id}/delete")
    public RedirectView deleteKanban_todo(@PathVariable("id") Long id) {
        Kanban_todo oldKanban_todo = kanban_todoService.findKanban_todoById(id);
        kanban_todoService.deleteKanban_todo(oldKanban_todo);
        return new RedirectView("/kanbans/todo");
    }
}
