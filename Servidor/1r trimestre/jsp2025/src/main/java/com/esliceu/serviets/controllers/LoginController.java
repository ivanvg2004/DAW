package com.esliceu.serviets.controllers;

import com.esliceu.serviets.DAOS.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("user");
        String password = req.getParameter("password");
        //Determinar si l'usuari existeix al sistema
        //i a més si ha possat be la password
        UserDAO userDAO = new UserDAO();
        boolean userExist = userDAO.checkUser(user, password);
        if(userExist){

            //posem dins la sessió l'objecte user
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            resp.sendRedirect("/private");
            return;
        }else {
            req.setAttribute("message", "Username / password incorrect");
        }
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp")
                .forward(req, resp);
    }
}
