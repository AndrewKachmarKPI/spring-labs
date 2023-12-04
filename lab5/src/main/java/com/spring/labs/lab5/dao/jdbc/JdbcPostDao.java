package com.spring.labs.lab5.dao.jdbc;

import com.spring.labs.lab5.dao.PostDao;
import com.spring.labs.lab5.dao.jdbc.mappers.PostRowMapper;
import com.spring.labs.lab5.domain.Post;
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
@Qualifier("jdbcPostDao")
public class JdbcPostDao implements PostDao {

    public static final String INSERT_POST_SQL = "INSERT INTO posts ( name, content, description,creation_date, up_votes,down_votes,author_id, topic_id) VALUES (?,?,?,?,?,?,?,?)";
    public static final String UPDATE_POST_SQL =  "UPDATE posts SET name=?, content=?, description=?, creation_date=?, up_votes=?, down_votes=?, author_id=?, topic_id=? WHERE id=?";

    public static final String SELECT_ALL_SQL = "SELECT * FROM posts";
    public static final String SELECT_BY_POST_ID_SQL = "SELECT * FROM posts WHERE id = ?";
    public static final String SELECT_BY_POST_NAME_SQL = "SELECT * FROM posts WHERE name = ?";
    public static final String SELECT_COUNT_BY_NAME_SQL = "SELECT COUNT(*) FROM posts WHERE name = ?";
    public static final String DELETE_BY_POST_NAME_SQL = "DELETE FROM posts WHERE name = ?";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Post save(Post post) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        if (findById(post.getId()).isPresent()) {
            jdbcTemplate.update(
                    con -> {
                        PreparedStatement ps = con.prepareStatement(UPDATE_POST_SQL);
                        ps.setString(1, post.getName());
                        ps.setString(2, post.getContent());
                        ps.setString(3, post.getDescription());
                        ps.setString(4, String.valueOf(post.getCreationDate()));
                        ps.setInt(5, post.getUpVotes());
                        ps.setInt(6, post.getDownVotes());
                        ps.setLong(7, post.getAuthor().getId());
                        ps.setLong(8, post.getTopic().getId());
                        ps.setLong(9, post.getId());
                        return ps;
                    });
            return post;
        }
        else {
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
    }


    @Override
    public List<Post> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new PostRowMapper(jdbcTemplate));
    }

    @Override
    public Optional<Post> findById(Long id) {
        List<Post> result = jdbcTemplate.query(SELECT_BY_POST_ID_SQL, new Object[]{id}, new PostRowMapper(jdbcTemplate));
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public Optional<Post> findByName(String name) {
        List<Post> result = jdbcTemplate.query(SELECT_BY_POST_NAME_SQL, new Object[]{name}, new PostRowMapper(jdbcTemplate));
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public boolean existByName(String postName) {
        int count = jdbcTemplate.queryForObject(SELECT_COUNT_BY_NAME_SQL, new Object[]{postName}, Integer.class);
        return count > 0;
    }

    @Override
    public void deleteByName(String postName) {
        jdbcTemplate.update(DELETE_BY_POST_NAME_SQL, postName);
    }
}
