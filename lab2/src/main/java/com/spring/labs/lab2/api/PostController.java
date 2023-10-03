package com.spring.labs.lab2.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.spring.labs.lab2.domain.Post;
import com.spring.labs.lab2.dto.CreatePostDto;
import com.spring.labs.lab2.service.PostService;
import com.spring.labs.lab2.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/posts")
public class PostController {

	@Autowired
	private PostService postService;
	@Autowired
	private UserService userService;

	@GetMapping({ "/create", "/create/{postId}" })
	public ModelAndView getAllPosts(@PathVariable(value = "postId", required = false) long postId) {
		ModelAndView modelAndView = new ModelAndView("categories/create");
		modelAndView.addObject("post", new CreatePostDto());
		modelAndView.addObject("users", userService.findAll());
		if (Optional.ofNullable(postId).isPresent()) {
			Post post = postService.getById(postId);
			CreatePostDto createPostDto = CreatePostDto.builder().author(post.getAuthor().getUsername())
					.content(post.getContent()).upvotes(post.getUpvotes()).downvotes(post.getDownvotes()).build();
			modelAndView.addObject("post", createPostDto);
			modelAndView.addObject("postId", postId);
		}
		return modelAndView;
	}

	@PostMapping({ "/create" })
	public ModelAndView create(@Valid @ModelAttribute("posts") CreatePostDto post, BindingResult bindingResult,
			@RequestParam(value = "postId", required = false) Long postId) {
		ModelAndView modelAndView = new ModelAndView("posts/create");
		if (bindingResult.hasFieldErrors()) {
			return modelAndView.addObject("users", userService.findAll());
		}

		try {
			if (Optional.ofNullable(postId).isEmpty()) {
				postService.createPost(post);
			} else {
				System.out.println("UPDATE");
				postService.update(post, postId);
			}
		} catch (Exception e) {
			return modelAndView.addObject("errorMessage", e.getMessage()).addObject("users", userService.findAll());
		}
		return new ModelAndView("redirect:/posts/all");
	}

	@GetMapping({ "", "/all" })
	public ModelAndView findAll() {
		ModelAndView modelAndView = new ModelAndView("posts/dashboard");
		modelAndView.addObject("posts", postService.findAll());
		return modelAndView;
	}

	@PostMapping("/delete")
	public ModelAndView deleteByName(@RequestParam String postName) {
		postService.deleteByName(postName);
		return new ModelAndView("redirect:/posts/all");
	}

	@GetMapping("/find")
	public ModelAndView findPostByName() {
		return new ModelAndView("findPost");
	}

	@PostMapping("/find")
	public ModelAndView findByName(@RequestParam("postName") String postName) {
		ModelAndView modelAndView = new ModelAndView("post");
		modelAndView.addObject("postName", postService.findByName(postName));
		return modelAndView;
	}
}