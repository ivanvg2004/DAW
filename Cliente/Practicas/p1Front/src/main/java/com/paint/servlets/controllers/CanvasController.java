package com.paint.servlets.controllers;

import com.paint.servlets.DAOS.CanvasDAO;
import com.paint.servlets.DAOS.UserDAO;
import com.paint.servlets.DTO.DibuixDTO;
import com.paint.servlets.models.Canvas;
import com.paint.servlets.models.User;
import com.paint.servlets.services.CanvasService;
import com.paint.servlets.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.DataInput;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/canvas")
public class CanvasController extends HttpServlet {
    private UserService userService = new UserService();
    private CanvasService canvasService = new CanvasService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("user");
        User user = userService.getUserByUsername(username);

        if (user == null){
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String idParam = req.getParameter("id");

        int canvasWidth = 800;
        int canvasHeight = 600;

        if(idParam != null){
            try {
                int dibuixId = Integer.parseInt(idParam);
                Canvas dibuix = canvasService.getDrawingById(dibuixId);

                boolean esPropietari = false;

                if (dibuix != null){
                    List<DibuixDTO> meusDibuixos = canvasService.getDrawingsByUser(user);
                    for (DibuixDTO d : meusDibuixos){
                        if (d.getId() == dibuixId){
                            esPropietari = true;
                            break;
                        }
                    }
                }
                if (dibuix != null && esPropietari){
                    req.setAttribute("dibuixId", dibuix.getId());
                    req.setAttribute("dibuixName", dibuix.getName());
                    req.setAttribute("dibuixContent", dibuix.getContent());
                    canvasWidth = dibuix.getWidth();
                    canvasHeight = dibuix.getHeight();
                }else {
                    resp.sendRedirect(req.getContextPath() + "/dibuixos");
                    return;
                }
            } catch (NumberFormatException e) {
                resp.sendRedirect(req.getContextPath() + "/dibuixos");
                return;
            }
        }

        req.setAttribute("canvasWidth", canvasWidth);
        req.setAttribute("canvasHeight", canvasHeight);

        req.getRequestDispatcher("/WEB-INF/jsp/canvas.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("user");
        User user = userService.getUserByUsername(username);

        String name = req.getParameter("drawingName");
        String contingutJson = req.getParameter("drawingContent");
        String idParam = req.getParameter("drawingId");

        int width = 800;
        int height = 600;
        try {
            width = Integer.parseInt(req.getParameter("canvasWidth"));
            height = Integer.parseInt(req.getParameter("canvasHeight"));
        } catch (Exception e) {
        }

        try {
            if (idParam != null && !idParam.isEmpty()) {
                int dibuixId = Integer.parseInt(idParam);
                canvasService.actualitzarDibuix(user, dibuixId, name, contingutJson, width, height);
            } else {
                boolean isNameEmpty = (name == null || name.isEmpty());
                boolean isContentEmpty = (contingutJson == null || contingutJson.equals("[]"));

                if (!isNameEmpty || !isContentEmpty) {
                    canvasService.guardarDibuix(user, name, contingutJson, width, height);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/canvas");
    }
}