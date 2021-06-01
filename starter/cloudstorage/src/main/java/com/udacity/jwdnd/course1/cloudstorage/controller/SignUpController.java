package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class SignUpController {
    private final UserService userService;

    @GetMapping("/signup")
    public String getSignUp() {
        return "/signup";
    }

    @PostMapping("/signup")
    public String signUp(User user, RedirectAttributes redirectAttributes) {
        String signupError = null;
        if (userService.isUsernameAvailable(user.getUsername())) {
            signupError = "The username already exists.";
        }

        if (signupError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null) {
            redirectAttributes.addFlashAttribute("signupSuccess", true);
            redirectAttributes.addFlashAttribute("successMessage", "Account have been created. Please login to continue");
            return "redirect:/login";
        }
        redirectAttributes.addAttribute("signupError", signupError);
        return "redirect:/signup";
    }
}

