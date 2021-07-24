package com.contributor.web;

import com.contributor.exception.ProjectAlreadyExistsException;
import com.contributor.model.event.ContributionRequestEvent;
import com.contributor.payload.request.CommentRequest;
import com.contributor.payload.request.ProjectRequest;
import com.contributor.payload.request.ProjectUpdateRequest;
import com.contributor.payload.response.ProjectResponse;
import com.contributor.payload.response.ProjectResponseMinified;
import com.contributor.security.AppUserDetailsModel;
import com.contributor.service.CommentService;
import com.contributor.service.ProjectService;
import com.contributor.service.VoteService;
import com.contributor.shared.ProjectDto;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ModelMapper modelMapper;
    private final VoteService voteService;
    private final ProjectService projectService;
    private final CommentService commentService;
    private final ApplicationEventPublisher eventPublisher;
    private final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

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
        List<ProjectResponseMinified> allBySearchQuery = projectService.findAllBySearchQuery(keyword);
        ModelAndView mav = new ModelAndView("project/search");
        mav.addObject("projects", allBySearchQuery);

        return mav;
    }

    @GetMapping("/{projectId}/details")
    public ModelAndView showProjectByProjectId(@PathVariable("projectId") String projectId) {
        ModelAndView mav = new ModelAndView("project/details");
        mav.addObject("project", projectService.findByProjectId(projectId));
        mav.addObject("votesCount", voteService.voteCount(projectId));
        mav.addObject("commentRequest", new CommentRequest());

        return mav;
    }

    @GetMapping("/{projectId}/edit")
    @PreAuthorize("@authValidation.checkByID(#projectId, authentication.principal.username)")
    public ModelAndView showProjectEditPage(@PathVariable("projectId") String projectId) {
        ModelAndView mav = new ModelAndView("project/edit");
        mav.addObject("projectUpdateRequest", new ProjectUpdateRequest());
        mav.addObject("project", projectService.findByProjectId(projectId));

        return mav;
    }

    @PostMapping("/{projectId}/edit")
    @PreAuthorize("@authValidation.checkByID(#projectId, authentication.principal.username)")
    public String updateProject(@PathVariable("projectId") String projectId,
                                @Valid @ModelAttribute("ProjectRequest") ProjectUpdateRequest updateRequest) {
        projectService.updateProject(updateRequest, projectId);

        return "redirect:/projects/{projectId}/details";
    }

    @SneakyThrows
    @PostMapping("/create")
    public String createProject(@AuthenticationPrincipal AppUserDetailsModel auth,
                                @Valid @ModelAttribute("projectRequest") ProjectRequest projectRequest, ModelMap modelMap) {

        ProjectDto projectDto = modelMapper.map(projectRequest, ProjectDto.class);
        try {
            CompletableFuture<ProjectResponse> project = projectService.createProject(auth, projectDto);
            modelMap.addAttribute("project", project);
        } catch (Exception exception) {
            throw new ProjectAlreadyExistsException(exception.getMessage());
        }

        return "redirect:/projects/explore";
    }

    @PostMapping("/{projectId}/comment")
    public String commentOnProject(@PathVariable("projectId") String projectId,
                                   @ModelAttribute CommentRequest commentRequest,
                                   @AuthenticationPrincipal AppUserDetailsModel authUser) {
        commentService.createComment(authUser, projectId, commentRequest);

        return "redirect:/projects/{projectId}/details";
    }

    @PostMapping("/contribution/{projectId}/request")
    public String requestContribution(@PathVariable("projectId") String projectId,
                                      @AuthenticationPrincipal AppUserDetailsModel authUser) {
        LOGGER.info("Collecting data for contribution request event.");
        eventPublisher.publishEvent(new ContributionRequestEvent(authUser, projectId));

        return "redirect:/projects/{projectId}/details";
    }

    @PostMapping("/{projectId}/delete")
    @PreAuthorize("@authValidation.checkByID(#projectId, authentication.principal.username)")
    public String deleteHostedProject(@PathVariable("projectId") String projectId) {
        projectService.delete(projectId);

        return "redirect:/users/me";
    }
}
