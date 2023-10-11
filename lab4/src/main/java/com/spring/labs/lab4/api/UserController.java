package com.spring.labs.lab4.api;

import com.spring.labs.lab4.domain.User;
import com.spring.labs.lab4.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Methods related to system users")
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users")
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{username}")
    @Operation(summary = "Get user by username")
    public User findUserByName(@PathVariable
                               @Parameter(description = "Username of the user", required = true)
                               @NotBlank String username) {
        return userService.findUserByName(username);
    }
}
