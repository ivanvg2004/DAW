package com.springPaint.p1SpringBoot.dao;

import com.springPaint.p1SpringBoot.models.Version;

import java.util.List;

public interface VersionDao {
    void saveVersion(Version version);
    List<Version> getVersionsByCanvasId(int idCanvas);
    Version getVersionById(int versionId);
}
