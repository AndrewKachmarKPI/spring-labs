package com.spring.labs.lab5.dao.jdbc;

import com.spring.labs.lab5.dao.TopicDao;
import com.spring.labs.lab5.dao.jdbc.mappers.TopicRowMapper;
import com.spring.labs.lab5.domain.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Qualifier("jdbcTopicDao")
public class JdbcTopicDao implements TopicDao {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public Topic save(Topic topic) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertSql = "INSERT INTO topics (title, content, author_id, creation_date, category_id) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, topic.getTitle());
                    ps.setString(2, topic.getContent());
                    ps.setLong(3, topic.getAuthor().getId());
                    ps.setString(4, String.valueOf(topic.getCreationDate()));
                    ps.setLong(5, topic.getForumCategory().getId());
                    return ps;
                }, keyHolder);
        return topic.toBuilder()
                .id(keyHolder.getKey().longValue()).build();
    }

    @Override
    public List<Topic> findAll() {
        return jdbcTemplate.query("SELECT * FROM topics", new TopicRowMapper(jdbcTemplate));

    }

    @Override
    public Topic findById(Long id) {
        String selectSql = "SELECT * FROM topics WHERE id = ?";
        List<Topic> result = jdbcTemplate.query(selectSql, new Object[]{id}, new TopicRowMapper(jdbcTemplate));
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public void deleteTopic(String title) {
        String deleteSql = "DELETE FROM topics WHERE title = ?";
        jdbcTemplate.update(deleteSql, title);
    }

    @Override
    public Optional<Topic> findByName(String title) {
        String selectSql = "SELECT * FROM topics WHERE title = ?";
        List<Topic> result = jdbcTemplate.query(selectSql, new Object[]{title}, new TopicRowMapper(jdbcTemplate));
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public boolean existByTitle(String title) {
        String countSql = "SELECT COUNT(*) FROM topics WHERE title = ?";
        int count = jdbcTemplate.queryForObject(countSql, new Object[]{title}, Integer.class);
        return count > 0;
    }
}
