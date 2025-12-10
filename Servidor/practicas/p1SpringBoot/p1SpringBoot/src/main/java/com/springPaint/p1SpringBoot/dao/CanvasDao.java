package com.springPaint.p1SpringBoot.dao;

import com.springPaint.p1SpringBoot.models.Canvas;
import com.springPaint.p1SpringBoot.models.User;

import java.util.List;

public interface CanvasDao {
    int saveCanvas(Canvas canvas);
    Canvas getCanvas(int id);
    int createName(User user);
    void updateCanvas(Canvas canvas);

    List<Canvas> getActiveCanvasesByAuthor(int idAuthor);
    List<Canvas> getDeletedCanvasesByAuthor(int idAuthor);

    List<Canvas> getPublicCanvases(int userIdExcluding);

    void softDeleteCanvas(int id);
    void restoreCanvas(int id);
    void deleteCanvasPermanently(int id);
}
