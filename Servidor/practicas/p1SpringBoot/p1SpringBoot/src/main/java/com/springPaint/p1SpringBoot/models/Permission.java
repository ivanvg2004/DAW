package com.springPaint.p1SpringBoot.models;

public class Permission {
    private int idCanvas;
    private int idUser;
    private String readPermission;
    private String writePermission;

    public int getIdCanvas() {
        return idCanvas;
    }

    public void setIdCanvas(int idCanvas) {
        this.idCanvas = idCanvas;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getReadPermission() {
        return readPermission;
    }

    public void setReadPermission(String readPermission) {
        this.readPermission = readPermission;
    }

    public String getWritePermission() {
        return writePermission;
    }

    public void setWritePermission(String writePermission) {
        this.writePermission = writePermission;
    }
}
