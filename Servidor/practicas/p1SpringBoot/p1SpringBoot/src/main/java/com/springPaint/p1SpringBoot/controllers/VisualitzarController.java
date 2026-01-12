package com.springPaint.p1SpringBoot.controllers;

import com.springPaint.p1SpringBoot.models.Canvas;
import com.springPaint.p1SpringBoot.models.User;
import com.springPaint.p1SpringBoot.models.Version;
import com.springPaint.p1SpringBoot.service.CanvasService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VisualitzarController {
    @Autowired
    CanvasService canvasService;

    @GetMapping("/visualitzar")
    public String visualizeCanvas(@RequestParam int id, @RequestParam(required = false) Integer versionId,
                                  HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        Canvas canvas = canvasService.getCanvasById(id);

        if (canvas == null || canvas.getIsDelete() == 1 || !canvasService.canUserView(canvas, user.getId())){
            return "redirect:/dibuixos";
        }

        List<Version> allVersions = canvasService.getAllVersionsOfCanvas(id);
        Version versionToDisplay = null;

        if (allVersions.isEmpty()) return "redirect:/dibuixos";

        if (versionId != null) {
            Version requestedVersion = canvasService.getVersionContentById(versionId);
            if (requestedVersion != null && requestedVersion.getIdCanvas() == id) {
                versionToDisplay = requestedVersion;
            }
        }

        if (versionToDisplay == null){
            versionToDisplay = allVersions.get(0);
        }

        model.addAttribute("canvas", canvas);
        model.addAttribute("versionActual", versionToDisplay);
        model.addAttribute("allVersions", allVersions);
        model.addAttribute("currentVersionId", versionToDisplay.getId());

        return "visualitzar";
    }
}