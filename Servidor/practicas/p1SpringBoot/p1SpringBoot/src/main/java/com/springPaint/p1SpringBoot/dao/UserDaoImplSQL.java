package com.springPaint.p1SpringBoot.dao;

import com.springPaint.p1SpringBoot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImplSQL implements UserDao{
    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = (rs, rowNum) -> {
       User user = new User();
       user.setId(rs.getInt("id"));
       user.setUsername(rs.getString("username"));
       user.setName(rs.getString("name"));
       user.setPassword(rs.getString("password"));
       return user;
    };

    @Override
    public User getUser(int id) {
        List<User> list = jdbcTemplate.query("select * from user where id =?", userRowMapper, id);
        return list.get(0);
    }

    @Override
    public User getUserByUsername(String username) {
        try{
            String sql = "select * from user where username = ?";
            List<User> list = jdbcTemplate.query(sql, userRowMapper, username);
            if (list.isEmpty()) return null;
            return list.get(0);
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public void saveUser(User user) {
        jdbcTemplate.update("insert into user (username, name, password) values (?,?,?)", user.getUsername(), user.getName(), user.getPassword());
    }
}
