package com.springPaint.p1SpringBoot.models;

import java.util.Date;

public class Version {
    private int id;
    private int idCanvas;
    private String content;
    private Date creationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCanvas() {
        return idCanvas;
    }

    public void setIdCanvas(int idCanvas) {
        this.idCanvas = idCanvas;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
