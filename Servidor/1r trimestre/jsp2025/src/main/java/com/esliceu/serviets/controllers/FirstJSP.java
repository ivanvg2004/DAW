package com.esliceu.serviets.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/firstjsp")
public class FirstJSP extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/*        int aleatori = (int) (Math.random()*100);
        //Sale en la pagina
        req.setAttribute("aleatori", aleatori);
        //sale en el dockercompose
        System.out.println("El numero aleatori es " + aleatori);*/
        List<Integer> primers = calculaPrimers();
        System.out.println(primers);
        req.setAttribute("primers", primers);
        req.getRequestDispatcher("/WEB-INF/jsp/firstjsp.jsp")
                .forward(req, resp);
    }

    List<Integer> calculaPrimers() {
        int[] ar = new int[100];
        List<Integer> llistaPrimers = new ArrayList<>();
        for (int i = 0; i < ar.length; i++) {
            ar[i] = i+2;
        }
        for (int i = 0; i < ar.length; i++) {
            int primer = ar[i];
            if(primer == -1 ) continue;
            llistaPrimers.add(primer);
            int index = i;
            do{
                ar[index] = -1;
                index += primer;
            }while (index < ar.length);
        }
        return llistaPrimers;
    }
}
