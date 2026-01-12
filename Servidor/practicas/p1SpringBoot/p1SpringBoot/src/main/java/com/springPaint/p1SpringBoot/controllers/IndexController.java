package com.springPaint.p1SpringBoot.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(HttpSession session){
        session.invalidate();
        return "index";
    }

    @GetMapping("private")
    public String privatePage(HttpSession session, Model model){
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);

        return "private";
    }
}