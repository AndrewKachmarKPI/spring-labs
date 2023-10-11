package com.spring.labs.lab5.dao.jdbc.mappers;

import com.spring.labs.lab5.domain.ForumCategory;
import com.spring.labs.lab5.domain.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


@AllArgsConstructor
public class ForumCategoryRowMapper implements RowMapper<ForumCategory> {
    private JdbcTemplate jdbcTemplate;

    @Override
    public ForumCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        String selectSql = "SELECT * FROM users WHERE id = ?";
        User user = jdbcTemplate.queryForObject(selectSql, new Object[]{rs.getLong("moderator_id")}, new UserRowMapper());
        return ForumCategory.builder()
                .id(rs.getLong("id"))
                .created(rs.getString("created"))
                .categoryName(rs.getString("category_name"))
                .description(rs.getString("description"))
                .backgroundImage(rs.getString("background_image"))
                .moderator(user)
                .build();
    }
}
