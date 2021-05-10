package com.contributor.web;

import com.contributor.security.AppUserDetailsModel;
import com.contributor.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/vote")
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/{projectId}/upvote")
    public String vote(@PathVariable("projectId") String projectId, @AuthenticationPrincipal AppUserDetailsModel authUser) {
        voteService.vote(projectId, authUser.getUsername());

        return "redirect:/projects/{projectId}/details";
    }
}
