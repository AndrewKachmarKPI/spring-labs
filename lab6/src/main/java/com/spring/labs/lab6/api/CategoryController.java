package com.spring.labs.lab6.api;

import com.spring.labs.lab6.domain.ForumCategoryEntity;
import com.spring.labs.lab6.dto.ForumCategoryDto;
import com.spring.labs.lab6.dto.create.CreateForumCategoryDto;
import com.spring.labs.lab6.dto.PageDto;
import com.spring.labs.lab6.exceptions.ErrorResponse;
import com.spring.labs.lab6.service.ForumCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Validated
@Tag(name = "Forum categories", description = "Methods related to forum categories")
public class CategoryController {
    private final ForumCategoryService categoryService;

    @Operation(summary = "Create a new forum category", description = "Create a new forum category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Forum category created successfully",
                    content = @Content(schema = @Schema(implementation = ForumCategoryEntity.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Forum category duplicate",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping
    public ResponseEntity<ForumCategoryDto> createForumCategory(
            @Valid @RequestBody CreateForumCategoryDto forumCategory) {
        return new ResponseEntity<>(categoryService.create(forumCategory), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing forum category by ID", description = "Update an existing forum category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Forum category updated successfully",
                    content = @Content(schema = @Schema(implementation = ForumCategoryEntity.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Forum category is not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Forum category duplicate",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PutMapping("/{categoryId}")
    public ResponseEntity<ForumCategoryDto> updateForumCategory(
            @Parameter(description = "Category ID to update", required = true)
            @PathVariable Long categoryId,
            @Valid @RequestBody CreateForumCategoryDto forumCategory) {
        return new ResponseEntity<>(categoryService.update(categoryId, forumCategory), HttpStatus.OK);
    }

    @Operation(summary = "Get all forum categories", description = "Get a list of all forum categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Forum category retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ForumCategoryEntity.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping
    public ResponseEntity<List<ForumCategoryDto>> getAllForumCategories() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get all forum categories with filters", description = "Get a list of all forum categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Forum category retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ForumCategoryEntity.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/filtered")
    public ResponseEntity<PageDto<ForumCategoryDto>> getAllForumCategoriesWithFilters(@RequestParam @Min(0) Integer pageNumber,
                                                                                      @RequestParam @Min(1) Integer pageSize,
                                                                                      @RequestParam String username) {
        return new ResponseEntity<>(categoryService.findAll(username, pageNumber, pageSize), HttpStatus.OK);
    }

    @Operation(summary = "Delete a forum category by name", description = "Delete a forum category by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Forum category deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Forum category is not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @DeleteMapping("/{categoryName}")
    public ResponseEntity<Void> deleteForumCategoryByName(
            @Parameter(description = "Category name to delete", required = true)
            @PathVariable String categoryName) {
        categoryService.deleteByName(categoryName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get a forum category by name", description = "Get a forum category by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Forum category found successfully",
                    content = @Content(schema = @Schema(implementation = ForumCategoryEntity.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Forum category is not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/name/{categoryName}")
    public ResponseEntity<ForumCategoryDto> getForumCategoryByName(
            @Parameter(description = "Category name to retrieve", required = true)
            @PathVariable String categoryName) {
        return new ResponseEntity<>(categoryService.findByName(categoryName), HttpStatus.OK);
    }

    @Operation(summary = "Get a forum category by ID", description = "Get a forum category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Forum category updated successfully",
                    content = @Content(schema = @Schema(implementation = ForumCategoryEntity.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Forum category is not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ForumCategoryDto> getForumCategoryById(
            @Parameter(description = "Category ID to retrieve", required = true)
            @PathVariable Long id) {
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }
}
