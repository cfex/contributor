package com.contributor.web;

import com.contributor.model.Project;
import com.contributor.payload.request.CommentRequest;
import com.contributor.payload.request.ProjectRequest;
import com.contributor.payload.response.ProjectResponse;
import com.contributor.payload.response.ProjectResponseMinified;
import com.contributor.security.AppUserDetailsModel;
import com.contributor.service.CommentService;
import com.contributor.service.ProjectService;
import com.contributor.service.VoteService;
import com.contributor.shared.ProjectDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ModelMapper modelMapper;
    private final VoteService voteService;
    private final ProjectService projectService;
    private final CommentService commentService;

    @GetMapping("/explore")
    public ModelAndView showAll() {
        List<ProjectResponseMinified> projects = projectService.fetchAll();
        ModelAndView mav = new ModelAndView("project/index");
        mav.addObject("projects", projects);

        return mav;
    }

    @GetMapping("/create")
    public String showCreateFormView(ModelMap modelMap) {
        modelMap.put("projectRequest", new ProjectRequest());
        return "project/create";
    }

    @GetMapping("/search")
    public ModelAndView showProjectForSearchQuery(@RequestParam("keyword") String keyword) {
        List<Project> allBySearchQuery = projectService.findAllBySearchQuery(keyword);
        ModelAndView mav = new ModelAndView("project/search");
        mav.addObject("projects", allBySearchQuery);

        return mav;
    }

    @GetMapping("/{projectId}/details")
    public ModelAndView showProjectByProjectId(@PathVariable("projectId") String projectId, ModelMap modelMap) {
        ModelAndView mav = new ModelAndView("project/details");
        mav.addObject("project", projectService.findByProjectId(projectId));
        mav.addObject("votesCount", voteService.voteCount(projectId));
        modelMap.put("commentRequest", new CommentRequest());

        return mav;
    }

    @PostMapping("/create")
    public String createProject(@AuthenticationPrincipal AppUserDetailsModel auth,
                                @ModelAttribute("projectRequest") ProjectRequest projectRequest, Model model) {
        ProjectDto projectDto = modelMapper.map(projectRequest, ProjectDto.class);
        ProjectResponse project = projectService.createProject(auth, projectDto);
        model.addAttribute("project", project);

        return "project/index";
    }

    @PostMapping("/{projectId}/comment")
    public String commentOnProject(@PathVariable("projectId") String projectId,
                                   @ModelAttribute CommentRequest commentRequest,
                                   @AuthenticationPrincipal AppUserDetailsModel authUser) {
        commentService.createComment(authUser, projectId, commentRequest);

        return "redirect:/projects/{projectId}/details";
    }
}
