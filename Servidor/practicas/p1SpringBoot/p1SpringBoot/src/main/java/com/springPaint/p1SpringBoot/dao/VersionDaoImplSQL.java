package com.springPaint.p1SpringBoot.dao;

import com.springPaint.p1SpringBoot.models.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VersionDaoImplSQL implements VersionDao{
    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<Version> versionRowMapper = (rs, rowNum) -> {
        Version version = new Version();
        version.setId(rs.getInt("id"));
        version.setIdCanvas(rs.getInt("id_canvas"));
        version.setContent(rs.getString("content"));
        version.setCreationDate(rs.getTimestamp("creationDate"));
        return version;
    };

    @Override
    public void saveVersion(Version version) {
        String sql = "insert into version (id_canvas, content, creationDate) values (?,?, NOW())";
        jdbcTemplate.update(sql,
                    version.getIdCanvas(),
                    version.getContent()
        );
    }

    @Override
    public List<Version> getVersionsByCanvasId(int idCanvas) {
        String sql = "SELECT * FROM version WHERE id_canvas = ? ORDER BY creationDate DESC";
        return jdbcTemplate.query(sql, versionRowMapper, idCanvas);
    }

    @Override
    public Version getVersionById(int versionId) {
        String sql = "SELECT * FROM version WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, versionRowMapper, versionId);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}
