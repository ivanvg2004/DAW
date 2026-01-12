package com.liceu.hibernate.controllers;

import com.liceu.hibernate.models.Job;
import com.liceu.hibernate.models.User;
import com.liceu.hibernate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @ResponseBody//Retorna un user
    @GetMapping("/search")
    public User getUserByName(@RequestParam String username){
        return userService.getUserByName(username);
    }

    @GetMapping("/user")
    public String newUser(Model model){
        List<User> users = userService.allUsers();
        List<Job> jobs = userService.allJobs();
        model.addAttribute("users", users);
        model.addAttribute("jobs", jobs);

        return "user";
    }
    //@RequestParam: DAdes han de coincidir amb name de form
    @PostMapping("/user")
    public String newUserPost(@RequestParam String name,
                              @RequestParam String password,
                              @RequestParam int birth,
                              @RequestParam Long jobs){
        userService.newUser(name, birth, password, jobs);
        return "redirect:/user";
    }
}
