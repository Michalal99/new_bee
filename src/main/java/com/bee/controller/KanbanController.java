package com.bee.controller;

import com.bee.models.*;
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

    @GetMapping("/{id}/todo")
    public String showKanbanToDos(@PathVariable("id") Long id, Model model) {
        List<Kanban_todo> kanban_todos = kanbanService.findKanbanById(id).getKanban_todo();
        model.addAttribute("kanbans", kanban_todos);
        return "Kanban/index_sub";
    }

    @GetMapping("/{id}/todo/create")
    public String createKanbanToDo(@PathVariable("id") Long id, Model model) {
        Kanban kanban = kanbanService.findKanbanById(id);
        Kanban_todo kanban_todo = new Kanban_todo();
        kanban_todo.setKanban(kanban);
        model.addAttribute("kanban_sub", kanban_todo);
        return "Kanban/create_sub";
    }

    @GetMapping("/{id}/done")
    public String showKanbanDones(@PathVariable("id") Long id, Model model) {
        List<Kanban_done> kanban_dones = kanbanService.findKanbanById(id).getKanban_done();
        model.addAttribute("kanbans", kanban_dones);
        return "Kanban/index_sub";
    }

    @GetMapping("/{id}/done/create")
    public String createKanbanDone(@PathVariable("id") Long id, Model model) {
        Kanban kanban = kanbanService.findKanbanById(id);
        Kanban_done kanban_done = new Kanban_done();
        kanban_done.setKanban(kanban);
        model.addAttribute("kanban_sub", kanban_done);
        return "Kanban/create_sub";
    }

    @GetMapping("/{id}/inprogress")
    public String showKanbanInProgresses(@PathVariable("id") Long id, Model model) {
        List<Kanban_inprogress> kanban_inprogresses = kanbanService.findKanbanById(id).getKanban_inprogress();
        model.addAttribute("kanbans", kanban_inprogresses);
        return "Kanban/index_sub";
    }

    @GetMapping("/{id}/inprogress/create")
    public String createKanbanInProgress(@PathVariable("id") Long id, Model model) {
        Kanban kanban = kanbanService.findKanbanById(id);
        Kanban_inprogress kanban_inprogress = new Kanban_inprogress();
        kanban_inprogress.setKanban(kanban);
        model.addAttribute("kanban_sub", kanban_inprogress);
        return "Kanban/create_sub";
    }
}
