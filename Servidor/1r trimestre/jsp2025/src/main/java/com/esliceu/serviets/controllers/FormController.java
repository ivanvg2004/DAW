package com.esliceu.serviets.controllers;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/form")
public class FormController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            req.getRequestDispatcher("/WEB-INF/jsp/form.jsp")
                    .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String s1 = req.getParameter("var1");
        String s2 = req.getParameter("var2");
        String op = req.getParameter("op");
        int i1 = Integer.parseInt(s1);
        int i2 = Integer.parseInt(s2);
        req.setAttribute("i1", i1);
        req.setAttribute("i2", i2);
        req.setAttribute("op", op);
        req.setAttribute("result", calculadora(i1, i2, op));
        req.getRequestDispatcher("/WEB-INF/jsp/form.jsp")
                .forward(req, resp);
    }
    private int calculadora(int i1, int i2, String op) {
        int res = 0;
        if (op.equals("+")){
            res = i1 + i2;
        }else if (op.equals("-")){
            res = i1 - i2;
        }else if (op.equals("*")) {
            res = i1 * i2;
        }else if (op.equals("/")){
            res = i1 / i2;
        }
        return res;
    }
}

