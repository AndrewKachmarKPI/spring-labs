package com.spring.labs.lab5.dao.jdbc;

import com.spring.labs.lab5.dao.PostDao;
import com.spring.labs.lab5.dao.jdbc.mappers.ForumCategoryRowMapper;
import com.spring.labs.lab5.dao.jdbc.mappers.PostRowMapper;
import com.spring.labs.lab5.dao.jdbc.mappers.TopicRowMapper;
import com.spring.labs.lab5.domain.ForumCategory;
import com.spring.labs.lab5.domain.Post;
import com.spring.labs.lab5.domain.Topic;
import com.spring.labs.lab5.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Qualifier("jdbcPostDao")
public class JdbcPostDao implements PostDao {

    public static final String INSERT_POST_SQL = "INSERT INTO posts ( name, content, description,creation_date, up_votes,down_votes,author_id, topic_id) VALUES (?,?,?,?,?,?,?,?)";

    private final JdbcTemplate jdbcTemplate;

    public Post save(Post post) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(INSERT_POST_SQL, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, post.getName());
                    ps.setString(2,post.getContent());
                    ps.setString(3,post.getDescription());
                    ps.setString(4,String.valueOf(post.getCreationDate()));
                    ps.setInt(5, post.getUpVotes());
                    ps.setInt(6, post.getDownVotes());
                    ps.setLong(7, post.getAuthor().getId());
                    ps.setLong(8, post.getTopic().getId());
                    return ps;
                }, keyHolder);
        return post.toBuilder()
                .id(keyHolder.getKey().longValue()).build();
    }

    @Override
    public List<Post> findAll() {
        return jdbcTemplate.query("SELECT * FROM posts", new PostRowMapper(jdbcTemplate));
    }

    @Override
    public Optional<Post> findById(Long id) {
        String selectSql = "SELECT * FROM posts WHERE id = ?";
        List<Post> result = jdbcTemplate.query(selectSql, new Object[]{id}, new PostRowMapper(jdbcTemplate));
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public Optional<Post> findByName(String name) {
        String selectSql = "SELECT * FROM posts WHERE name = ?";
        List<Post> result = jdbcTemplate.query(selectSql, new Object[]{name}, new PostRowMapper(jdbcTemplate));
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public boolean existByName(String postName) {
        String countSql = "SELECT COUNT(*) FROM posts WHERE name = ?";
        int count = jdbcTemplate.queryForObject(countSql, new Object[]{postName}, Integer.class);
        return count > 0;
    }

    @Override
    public void deleteByName(String postName) {
        String deleteSql = "DELETE FROM posts WHERE name = ?";
        jdbcTemplate.update(deleteSql, postName);
    }
}
