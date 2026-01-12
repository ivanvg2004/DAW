package com.springPaint.p1SpringBoot.controllers;

import com.springPaint.p1SpringBoot.models.User;
import com.springPaint.p1SpringBoot.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("login")
    public String login(HttpSession session){
        session.invalidate();
        return "login";
    }
    @PostMapping("login")
    public String selectUser(@RequestParam String username, @RequestParam String password, HttpSession session, Model model){
        User user = userService.validateUser(username, password);
        if (user != null){
            session.setAttribute("username", user.getUsername());
            session.setAttribute("user", user);

            return "redirect:/private";
        }else {
            model.addAttribute("error", "Usuario o contraseña Incorrectos");
            return "login";
        }
    }
}
