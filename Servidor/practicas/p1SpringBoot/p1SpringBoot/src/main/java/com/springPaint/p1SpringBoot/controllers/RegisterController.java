package com.springPaint.p1SpringBoot.controllers;

import org.springframework.ui.Model;
import com.springPaint.p1SpringBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String newUser(@RequestParam String username, @RequestParam String name, @RequestParam String password, Model model){
        boolean isRegistered = userService.saveUser(username, name, password);
        if (isRegistered) {
            return "redirect:/login";
        }else {
            model.addAttribute("error", "El nombre de usuario ya existe. Prueba con otro");
            return "register";
        }
    }
}
