package com.paint.servlets.controllers;

import com.paint.servlets.models.User;
import com.paint.servlets.services.CanvasService;
import com.paint.servlets.services.UserService;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@MultipartConfig
@WebServlet(value = "/esborrar")
public class EsborrarController extends HttpServlet {
    private UserService userService = new UserService();
    private CanvasService canvasService = new CanvasService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            HttpSession session = req.getSession();
            String username = (String) session.getAttribute("user");

            if (username == null){
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("{\"success\": false, \"message\": \"No autoritzat\"}");
                return;
            }

            User user = userService.getUserByUsername(username);

            String idParam = req.getParameter("id");
            int dibuixId = Integer.parseInt(idParam);

            boolean success = canvasService.esborrarDibuix(user,dibuixId);

            resp.setContentType("application/json");
            if (success) {
                resp.getWriter().write("{\"success\": true}");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"success\": false, \"message\": \"El dibuix no s'ha pogut esborrar\"}");
            }
        }catch (NumberFormatException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"success\": false, \"message\": \"ID invalid\"}");
        } catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        }
    }
}
