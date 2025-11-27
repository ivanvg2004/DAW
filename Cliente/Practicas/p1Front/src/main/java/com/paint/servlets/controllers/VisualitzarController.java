package com.paint.servlets.controllers;

import com.paint.servlets.models.Canvas;
import com.paint.servlets.services.CanvasService;
import com.paint.servlets.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/visualitzar")
public class VisualitzarController extends HttpServlet {

    private UserService userService = new UserService();
    private CanvasService canvasService = new CanvasService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("user");

        if (username == null || userService.getUserByUsername(username) == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            int dibuixId = Integer.parseInt(req.getParameter("id"));

            Canvas dibuix = canvasService.getDrawingById(dibuixId);

            if (dibuix != null) {
                req.setAttribute("dibuix", dibuix);
                req.getRequestDispatcher("/WEB-INF/jsp/visualitzar.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/dibuixos");
            }
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/dibuixos");
        }
    }
}
