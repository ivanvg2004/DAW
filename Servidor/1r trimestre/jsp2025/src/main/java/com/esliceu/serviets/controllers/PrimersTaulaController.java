package com.esliceu.serviets.controllers;

import com.esliceu.serviets.models.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/taula")
public class PrimersTaulaController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Integer> list = calculaPrimers(500);
        List<List<Integer>> taula = new ArrayList<>();
        int pos = 0;
        while (pos < list.size()){
            List<Integer> row = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                if (pos >= list.size()) break;
                row.add(list.get(pos));
                pos++;
            }
            taula.add(row);
        }
        req.setAttribute("taula",taula);

        Person p = new Person();
        p.setName("Bill Gates");
        p.setBirthYear(1950);
        req.setAttribute("bill", p);

        Map<String, Integer> map = new HashMap<>();
        map.put("Dilluns", 4);
        map.put("Dimarts", 5);
        map.put("Dimecres", 6);
        map.put("Dijous", 2);
        map.put("Divendres", 3);
        req.setAttribute("dies", map);


        req.getRequestDispatcher("/WEB-INF/jsp/taula.jsp")
                .forward(req, resp);
    }
    List<Integer> calculaPrimers(int max) {
        int[] ar = new int[max];
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
