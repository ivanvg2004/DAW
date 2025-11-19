package com.liceu.demo.dao;

import com.liceu.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestDaoImplSQL implements TestDao{
    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        return user;
    };

    @Override
    public User getUser(int id) {
        List<User> list = jdbcTemplate.query("select *  from user where id =?", userRowMapper, id);
        return list.get(0);
    }

    @Override
    public int lastId() {
        return 0;
    }

    @Override
    public void saveUser(User user){
        jdbcTemplate.update("insert into user (name, password) values (?,?)", user.getName(),user.getPassword());
    }
}
