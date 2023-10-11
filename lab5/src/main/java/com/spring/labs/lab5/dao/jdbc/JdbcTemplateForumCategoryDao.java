package com.spring.labs.lab5.dao.jdbc;

import com.spring.labs.lab5.dao.ForumCategoryDao;
import com.spring.labs.lab5.dao.jdbc.mappers.ForumCategoryRowMapper;
import com.spring.labs.lab5.domain.ForumCategory;
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
@Qualifier("jdbcCategoryDao")
public class JdbcTemplateForumCategoryDao implements ForumCategoryDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public ForumCategory save(ForumCategory forumCategory) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertSql = "INSERT INTO forum_category (created, category_name, description, background_image, moderator_id) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, forumCategory.getCreated());
                    ps.setString(2, forumCategory.getCategoryName());
                    ps.setString(3, forumCategory.getDescription());
                    ps.setString(4, forumCategory.getBackgroundImage());
                    ps.setLong(5, forumCategory.getModerator().getId());
                    return ps;
                }, keyHolder);
        return forumCategory.toBuilder()
                .id(keyHolder.getKey().longValue()).build();
    }

    @Override
    public List<ForumCategory> findAll() {
        return jdbcTemplate.query("SELECT * FROM forum_category", new ForumCategoryRowMapper(jdbcTemplate));
    }

    @Override
    public int totalSize() {
        String countSql = "SELECT COUNT(*) FROM forum_category";
        return jdbcTemplate.queryForObject(countSql, Integer.class);
    }

    @Override
    public List<ForumCategory> findAllPageableAndFiltered(Integer offset, Integer limit, String title) {
        String selectSql = "SELECT * FROM forum_category WHERE category_name LIKE ? LIMIT ? OFFSET ?";
        return jdbcTemplate.query(selectSql, new Object[]{title + "%", limit, offset}, new ForumCategoryRowMapper(jdbcTemplate));
    }

    @Override
    public Optional<ForumCategory> findByName(String categoryName) {
        String selectSql = "SELECT * FROM forum_category WHERE category_name = ?";
        List<ForumCategory> result = jdbcTemplate.query(selectSql, new Object[]{categoryName}, new ForumCategoryRowMapper(jdbcTemplate));
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public Optional<ForumCategory> findById(Long id) {
        String selectSql = "SELECT * FROM forum_category WHERE id = ?";
        List<ForumCategory> result = jdbcTemplate.query(selectSql, new Object[]{id}, new ForumCategoryRowMapper(jdbcTemplate));
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public boolean existByName(String categoryName) {
        String countSql = "SELECT COUNT(*) FROM forum_category WHERE category_name = ?";
        int count = jdbcTemplate.queryForObject(countSql, new Object[]{categoryName}, Integer.class);
        return count > 0;
    }

    @Override
    public void removeByName(String categoryName) {
        String deleteSql = "DELETE FROM forum_category WHERE category_name = ?";
        jdbcTemplate.update(deleteSql, categoryName);
    }
}
