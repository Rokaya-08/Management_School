package com.impt.Gestion_Ecole.controllers;

import com.impt.Gestion_Ecole.services.services.UserService;
import com.impt.Gestion_Ecole.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    public String userpage() {
        return "user";
    }


    @RequestMapping("/login")
    public String loginForm() {
        return "login";
    }


    //Ajouter  Admin
    @GetMapping("/registration_admin")
    public String registrationFormAdmin(Model model) {
        User admin = new User();
        model.addAttribute("admin", admin);
        return "registration_admin";
        // return "redirect:/";
    }
    @PostMapping("/registration_admin")
    public String registrationAdmin(
            @Valid @ModelAttribute("admin") User admins,
            BindingResult result,
            Model model) {
        User existingUser = userService.findUserByEmail(admins.getEmail());

        if (existingUser != null)
            result.rejectValue("email", null,
                    "Utilisateur déjà enregistré!!!");

        if (result.hasErrors()) {
            model.addAttribute("admin", admins);
            return "/registration_admin";
        }

        userService.saveAdmin(admins);
        return "redirect:/registration_admin?success";

    }

}
