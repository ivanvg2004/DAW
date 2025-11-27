package com.paint.servlets.DAOS;

import com.paint.servlets.DTO.DibuixDTO;
import com.paint.servlets.models.Canvas;
import com.paint.servlets.models.User;

import java.util.*;

public class CanvasDAO {

    private final static Map<User, Map<Integer, Canvas>> drawsDatabase = new HashMap();
    private static int idGenerate = 0;

    private static int generarID() {
        int id = idGenerate;
        idGenerate++;
        return id;
    }
    private static String generarName(int id, String name) {
        if (name.isEmpty()){
            name = "Dibuix" + id;
        }
        return name;
    }

    public static void guardarDibuix(User username, String name, String contingutJson, int width, int height) {
        int id = generarID();
        name = generarName(id, name);

        Canvas canvas = new Canvas(id, name, contingutJson, width, height);

        Map<Integer, Canvas> personalDrawings = drawsDatabase.get(username);

        if (personalDrawings == null){
            personalDrawings = new HashMap<>();
            drawsDatabase.put(username, personalDrawings);
        }
        personalDrawings.put(id, canvas);

        System.out.println(
                "Data:" + canvas.getCreatedAt() +
                        "Username: " + username.getUser() +
                        " ID: " +  canvas.getId() +
                        " Nom: " + canvas.getName() +
                        " Dibuix: " + canvas.getContent() +
                        " Mides: " + width + "x" + height);
    }

    public static List<DibuixDTO> getDrawingsByUser(User user){
        List<DibuixDTO> llistaDTO = new ArrayList<>();
        Map<Integer, Canvas> personalDrawings = drawsDatabase.get(user);

        if (personalDrawings != null){
            for (Canvas canvas : personalDrawings.values()) {
                DibuixDTO dto = new DibuixDTO(
                        canvas.getId(),
                        canvas.getName(),
                        user.getUser(),
                        canvas.getCreatedAt()
                );
                llistaDTO.add(dto);
            }
        }
        return llistaDTO;
    }

    public static List<DibuixDTO> getAllDrawings(){
        List<DibuixDTO> llistaDTO = new ArrayList<>();
        for (Map.Entry<User, Map<Integer, Canvas>> entry : drawsDatabase.entrySet()) {
            User user = entry.getKey();
            Map<Integer, Canvas> personalMap = entry.getValue();

            for (Canvas canvas : personalMap.values()) {
                DibuixDTO dto = new DibuixDTO(
                        canvas.getId(),
                        canvas.getName(),
                        user.getUser(),
                        canvas.getCreatedAt()
                );
                llistaDTO.add(dto);
            }
        }
        return llistaDTO;
    }

    public static boolean esborrarDibuix(User user, int dibuixId){
        Map<Integer, Canvas> personalDrawings = drawsDatabase.get(user);

        if (personalDrawings != null){
            Canvas dibuixEsborrat = personalDrawings.remove(dibuixId);
            return dibuixEsborrat != null;
        }
        return false;
    }

    public static Canvas getDrawingById(int dibuixId) {
        for (Map<Integer, Canvas> personalMap : drawsDatabase.values()) {
            if (personalMap.containsKey(dibuixId)) {
                return personalMap.get(dibuixId);
            }
        }
        return null;
    }

    public static boolean actualitzarDibuix(User user, int dibuixId, String name, String contingutJson, int width, int height){
        Map<Integer, Canvas> personalDrawings = drawsDatabase.get(user);

        if (personalDrawings != null && personalDrawings.containsKey(dibuixId)){
            Canvas dibuixExistent = personalDrawings.get(dibuixId);

            name = generarName(dibuixId, name);

            dibuixExistent.setName(name);
            dibuixExistent.setContent(contingutJson);
            dibuixExistent.setWidth(width);
            dibuixExistent.setHeight(height);
            return true;
        }

        return false;
    }
}