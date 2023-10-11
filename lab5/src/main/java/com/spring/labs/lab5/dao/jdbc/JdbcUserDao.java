package com.spring.labs.lab5.dao.jdbc;

import com.spring.labs.lab5.dao.UserDao;
import com.spring.labs.lab5.dao.jdbc.mappers.UserRowMapper;
import com.spring.labs.lab5.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Qualifier("jdbcUserDao")
public class JdbcUserDao implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User save(User user) {
        String insertSql = "INSERT INTO users (username, email, password, profile_picture, biography, registration_date, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getProfilePicture());
            ps.setString(5, user.getBiography());
            ps.setString(6, user.getRegistrationDate());
            ps.setString(7, user.getRole().toString());
            return ps;
        }, keyHolder);
        return user.toBuilder().id(keyHolder.getKey().longValue()).build();
    }

    @Override
    public List<User> findAll() {
        String selectSql = "SELECT * FROM users";
        return jdbcTemplate.query(selectSql, new UserRowMapper());
    }

    @Override
    public User findByName(String username) {
        String selectSql = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(selectSql, new Object[]{username}, new UserRowMapper());
    }

    @Override
    public Boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);
        return count > 0;
    }
}
