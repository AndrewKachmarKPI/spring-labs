package com.spring.labs.lab5.dao.jdbc.mappers;

import com.spring.labs.lab5.domain.Post;
import com.spring.labs.lab5.domain.Topic;
import com.spring.labs.lab5.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@AllArgsConstructor
public class PostRowMapper implements RowMapper<Post> {

    private static final String SELECT_USER_BY_ID_SQL = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_TOPIC_BY_ID_SQL = "SELECT * FROM topics WHERE id = ?";

    private JdbcTemplate jdbcTemplate;

    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = jdbcTemplate.queryForObject(SELECT_USER_BY_ID_SQL, new Object[]{rs.getLong("author_id")}, new UserRowMapper());
        Topic topic = jdbcTemplate.queryForObject(SELECT_TOPIC_BY_ID_SQL, new Object[]{rs.getLong("topic_id")}, new TopicRowMapper(jdbcTemplate));
        return Post.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .content(rs.getString("content"))
                .description(rs.getString("description"))
                .creationDate(LocalDateTime.parse(rs.getString("creation_date")))
                .upVotes(rs.getInt("up_votes"))
                .downVotes(rs.getInt("down_votes"))
                .author(user)
                .topic(topic)
                .build();
    }
}
