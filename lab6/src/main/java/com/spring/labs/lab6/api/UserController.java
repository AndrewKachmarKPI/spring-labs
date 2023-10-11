package com.spring.labs.lab6.api;

import com.spring.labs.lab6.dto.UserDto;
import com.spring.labs.lab6.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserDto>> findAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{username}")
    @Operation(summary = "Get user by username")
    public ResponseEntity<UserDto> findUserByName(@PathVariable
                                                  @Parameter(description = "Username of the user", required = true)
                                                  @NotBlank String username) {
        return ResponseEntity.ok(userService.findUserByName(username));
    }
}
