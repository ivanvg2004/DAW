package com.paint.servlets.controllers;

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
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/dibuixos")
public class DibuixosController extends HttpServlet {
    private UserService userService = new UserService();
    private CanvasService canvasService = new CanvasService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("user");
        User user = userService.getUserByUsername(username);
        if (username == null || user == null){
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String scope = req.getParameter("scope");
        List<DibuixDTO> llistaDibuixos;
        String titol;

        if ("my".equals(scope)){
            llistaDibuixos = canvasService.getDrawingsByUser(user);
            titol = "Els meus dibuixos";
        }else {
            llistaDibuixos = canvasService.getAllDrawings();
            titol = "Tots els dibuixos";
        }

        req.setAttribute("dibuixos", llistaDibuixos);
        req.setAttribute("titol", titol);

        req.getRequestDispatcher("/WEB-INF/jsp/dibuixos.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/dibuixos.jsp")
                .forward(req, resp);
    }
}
