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

	@GetMapping({ "/create/{topicTitle}", "/create/{id}/{topicTitle}" })
	public ModelAndView createPost(@PathVariable(value = "id", required = false) Long id,
			@PathVariable("topicTitle") String topicTitle) {
		ModelAndView modelAndView = new ModelAndView("posts/create");
		modelAndView.addObject("post", new CreatePostDto());
		modelAndView.addObject("authors", userService.findAll());
		modelAndView.addObject("topicTitle", topicTitle);
		if (Optional.ofNullable(id).isPresent()) {
			Post post = postService.findById(id);
			CreatePostDto createPostDto = CreatePostDto.builder()
					.name(post.getName())
					.description(post.getDescription())
					.author(post.getAuthor().getUsername())
					.content(post.getContent()).build();
			modelAndView.addObject("post", createPostDto);
			modelAndView.addObject("id", id);
		}
		return modelAndView;
	}

	@PostMapping({ "/create" })
	public ModelAndView create(@Valid @ModelAttribute("post") CreatePostDto post, BindingResult bindingResult,
			@RequestParam(value = "id", required = false) Long id, @RequestParam("topicTitle") String topicTitle) {
		ModelAndView modelAndView = new ModelAndView("posts/create");
		if (bindingResult.hasFieldErrors()) {
			modelAndView.addObject("topicTitle", topicTitle);
			modelAndView.addObject("users", userService.findAll());
			return modelAndView;
		}

		try {
			if (Optional.ofNullable(id).isEmpty()) {
				postService.createPost(post, topicTitle);
			} else {
				System.out.println("UPDATE post");
				postService.update(post, id);
			}
		} catch (Exception e) {
			return modelAndView.addObject("error Message", e.getMessage()).addObject("users", userService.findAll());
		}
		return new ModelAndView("redirect:/posts/" + topicTitle);
	}
	@GetMapping({ "/{topicTitle}" })
	public ModelAndView findPostsByTitle(@PathVariable("topicTitle") String topicTitle) {
		ModelAndView modelAndView = new ModelAndView("posts/posts");
		modelAndView.addObject("posts", postService.findByTopicName(topicTitle));
		modelAndView.addObject("topicTitle", topicTitle);
		return modelAndView;
	}

	@GetMapping({ "/post/{name}" })
	public ModelAndView findPost(@PathVariable("name") String name) {
		ModelAndView modelAndView = new ModelAndView("posts/post");
		modelAndView.addObject("post", postService.findByPostName(name));
		modelAndView.addObject("name", name);
		return modelAndView;
	}


	@PostMapping("/delete")
	public ModelAndView deleteByName(@RequestParam String name, @RequestParam("topic") String topic) {
		postService.deleteByName(name);
		return new ModelAndView("redirect:/posts/" + topic);
	}
}