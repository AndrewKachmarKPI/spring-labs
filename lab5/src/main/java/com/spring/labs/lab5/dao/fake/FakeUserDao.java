package com.spring.labs.lab5.dao.fake;

import com.spring.labs.lab5.dao.UserDao;
import com.spring.labs.lab5.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class FakeUserDao implements UserDao {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public User save(User user) {
        return users.put(user.getUsername(), user);
    }

    @Override
    public List<User> findAll() {
        return users.values().stream().toList();
    }

    @Override
    public User findByName(String username) {
        return users.get(username);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return users.containsKey(username);
    }
}
