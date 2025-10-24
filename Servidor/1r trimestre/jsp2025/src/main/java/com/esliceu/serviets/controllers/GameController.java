package com.esliceu.serviets.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(value = "/game")
public class GameController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int secret = (int) (Math.random()*20);
        session.setAttribute("secret", secret);
        session.setAttribute("gameover", false);
        session.setAttribute("intents", 5);
        System.out.println("Secret: " + secret);
        req.getRequestDispatcher("/WEB-INF/jsp/game.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int num = Integer.parseInt(req.getParameter("number"));
        HttpSession session = req.getSession();
        int secret = (int) session.getAttribute("secret");
        int intents = (int) session.getAttribute("intents");
        boolean gameover = (boolean) session.getAttribute("gameover");


        String message = "";
        if(num< secret){
            message = "El secret es major";
            intents--;
        } else if (num == secret) {
            message = "Has encertat!!";
            gameover = true;
        }else {
            message = "El secret és menor";
            intents--;
        }
        if (intents == 0){
            message = "Has perdut...";
            gameover = true;
        }
        req.setAttribute("intents", intents);
        req.setAttribute("message", message);
        req.setAttribute("gameover", gameover);

        session.setAttribute("intents", intents);
        session.setAttribute("gameover", gameover);


        req.getRequestDispatcher("/WEB-INF/jsp/game.jsp")
                .forward(req, resp);
    }
}
