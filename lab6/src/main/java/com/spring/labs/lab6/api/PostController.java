package com.spring.labs.lab6.api;

import com.spring.labs.lab6.dto.create.CreatePostDto;
import com.spring.labs.lab6.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.spring.labs.lab6.domain.PostEntity;
import com.spring.labs.lab6.dto.PostDto;
import com.spring.labs.lab6.service.PostService;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
@Validated
@Tag(name = "Posts", description = "Methods related to forum posts")
public class PostController {

    private final PostService postService;

    @Operation(summary = "Create a new post", description = "Create a new post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post created successfully",
                    content = @Content(schema = @Schema(implementation = PostEntity.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Post duplicate",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping({ "/create/{topicTitle}"})
    public ResponseEntity<PostDto> createPost(
            @Parameter(description = "topic title name", required = true)
            @PathVariable String topicTitle,
            @Valid @RequestBody CreatePostDto post) {
        PostDto createdPost = postService.createPost(post, topicTitle);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @Operation(summary = "Update post by ID", description = "Update an existing post by its Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post updated successfully",
                    content = @Content(schema = @Schema(implementation = PostEntity.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post is not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Post duplicate",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(
            @Parameter(description = "Post ID to update", required = true)
            @PathVariable Long postId,
            @Valid @RequestBody CreatePostDto post) {
        PostDto updatedpost = postService.update(post, postId);
        return new ResponseEntity<>(updatedpost, HttpStatus.OK);
    }

    @Operation(summary = "Find post by name", description = "Find a post by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post found successfully",
                    content = @Content(schema = @Schema(implementation = PostEntity.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post is not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/post/{name}")
    public ResponseEntity<PostDto> findPostByPostName(
            @Parameter(description = "Post name to retrieve", required = true)
            @PathVariable String name) {
        PostDto post = postService.findByPostName(name);
        return new ResponseEntity<>(post, post != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Find post by id", description = "Find a post by its id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post found successfully",
                    content = @Content(schema = @Schema(implementation = PostEntity.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post is not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/post/id/{id}")
    public ResponseEntity<PostDto> findPostById(
            @Parameter(description = "Post id to retrieve", required = true)
            @PathVariable Long id) {
        PostDto post = postService.findById(id);
        return new ResponseEntity<>(post, post != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Find posts by topic name", description = "Find a list of all posts by topic name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PostEntity.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/{topicName}")
    public ResponseEntity<List<PostDto>> findPostsByTopicName(
            @Parameter(description = "Topic name", required = true)
            @PathVariable String topicName) {
        return new ResponseEntity<>(postService.findByTopicName(topicName), HttpStatus.OK);
    }

    @Operation(summary = "Find all post", description = "Find a list of all posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PostEntity.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> findAllPosts() {
        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
    }


    @Operation(summary = "Delete a post by post name", description = "Delete a post by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post is not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @DeleteMapping("/delete/{postName}")
    public ResponseEntity<Void> deletePostByName(
            @Parameter(description = "Post to delete", required = true)
            @PathVariable String postName) {
        postService.deleteByName(postName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}