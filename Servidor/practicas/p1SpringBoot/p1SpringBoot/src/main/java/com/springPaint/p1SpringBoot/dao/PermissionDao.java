package com.springPaint.p1SpringBoot.dao;

import com.springPaint.p1SpringBoot.models.Permission;

import java.util.List;

public interface PermissionDao {
    void addPermission(Permission permission);

    void removePermission(int idCanvas, int idUser);

    Permission getPermission(int idCanvas, int idUser);

    List<Permission> getPermissionsByCanvas(int idCanvas);

    List<Integer> getCanvasIdsSharedWithUser(int idUser);
}
