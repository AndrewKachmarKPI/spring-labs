package com.spring.labs.lab4.api;

import com.spring.labs.lab4.domain.Topic;
import com.spring.labs.lab4.dto.CreateTopicDto;
import com.spring.labs.lab4.exceptions.ErrorResponse;
import com.spring.labs.lab4.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
@Validated
@Tag(name = "Forum topics", description = "Methods related to forum topics")
public class TopicController {
    private final TopicService topicService;

    @Operation(summary = "Create a new topic", description = "Create a new topic.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Topic created successfully",
                    content = @Content(schema = @Schema(implementation = Topic.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Topic duplicate",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })

    @PostMapping("/{categoryName}")
    public ResponseEntity<Topic> createTopic(
            @Parameter(description = "Forum category name", required = true)
            @PathVariable String categoryName,
            @Valid @RequestBody CreateTopicDto topic) {
        Topic createdTopic = topicService.create(topic, categoryName);
        return new ResponseEntity<>(createdTopic, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing topic by ID", description = "Update an existing topic by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Topic updated successfully",
                    content = @Content(schema = @Schema(implementation = Topic.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Topic is not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Topic duplicate",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })

    @PutMapping("/{topicId}")
    public ResponseEntity<Topic> updateTopic(
            @Parameter(description = "Topic ID to update", required = true)
            @PathVariable Long topicId,
            @Valid @RequestBody CreateTopicDto topic) {
        Topic updatedTopic = topicService.updateTopic(topic, topicId);
        return new ResponseEntity<>(updatedTopic, HttpStatus.OK);
    }

    @Operation(summary = "Find all topics by category", description = "Find a list of all topics by category name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Topic retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Topic.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })

    @GetMapping("/{categoryName}")
    public ResponseEntity<List<Topic>> findAllTopicsByCategory(
            @Parameter(description = "Forum category name", required = true)
            @PathVariable String categoryName) {
        return new ResponseEntity<>(topicService.findAll(categoryName), HttpStatus.OK);
    }

    @Operation(summary = "Delete a topic by title", description = "Delete a topic by its title.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Topic deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Topic is not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })

    @DeleteMapping("/{topicTitle}")
    public ResponseEntity<Void> deleteTopicByTitle(
            @Parameter(description = "Topic title to delete", required = true)
            @PathVariable String topicTitle) {
        topicService.deleteTopic(topicTitle);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Find a topic by name", description = "Find a topic by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Topic found successfully",
                    content = @Content(schema = @Schema(implementation = Topic.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Topic is not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })

    @GetMapping("/title/{topicTitle}")
    public ResponseEntity<Topic> findTopicByTitle(
            @Parameter(description = "Topic name to retrieve", required = true)
            @PathVariable String topicTitle) {
        Topic topic = topicService.findByName(topicTitle);
        return new ResponseEntity<>(topic, topic != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Find a topic by ID", description = "Find a topic by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Topic found successfully",
                    content = @Content(schema = @Schema(implementation = Topic.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Topic is not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })

    @GetMapping("/id/{id}")
    public ResponseEntity<Topic> findTopicById(
            @Parameter(description = "Topic ID to retrieve", required = true)
            @PathVariable Long id) {
        Topic topic = topicService.findById(id);
        return new ResponseEntity<>(topic, topic != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
