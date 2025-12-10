package com.springPaint.p1SpringBoot.service;

import com.springPaint.p1SpringBoot.dao.CanvasDao;
import com.springPaint.p1SpringBoot.dao.PermissionDao;
import com.springPaint.p1SpringBoot.dao.UserDao;
import com.springPaint.p1SpringBoot.dao.VersionDao;
import com.springPaint.p1SpringBoot.models.Canvas;
import com.springPaint.p1SpringBoot.models.Permission;
import com.springPaint.p1SpringBoot.models.User;
import com.springPaint.p1SpringBoot.models.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CanvasService {
    @Autowired
    CanvasDao canvasDao;
    @Autowired
    VersionDao versionDao;
    @Autowired
    PermissionDao permissionDao;
    @Autowired
    UserDao userDao;

    @Transactional
    public int saveNewCanvas(int idAuthor, String name, String permission, String content){
        Canvas canvas = new Canvas();
        canvas.setIdAutor(idAuthor);
        canvas.setName(name);
        canvas.setIsDelete(0);
        canvas.setPermission(permission);

        int newCanvasId = canvasDao.saveCanvas(canvas);

        Version version = new Version();
        version.setIdCanvas(newCanvasId);
        version.setContent(content);

        versionDao.saveVersion(version);

        return newCanvasId;
    }

    public Canvas getCanvasById(int canvasId){
        return canvasDao.getCanvas(canvasId);
    }

    public List<Version> getAllVersionsOfCanvas(int canvasId) {
        return versionDao.getVersionsByCanvasId(canvasId);
    }

    public Version getVersionContentById(int versionId) {
        return versionDao.getVersionById(versionId);
    }

    public String saveNewName(User user) {
        String name = "Dibuix " + canvasDao.createName(user);
        return name;
    }

    public List<Canvas> getMyActiveCanvases(int userId) {
        return canvasDao.getActiveCanvasesByAuthor(userId);
    }

    public List<Canvas> getMyDeletedCanvases(int userId) {
        return canvasDao.getDeletedCanvasesByAuthor(userId);
    }

    public void moveToTrash(int canvasId) {
        canvasDao.softDeleteCanvas(canvasId);
    }

    public Map<String, Object> getCanvasForEdit(int canvasId) {
        Canvas canvas = canvasDao.getCanvas(canvasId);
        List<Version> versions = versionDao.getVersionsByCanvasId(canvasId);

        String content = "[]";
        if (!versions.isEmpty()){
            content = versions.get(0).getContent();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("canvas", canvas);
        map.put("content", content);
        return map;
    }

    @Transactional
    public void updateCanvas(int canvasId, String name, String permission, String content) {
        Canvas c = new Canvas();
        c.setId(canvasId);
        c.setName(name);
        c.setPermission(permission);
        canvasDao.updateCanvas(c);

        Version v = new Version();
        v.setIdCanvas(canvasId);
        v.setContent(content);
        versionDao.saveVersion(v);
    }

    public void restoreCanvas(int canvasId){
        canvasDao.restoreCanvas(canvasId);
    }

    public void deleteCanvasPermanently(int canvasId){
        canvasDao.deleteCanvasPermanently(canvasId);
    }

    public void shareCanvas(int canvasId, int ownerId, String targetUsername, boolean canWrite) throws Exception{
        Canvas c = canvasDao.getCanvas(canvasId);
        if (c == null || c.getIdAutor() != ownerId) {
            throw new Exception("No tienes permiso para compartir este dibujo.");
        }

        User target = userDao.getUserByUsername(targetUsername);
        if (target == null) {
            throw new Exception("El usuario '" + targetUsername + "' no existe.");
        }

        if (target.getId() == ownerId) {
            throw new Exception("No puedes compartirte un dibujo a ti mismo.");
        }

        Permission p = new Permission();
        p.setIdCanvas(canvasId);
        p.setIdUser(target.getId());
        p.setReadPermission("public");
        p.setWritePermission(canWrite ? "public" : "private");

        permissionDao.addPermission(p);
    }

    public void stopSharing(int canvasId, int ownerId, int targetUserId) throws Exception {
        Canvas c = canvasDao.getCanvas(canvasId);
        if (c.getIdAutor() != ownerId) throw new Exception("No eres el propietario.");

        permissionDao.removePermission(canvasId, targetUserId);
    }

    public List<Canvas> getSharedWithMeCanvases(int userId) {
        Map<Integer, Canvas> uniqueCanvases = new HashMap<>();

        List<Integer> ids = permissionDao.getCanvasIdsSharedWithUser(userId);
        for (Integer id : ids) {
            Canvas c = canvasDao.getCanvas(id);
            if (c != null && c.getIsDelete() == 0) {
                uniqueCanvases.put(c.getId(), c);
            }
        }

        List<Canvas> publicCanvases = canvasDao.getPublicCanvases(userId);
        for (Canvas c : publicCanvases) {
            // putIfAbsent solo lo añade si no estaba ya en la lista
            uniqueCanvases.putIfAbsent(c.getId(), c);
        }

        // Devolver la lista combinada
        return new ArrayList<>(uniqueCanvases.values());
    }

    @Transactional
    public void cloneCanvas(int originalId, int newOwnerId) {
        Map<String, Object> data = getCanvasForEdit(originalId);
        Canvas original = (Canvas) data.get("canvas");
        String content = (String) data.get("content");

        Canvas copy = new Canvas();
        copy.setIdAutor(newOwnerId);
        copy.setName("Còpia de " + original.getName());
        copy.setIsDelete(0);
        copy.setPermission("private");

        int newId = canvasDao.saveCanvas(copy);

        Version v = new Version();
        v.setIdCanvas(newId);
        v.setContent(content);
        versionDao.saveVersion(v);
    }
    public boolean canUserView(Canvas c, int userId) {
        if (c.getIdAutor() == userId) return true;

        if ("public".equals(c.getPermission())) return true;

        Permission p = permissionDao.getPermission(c.getId(), userId);
        return p != null;
    }

    public boolean canUserEdit(Canvas c, int userId) {
        if (c.getIdAutor() == userId) return true;

        Permission p = permissionDao.getPermission(c.getId(), userId);
        return p != null && "public".equals(p.getWritePermission());
    }


}
