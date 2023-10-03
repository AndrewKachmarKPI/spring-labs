package com.spring.labs.lab2.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.spring.labs.lab2.domain.ForumCategory;
import com.spring.labs.lab2.domain.Post;
import com.spring.labs.lab2.dto.CreateForumCategoryDto;
import com.spring.labs.lab2.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	   @Autowired
	private final PostService postService; 
	   
	public PostController(PostService postService) {
		this.postService = postService;
	}

	   @GetMapping({"/create", "/create/{postId}"})
	public List<Post> getAllPosts() {
		return postService.getAllPosts();
	}

	@GetMapping("/{id}")
	public Post getPostById(@PathVariable Long id) {
		return postService.getPostById(id);
	}

    @GetMapping({"/create", "/create/{postId}"})
    public ModelAndView createPost(@PathVariable(value = "id", required = false) Long id) {
        ModelAndView modelAndView = new ModelAndView("posts/create");
        modelAndView.addObject("post", new CreatePostDto());
        modelAndView.addObject("users", userService.findAll());
        if (Optional.ofNullable(postId).isPresent()) {
            ForumCategory forumCategory = forumCategoryService.findById(postId);
            CreateForumCategoryDto createForumCategoryDto = CreateForumCategoryDto.builder()
                    .categoryName(forumCategory.getCategoryName())
                    .description(forumCategory.getDescription())
                    .username(forumCategory.getModerator().getUsername())
                    .build();
            modelAndView.addObject("post", createPostDto);
            modelAndView.addObject("postId", postId);
        }
        return modelAndView;
    }


	@PutMapping("/{id}")
	public void updatePost(@PathVariable Long id, @RequestParam(required = false) String content,
			@RequestParam(required = false) String name, @RequestParam(required = false) int upvotes,
			@RequestParam(required = false) int downvotes) {
		postService.updatePost(id, content, name, upvotes, downvotes);
	}

	@DeleteMapping("/{id}")
	public void deletePost(@PathVariable Long id) {
		postService.deletePost(id);
	}
}