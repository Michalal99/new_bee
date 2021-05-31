package com.bee.controller;

import com.bee.models.Kanban;
import com.bee.models.Kanban_done;
import com.bee.service.KanbanService;
import com.bee.service.Kanban_doneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/kanbans_done")
public class Kanban_doneController {
    @Autowired
    private Kanban_doneService kanban_doneService;

    @GetMapping("/create")
    public String createKanban_done(Model model) {
        Kanban_done kanban_done = new Kanban_done();
        model.addAttribute("kanban_done", kanban_done);
        return "Kanban/create_sub3";
    }

    @PostMapping
    public RedirectView storeKanban_done(@ModelAttribute("kanban_done") Kanban_done kanban_done, Model model) {
        kanban_doneService.addKanban_done(kanban_done);
        return new RedirectView("/kanbans");
    }

    @GetMapping
    public String indexKanbans(Model model) {
        List<Kanban_done> kanbans_todos = kanban_doneService.findAllKanbans_done();
        model.addAttribute("kanbans_subs", kanbans_todos);
        return "Kanban/index_sub";
    }

    @GetMapping("/{id}")
    public String showKanban_done(@PathVariable("id") Long id, Model model) {
        Kanban_done newKanban_done = kanban_doneService.findKanban_doneById(id);
        model.addAttribute("kanban_done", newKanban_done);
        return "Kanban_done/show";
    }

    @GetMapping("/{id}/delete")
    public RedirectView deleteKanban_done(@PathVariable("id") Long id) {
        Kanban_done oldKanban_done = kanban_doneService.findKanban_doneById(id);
        kanban_doneService.deleteKanban_done(oldKanban_done);
        return new RedirectView("/kanbans_done");
    }
}
