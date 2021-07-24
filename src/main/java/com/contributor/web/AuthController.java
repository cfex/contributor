package com.contributor.web;

import com.contributor.exception.AccountAlreadyExistsException;
import com.contributor.model.event.RegistrationCompleteEvent;
import com.contributor.model.user.User;
import com.contributor.model.verification.VerificationToken;
import com.contributor.payload.request.UserRegisterRequest;
import com.contributor.payload.response.UserDetailsResponse;
import com.contributor.service.UserService;
import com.contributor.service.VerificationTokenService;
import com.contributor.shared.UserDto;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;

@AllArgsConstructor
@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final MessageSource messageSource;
    private final ApplicationEventPublisher eventPublisher;
    private final VerificationTokenService verificationTokenService;
    private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String showRegisterForm(ModelMap modelMap) {
        modelMap.put("userRegisterRequest", new UserRegisterRequest());
        return "auth/registration";
    }

    @SneakyThrows
    @PostMapping(value = "/registration")
    public ModelAndView register(@Valid @ModelAttribute("userRegisterRequest") UserRegisterRequest registerRequest,
                                 WebRequest request) {

        Locale locale = request.getLocale();
        try {
            LOGGER.info("Collecting form request data on /registration");
            UserDto userDtoMap = modelMapper.map(registerRequest, UserDto.class);
            UserDetailsResponse userDetails = userService.createUser(userDtoMap);
            String applicationURL = request.getContextPath();
            eventPublisher.publishEvent(new RegistrationCompleteEvent(userDetails, request.getLocale(), applicationURL));
        } catch (Exception e) {
            ModelAndView mav = new ModelAndView("auth/registration", "user", registerRequest);
            mav.addObject("message", messageSource.getMessage("messages.email.error", null, locale));
            return mav;
        }

        return new ModelAndView("auth/login", "user", registerRequest);
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {
        Locale locale = request.getLocale();
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        Calendar calendar = Calendar.getInstance();

        if (verificationToken == null || (verificationToken.getExpirationDate().before(calendar.getTime()))) {
            String message = messageSource.getMessage("messages.token.expired", null, locale);
            model.addAttribute("message", message);
            return "redirect:/auth/error.html?lang=" + locale.getLanguage();
        }

        User user = verificationToken.getUser();
        userService.verifyUserEmail(user, verificationToken);

        return "redirect:/auth/login";
    }
}
