package com.springPaint.p1SpringBoot.controllers;

import com.springPaint.p1SpringBoot.models.Canvas;
import com.springPaint.p1SpringBoot.models.User;
import com.springPaint.p1SpringBoot.service.CanvasService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DibuixController {

    @Autowired
    CanvasService canvasService;

    @GetMapping("/dibuixos")
    public String verDibuixos(@RequestParam(required = false, defaultValue = "active") String filter,
                              HttpSession session, Model model){
        User user = (User) session.getAttribute("user");

        List<Canvas> lista;
        String titulo;

        switch (filter) {
            case "trash":
                lista = canvasService.getMyDeletedCanvases(user.getId());
                titulo = "Paperera de Reciclatge";
                break;
            case "shared":
                lista = canvasService.getSharedWithMeCanvases(user.getId());
                titulo = "Compartits amb mi";
                break;
            default:
                lista = canvasService.getMyActiveCanvases(user.getId());
                titulo = "Els meus Dibuixos";
                break;
        }

        model.addAttribute("dibuixos", lista);
        model.addAttribute("titulo", titulo);
        model.addAttribute("filter", filter);

        return "dibuixos";
    }

    @PostMapping("/esborrar")
    @ResponseBody
    public Map<String, Object> esborrarDibuix(@RequestParam int id, HttpSession session){
        Map<String, Object> response = new HashMap<>();

        try{
            canvasService.moveToTrash(id);
            response.put("success", true);
        }catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar");
        }

        return response;
    }

    @PostMapping("/restaurar")
    @ResponseBody
    public Map<String, Object> restaurarDibuix(@RequestParam int id, HttpSession session){
        Map<String, Object> response = new HashMap<>();

        try {
            canvasService.restoreCanvas(id);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al restaurar");
        }

        return response;
    }

    @PostMapping("/eliminarDefinitiu")
    @ResponseBody
    public Map<String, Object> eliminarDefinitiu(@RequestParam int id, HttpSession session){
        Map<String, Object> response = new HashMap<>();

        try {
            canvasService.deleteCanvasPermanently(id);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar definitivamente");
        }

        return response;
    }

    @PostMapping("/clonar")
    @ResponseBody
    public Map<String, Object> clonarDibuix(@RequestParam int id, HttpSession session){
        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("user");

        try {
            canvasService.cloneCanvas(id, user.getId());
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al clonar: " + e.getMessage());
        }

        return response;
    }
}