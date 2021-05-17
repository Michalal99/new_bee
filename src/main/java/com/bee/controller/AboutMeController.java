package com.bee.controller;

        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutMeController {

    @GetMapping("/aboutme")
    public String dashboard(Model model) {
        model.addAttribute("message", "Welcome to about me");
        return "aboutme";
    }
}
