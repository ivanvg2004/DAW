package com.springPaint.p1SpringBoot.controllers;

import com.springPaint.p1SpringBoot.models.Canvas;
import com.springPaint.p1SpringBoot.models.User;
import com.springPaint.p1SpringBoot.service.CanvasService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CanvasController {
    @Autowired
    CanvasService canvasService;

    @GetMapping("canvas")
    public String canvasSession(@RequestParam(required = false) Integer id, HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        if (id != null) {
            Map<String, Object> data = canvasService.getCanvasForEdit(id);
            Canvas c = (Canvas) data.get("canvas");

            if (c != null && canvasService.canUserEdit(c, user.getId())) {
                model.addAttribute("canvas", c);
                model.addAttribute("content", data.get("content"));
                return "canvas";
            } else {
                return "redirect:/dibuixos";
            }
        }

        model.addAttribute("canvas", new Canvas());
        model.addAttribute("content", "[]");
        return "canvas";
    }

    @PostMapping("/canvas")
    public String saveCanvas(@RequestParam(required = false) Integer id, @RequestParam String name, @RequestParam String permission, @RequestParam(required = false) String content, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null){
            return "redirect:/login";
        }
        if (content == null || content.trim().isEmpty() || content.equals("[]") && name.isEmpty()){
            return "redirect:/private";
        }
        if (name.isEmpty()) {
            name = canvasService.saveNewName(user);
        }

        if (id != null && id > 0) {
            Canvas c = canvasService.getCanvasById(id);
            if (c == null || !canvasService.canUserEdit(c, user.getId())) {
                // Si no existe o no tiene permiso, lo echamos
                return "redirect:/dibuixos";
            }

            // Si pasa la seguridad, actualizamos
            canvasService.updateCanvas(id, name, permission, content);
        }else {
            canvasService.saveNewCanvas(user.getId(), name, permission, content);
        }
        System.out.println("Contenido recibido (JSON): " + content);

        return "redirect:/private";
    }

    @PostMapping("/api/autosave")
    @org.springframework.web.bind.annotation.ResponseBody
    public Map<String, Object> autoSave(@RequestParam(required = false) Integer id, @RequestParam String name, @RequestParam String permission, @RequestParam String content, HttpSession session){
        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("user");

        if(user == null){
            response.put("status", "error");
            return response;
        }

        try{
            if (content == null || content.equals("[]")){
                response.put("status", "ignored");
                return response;
            }

            if (name == null || name.trim().isEmpty()) {
                name = canvasService.saveNewName(user);
                response.put("generatedName", name);
            }

            if (id != null && id > 0){
                Canvas c = canvasService.getCanvasById(id);
                if (c == null || !canvasService.canUserEdit(c, user.getId())) {
                    response.put("status", "error");
                    response.put("message", "No autorizado para editar este dibujo");
                    return response;
                }

                canvasService.updateCanvas(id, name, permission, content);
                response.put("status", "success");
                response.put("id", id);
            }else {
                int newId = canvasService.saveNewCanvas(user.getId(), name, permission, content);
                response.put("status", "success");
                response.put("id", newId);
            }
        } catch (Exception e){
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }
}
