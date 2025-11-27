package com.paint.servlets.DTO;

import java.util.Date;

public class DibuixDTO {
    private int id;
    private String name;
    private String username;
    private Date createdAt;

    public DibuixDTO(int id, String name, String username, Date createdAt) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

}
