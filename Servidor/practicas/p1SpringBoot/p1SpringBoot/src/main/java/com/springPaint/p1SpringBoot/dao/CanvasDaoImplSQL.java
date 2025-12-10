package com.springPaint.p1SpringBoot.dao;

import com.springPaint.p1SpringBoot.models.Canvas;
import com.springPaint.p1SpringBoot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CanvasDaoImplSQL implements CanvasDao{
    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<Canvas> canvasRowMapper = (rs,rowNum) -> {
        Canvas canvas = new Canvas();
        canvas.setId(rs.getInt("id"));
        canvas.setIdAutor(rs.getInt("id_author"));
        canvas.setName(rs.getString("name"));
        canvas.setIsDelete(rs.getInt("isDelete"));
        canvas.setPermission(rs.getString("permission"));
        return canvas;
    };

    @Override
    public int saveCanvas(Canvas canvas) {
        String sql ="insert into canvas (id_author, name, isDelete, permission) values (?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, canvas.getIdAutor());
            ps.setString(2, canvas.getName());
            ps.setInt(3, canvas.getIsDelete());
            ps.setString(4, canvas.getPermission());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public Canvas getCanvas(int id) {
        List<Canvas> list = jdbcTemplate.query("SELECT * FROM canvas WHERE id = ?", canvasRowMapper, id);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    @Override
    public int createName(User user) {
        String sql = "SELECT COUNT(*) FROM canvas WHERE id_author = ?";
        Integer maxId = jdbcTemplate.queryForObject(sql, Integer.class, user.getId());
        if (maxId == null){
            maxId = 0;
        }else {
            maxId++;
        }
        return maxId;
    }

    @Override
    public void updateCanvas(Canvas canvas) {
        String sql = "UPDATE canvas SET name = ?, permission = ? WHERE id = ?";
        jdbcTemplate.update(sql, canvas.getName(), canvas.getPermission(), canvas.getId());
    }

    @Override
    public List<Canvas> getActiveCanvasesByAuthor(int idAuthor) {
        String sql = "SELECT * FROM canvas WHERE id_author = ? AND isDelete = 0 ORDER BY id DESC";
        return jdbcTemplate.query(sql, canvasRowMapper, idAuthor);
    }

    @Override
    public List<Canvas> getDeletedCanvasesByAuthor(int idAuthor) {
        String sql = "SELECT * FROM canvas WHERE id_author = ? AND isDelete = 1 ORDER BY id DESC";
        return jdbcTemplate.query(sql, canvasRowMapper, idAuthor);
    }

    @Override
    public List<Canvas> getPublicCanvases(int userIdExcluding) {
        // Selecciona canvas que sean 'public', no estén borrados y no sean del usuario actual
        String sql = "SELECT * FROM canvas WHERE permission = 'public' AND id_author != ? AND isDelete = 0 ORDER BY id DESC";
        return jdbcTemplate.query(sql, canvasRowMapper, userIdExcluding);
    }

    @Override
    public void softDeleteCanvas(int id) {
        String sql = "UPDATE canvas SET isDelete = 1 WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void restoreCanvas(int id) {
        String sql = "UPDATE canvas SET isDelete = 0 WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteCanvasPermanently(int id) {
        String sql = "DELETE FROM canvas WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
