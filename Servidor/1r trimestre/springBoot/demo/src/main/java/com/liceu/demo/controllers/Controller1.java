package com.liceu.demo.controllers;

import com.liceu.demo.service.TestService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Controller1 {
    @Autowired
    private TestService testService;

    @GetMapping("/newuser")
    public String newUser(@RequestParam String name, @RequestParam String pass){
        testService.saveUser(name, pass);
        return "newuser";
    }

    @GetMapping("/test")
    public String test(Model model){
        model.addAttribute("nom", testService.getUser(1).getName());
        return "test";
    }

    @GetMapping("/suma")
    public String addGet(HttpSession session){
        return "sumaform";
    }

    @PostMapping("/suma")
    public String add(Model model, @RequestParam int n1, @RequestParam int n2){
        int suma = testService.suma(n1,n2);
        model.addAttribute("resultat", suma);
        return "suma";
    }
}
