package com.spring.labs.lab5.serviceImpl;


import com.spring.labs.lab5.dao.UserDao;
import com.spring.labs.lab5.domain.User;
import com.spring.labs.lab5.dto.CreateUserDto;
import com.spring.labs.lab5.enums.UserRole;
import com.spring.labs.lab5.exceptions.ResourceAlreadyExistsException;
import com.spring.labs.lab5.exceptions.ResourceNotFoundException;
import com.spring.labs.lab5.service.UserService;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    public final UserDao userDao;

    public UserServiceImpl(@Qualifier("jdbcUserDao") UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findUserByName(String username) {
        return Optional.ofNullable(userDao.findByName(username))
                .orElseThrow(() -> new ResourceNotFoundException("User with name:" + username + " is not found"));
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User saveUser(CreateUserDto createUserDto) {
        if (userDao.existsByUsername(createUserDto.getUsername())) {
            throw new ResourceAlreadyExistsException("User with name:" + createUserDto.getUsername() + " already exist");
        }
        User user = User.builder()
                .registrationDate(LocalDateTime.now().toString())
                .profilePicture(createUserDto.getProfilePicture())
                .role(createUserDto.getRole())
                .password(createUserDto.getPassword())
                .email(createUserDto.getEmail())
                .biography(createUserDto.getBiography())
                .username(createUserDto.getUsername())
                .build();
        return userDao.save(user);
    }

    @Override
    public void generateDefaultUsers(Integer size, Faker faker) {
        List<String> userNames = Stream.generate(() -> faker.name().username()).distinct().limit(size).toList();
        IntStream.range(1, size).mapToObj(index -> User.builder()
                .id((long) index)
                .username(userNames.get(index))
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .profilePicture(faker.avatar().image())
                .biography(faker.lorem().sentence())
                .registrationDate(LocalDateTime.now().toString())
                .role(UserRole.REGULAR_USER)
                .build()).forEach(userDao::save);
    }
}
