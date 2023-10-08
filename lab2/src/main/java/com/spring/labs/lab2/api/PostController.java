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

	@GetMapping({ "/create", "/create/{id}" })
	public ModelAndView createPost(@PathVariable(value = "id", required = false) Long id) {
		ModelAndView modelAndView = new ModelAndView("posts/create");
		modelAndView.addObject("post", new CreatePostDto());
		modelAndView.addObject("users", userService.findAll());
		if (Optional.ofNullable(id).isPresent()) {
			Post post = postService.findById(id);
			CreatePostDto createPostDto = CreatePostDto.builder().name(post.getName())
					.description(post.getDescription()).username(post.getAuthor().getUsername()).build();
			modelAndView.addObject("post", createPostDto);
			modelAndView.addObject("id", id);
		}
		return modelAndView;
	}

	@GetMapping({ "", "/all" })
	public ModelAndView findAll() {
		ModelAndView modelAndView = new ModelAndView("posts/posts");
		modelAndView.addObject("posts", postService.findAll());
		return modelAndView;
	}

    @GetMapping({"/{name}", "/all/{name}"})
    public ModelAndView findAll(@PathVariable("name") String name) {
        ModelAndView modelAndView = new ModelAndView("posts/posts");
        modelAndView.addObject("posts", postService.findByTopicName(name));
        modelAndView.addObject("name", name);
        return modelAndView;
    }

	@PostMapping({ "/create" })
	public ModelAndView create(@Valid @ModelAttribute("post") CreatePostDto post, BindingResult bindingResult,
			@RequestParam(value = "id", required = false) Long id) {
		ModelAndView modelAndView = new ModelAndView("posts/create");
		if (bindingResult.hasFieldErrors()) {
			return modelAndView.addObject("users", userService.findAll());
		}

		try {
			if (Optional.ofNullable(id).isEmpty()) {
				postService.createPost(post);
			} else {
				System.out.println("UPDATE post");
				postService.update(post, id);
			}
		} catch (Exception e) {
			return modelAndView.addObject("error Message", e.getMessage()).addObject("users", userService.findAll());
		}
		return new ModelAndView("redirect:/posts/all");
	}

	@GetMapping("/find")
	public ModelAndView findByName() {
		return new ModelAndView("findPost");
	}

	@PostMapping("/find")
	public ModelAndView findByName(@RequestParam("title") String title) {
		ModelAndView modelAndView = new ModelAndView("posts");
		modelAndView.addObject("posts", postService.findByTopicName(title));
		return modelAndView;
	}

	@PostMapping("/delete")
	public ModelAndView deleteByName(@RequestParam("name") String name) {
		postService.deleteByName(name);
		return new ModelAndView("redirect:/posts/" + name);
	}
}