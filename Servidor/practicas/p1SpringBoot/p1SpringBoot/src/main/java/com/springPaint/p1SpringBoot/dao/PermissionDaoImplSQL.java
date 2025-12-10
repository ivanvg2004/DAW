package com.springPaint.p1SpringBoot.dao;

import com.springPaint.p1SpringBoot.models.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PermissionDaoImplSQL implements PermissionDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<Permission> permissionRowMapper = (rs, rowNum) -> {
        Permission p = new Permission();
        p.setIdCanvas(rs.getInt("id_canvas"));
        p.setIdUser(rs.getInt("id_user"));
        p.setReadPermission(rs.getString("read_permission"));
        p.setWritePermission(rs.getString("write_permission"));
        return p;
    };

    @Override
    public void addPermission(Permission p) {
        String sql = "INSERT INTO permissions (id_canvas, id_user, read_permission, write_permission) " +
                "VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE read_permission = ?, write_permission = ?";

        jdbcTemplate.update(sql,
                p.getIdCanvas(),
                p.getIdUser(),
                p.getReadPermission(),
                p.getWritePermission(),
                p.getReadPermission(),
                p.getWritePermission()
        );
    }

    @Override
    public void removePermission(int idCanvas, int idUser) {
        String sql = "DELETE FROM permissions WHERE id_canvas = ? AND id_user = ?";
        jdbcTemplate.update(sql, idCanvas, idUser);
    }

    @Override
    public Permission getPermission(int idCanvas, int idUser) {
        String sql = "SELECT * FROM permissions WHERE id_canvas = ? AND id_user = ?";
        try {
            return jdbcTemplate.queryForObject(sql, permissionRowMapper, idCanvas, idUser);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Permission> getPermissionsByCanvas(int idCanvas) {
        String sql = "SELECT * FROM permissions WHERE id_canvas = ?";
        return jdbcTemplate.query(sql, permissionRowMapper, idCanvas);
    }

    @Override
    public List<Integer> getCanvasIdsSharedWithUser(int idUser) {
        String sql = "SELECT id_canvas FROM permissions WHERE id_user = ? " +
                "AND (read_permission = 'public' OR write_permission = 'public')";
        return jdbcTemplate.queryForList(sql, Integer.class, idUser);
    }
}
