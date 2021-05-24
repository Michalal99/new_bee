package com.bee.controller;

import com.bee.models.Kanban;
import com.bee.service.KanbanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/kanbans")
public class KanbanController {
    @Autowired
    private KanbanService kanbanService;

    @GetMapping("/create")
    public String createKanban(Model model) {
        Kanban kanban = new Kanban();
        model.addAttribute("kanban", kanban);
        return "Kanban/create";
    }

    @PostMapping
    public RedirectView storeKanban(@ModelAttribute("kanban") Kanban kanban, Model model) {
        kanbanService.addKanban(kanban);
        return new RedirectView("/kanbans");
    }

    @GetMapping
    public String indexKanbans(Model model) {
        List<Kanban> kanbans = kanbanService.findAllKanbans();
        model.addAttribute("kanbans", kanbans);
        return "Kanban/index";
    }

    @GetMapping("/{id}")
    public String showKanban(@PathVariable("id") Long id, Model model) {
        Kanban newKanban = kanbanService.findKanbanById(id);
        model.addAttribute("kanban", newKanban);
        return "Kanban/show";
    }

    @GetMapping("/{id}/delete")
    public RedirectView deleteKanban(@PathVariable("id") Long id) {
        Kanban oldKanban = kanbanService.findKanbanById(id);
        kanbanService.deleteKanban(oldKanban);
        return new RedirectView("/kanbans");
    }
}
