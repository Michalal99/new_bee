package com.bee.controller;

import com.bee.models.Kanban;
import com.bee.models.Kanban_inprogress;
import com.bee.service.KanbanService;
import com.bee.service.Kanban_inprogressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/kanbans_inprogress")
public class Kanban_inprogressController {
    @Autowired
    private Kanban_inprogressService kanban_inprogressService;

    @GetMapping("/create")
    public String createKanban_inprogress(Model model) {
        Kanban_inprogress kanban_inprogress = new Kanban_inprogress();
        model.addAttribute("kanban_inprogress", kanban_inprogress);
        return "Kanban/create_sub2";
    }

    @PostMapping
    public RedirectView storeKanban_inprogress(@ModelAttribute("kanban_inprogress") Kanban_inprogress kanban_inprogress, Model model) {
        kanban_inprogressService.addKanban_inprogress(kanban_inprogress);
        return new RedirectView("/kanbans");
    }

    @GetMapping
    public String indexKanbans(Model model) {
        List<Kanban_inprogress> kanbans_inprogresses = kanban_inprogressService.findAllKanbans_inprogress();
        model.addAttribute("kanbans_subs", kanbans_inprogresses);
        return "Kanban/index_sub2";
    }

    @GetMapping("/{id}")
    public String showKanban_inprogress(@PathVariable("id") Long id, Model model) {
        Kanban_inprogress newKanban_inprogress = kanban_inprogressService.findKanban_inprogressById(id);
        model.addAttribute("kanban_inprogress", newKanban_inprogress);
        return "Kanban_inprogress/show";
    }

    @GetMapping("/{id}/delete")
    public RedirectView deleteKanban_inprogress(@PathVariable("id") Long id) {
        Kanban_inprogress oldKanban_inprogress = kanban_inprogressService.findKanban_inprogressById(id);
        kanban_inprogressService.deleteKanban_inprogress(oldKanban_inprogress);
        return new RedirectView("/kanbans_inprogress");
    }
}
