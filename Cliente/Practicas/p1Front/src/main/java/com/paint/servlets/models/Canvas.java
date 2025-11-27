package com.paint.servlets.models;

import java.util.Date;

public class Canvas {
    private final int id;
    private String name;
    private String content;
    private final Date createdAt;
    private int width;
    private int height;

    public Canvas(int id, String name, String content, int width, int height){
        this.id = id;
        this.name = name;
        this.content = content;
        this.width = width;
        this.height = height;
        this.createdAt = new Date();
    }

    public Date getCreatedAt() { return createdAt; }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) { this.content = content; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
}