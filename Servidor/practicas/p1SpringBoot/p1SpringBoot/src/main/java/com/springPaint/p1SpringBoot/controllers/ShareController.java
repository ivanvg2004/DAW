package com.springPaint.p1SpringBoot.controllers;

import com.springPaint.p1SpringBoot.dao.PermissionDao;
import com.springPaint.p1SpringBoot.models.Canvas;
import com.springPaint.p1SpringBoot.models.Permission;
import com.springPaint.p1SpringBoot.models.User;
import com.springPaint.p1SpringBoot.service.CanvasService;
import com.springPaint.p1SpringBoot.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ShareController {

    @Autowired
    CanvasService canvasService;
    @Autowired
    PermissionDao permissionDao;
    @Autowired
    UserService userService;

    @GetMapping("/compartir")
    public String sharePage(@RequestParam int id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Canvas canvas = canvasService.getCanvasById(id);
        if (canvas == null || canvas.getIdAutor() != user.getId()) {
            return "redirect:/dibuixos";
        }

        List<Permission> permissions = permissionDao.getPermissionsByCanvas(id);
        List<Map<String, Object>> usersWithAccess = new ArrayList<>();
        for (Permission p : permissions) {
            User u = userService.getUser(p.getIdUser());
            if (u != null) {
                usersWithAccess.add(Map.of("user", u, "permission", p));
            }
        }

        // Obtener lista para el desplegable (excluyendo al dueño)
        List<User> allUsers = userService.getAllUsers();
        allUsers.removeIf(u -> u.getId() == user.getId());

        model.addAttribute("canvas", canvas);
        model.addAttribute("shares", usersWithAccess);
        model.addAttribute("availableUsers", allUsers); // Pasar al modelo

        return "compartir";
    }

    @PostMapping("/compartir")
    public String addShare(@RequestParam int canvasId,
                           @RequestParam String username,
                           @RequestParam(defaultValue = "false") boolean canWrite,
                           HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        try {
            canvasService.shareCanvas(canvasId, user.getId(), username, canWrite);
        } catch (Exception e) {
            e.printStackTrace();

            model.addAttribute("error", "Error al compartir: " + e.getMessage());

            return sharePage(canvasId, session, model);
        }

        return "redirect:/compartir?id=" + canvasId;
    }

    @PostMapping("/dejar-de-compartir")
    public String removeShare(@RequestParam int canvasId, @RequestParam int userId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            try {
                canvasService.stopSharing(canvasId, user.getId(), userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/compartir?id=" + canvasId;
    }
}