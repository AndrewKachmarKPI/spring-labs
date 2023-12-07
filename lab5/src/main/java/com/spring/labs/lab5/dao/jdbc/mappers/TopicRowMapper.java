package com.spring.labs.lab5.dao.jdbc.mappers;

import com.spring.labs.lab5.domain.ForumCategory;
import com.spring.labs.lab5.domain.Topic;
import com.spring.labs.lab5.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


@AllArgsConstructor
public class TopicRowMapper implements RowMapper<Topic> {
    private JdbcTemplate jdbcTemplate;

    @Override
    public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
        String userSelectSql = "SELECT * FROM users WHERE id = ?";
        String categorySelectSql = "SELECT * FROM forum_category WHERE id = ?";
        User user = jdbcTemplate.queryForObject(userSelectSql, new Object[]{rs.getLong("author_id")}, new UserRowMapper());
        ForumCategory forumCategory = jdbcTemplate.queryForObject(categorySelectSql, new Object[]{rs.getLong("category_id")}, new ForumCategoryRowMapper(jdbcTemplate));
        return Topic.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .content(rs.getString("content"))
                .author(user)
                .creationDate(LocalDateTime.parse(rs.getString("creation_date")))
                .forumCategory(forumCategory)
                .build();
    }
}
