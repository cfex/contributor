package com.contributor.web;

import com.contributor.dao.UserDao;
import com.contributor.payload.response.UserDetailsResponse;
import com.contributor.security.AppUserDetailsModel;
import com.contributor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/users")
public class UserController {

    private final UserDao userDao;
    private final UserService userService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("users", userDao.findAll());
        return "index";
    }

    @GetMapping("/{username}/profile")
    public ModelAndView showUserProfile(@AuthenticationPrincipal AppUserDetailsModel authUser,
                                        @PathVariable("username") String username) {
        ModelAndView mav = new ModelAndView("users/profile");
        UserDetailsResponse userDetailsResponse = authUser.getUsername().equals(username)
                ? userService.findByUsername(username) : userService.findByUsernameAndRetrieveOnlyPublished(username);

        mav.addObject("user", userDetailsResponse);
        return mav;
    }
}
