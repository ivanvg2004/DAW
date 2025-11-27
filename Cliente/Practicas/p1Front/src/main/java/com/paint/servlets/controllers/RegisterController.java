package com.paint.servlets.controllers;

import com.paint.servlets.models.User;
import com.paint.servlets.DAOS.UserDAO;
import com.paint.servlets.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/register")
public class RegisterController extends HttpServlet {

    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //si hay una sesion antigua la quita
        session.invalidate();
        req.getRequestDispatcher("/WEB-INF/jsp/register.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String user = req.getParameter("user");
        String password = req.getParameter("password");

        boolean registrationSuccess = userService.registerUser(name, user, password);

        if (registrationSuccess) {
            req.setAttribute("message", "Usuari registrat correctament! Si us plau, inicia sessió.");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp")
                    .forward(req, resp);
        } else {
            req.setAttribute("message", "Aquest nom d'usuari ja existeix");
            req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
        }
    }
}
