package com.tictactoe.controller;

import com.tictactoe.model.User;
import com.tictactoe.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository  userRepo;
    private final PasswordEncoder encoder;

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            Model model) {
        if (error  != null) model.addAttribute("error",  "Invalid username or password.");
        if (logout != null) model.addAttribute("logout", "You have been logged out.");
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("user") User user,
                             BindingResult result,
                             RedirectAttributes ra,
                             Model model) {
        if (result.hasErrors()) return "register";

        if (userRepo.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Username already taken. Choose another.");
            return "register";
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        ra.addFlashAttribute("success", "Account created! Please sign in.");
        return "redirect:/login";
    }
}
