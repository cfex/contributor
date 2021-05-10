package com.contributor.web;

import com.contributor.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/")
public class IndexController {

    private final ProjectService projectService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("projects", projectService.popularProjectsThisWeek());
        return "index";
    }
}
