package com.paint.servlets.controllers;

import com.paint.servlets.DAOS.UserDAO;
import com.paint.servlets.models.User;
import com.paint.servlets.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/login")
public class LoginController extends HttpServlet {
    private UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //si hay una session antigua la quita
        session.invalidate();
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("user");
        String password = req.getParameter("password");

        String userName = userService.loginUser(user, password);

        if(userName != null){
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setAttribute("name", UserDAO.getName(user));
            resp.sendRedirect(req.getContextPath() + "/private");
            return;
        } else {
            req.setAttribute("message", "Username / password incorrect");
        }

        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp")
                .forward(req, resp);
    }
}