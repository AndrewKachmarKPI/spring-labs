package com.spring.labs.lab5.dao.jdbc.mappers;

import com.spring.labs.lab5.domain.User;
import com.spring.labs.lab5.enums.UserRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .username(rs.getString("username"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .profilePicture(rs.getString("profile_picture"))
                .biography(rs.getString("biography"))
                .registrationDate(rs.getString("registration_date"))
                .role(UserRole.valueOf(rs.getString("role")))
                .build();
    }
}
