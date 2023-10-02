package com.spring.labs.lab2.api;

import com.spring.labs.lab2.domain.ForumCategory;
import com.spring.labs.lab2.dto.CreateForumCategoryDto;
import com.spring.labs.lab2.service.ForumCategoryService;
import com.spring.labs.lab2.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private ForumCategoryService forumCategoryService;
    @Autowired
    private UserService userService;

    @GetMapping({"/create", "/create/{categoryId}"})
    public ModelAndView createForm(@PathVariable(value = "categoryId", required = false) Long categoryId) {
        ModelAndView modelAndView = new ModelAndView("categories/create");
        modelAndView.addObject("category", new CreateForumCategoryDto());
        modelAndView.addObject("users", userService.findAll());
        if (Optional.ofNullable(categoryId).isPresent()) {
            ForumCategory forumCategory = forumCategoryService.findById(categoryId);
            CreateForumCategoryDto createForumCategoryDto = CreateForumCategoryDto.builder()
                    .categoryName(forumCategory.getCategoryName())
                    .description(forumCategory.getDescription())
                    .username(forumCategory.getModerator().getUsername())
                    .build();
            modelAndView.addObject("category", createForumCategoryDto);
            modelAndView.addObject("categoryId", categoryId);
        }
        return modelAndView;
    }

    @PostMapping({"/create"})
    public ModelAndView create(@Valid @ModelAttribute("category") CreateForumCategoryDto category, BindingResult bindingResult,
                               @RequestParam(value = "categoryId", required = false) Long categoryId) {
        ModelAndView modelAndView = new ModelAndView("categories/create");
        if (bindingResult.hasFieldErrors()) {
            return modelAndView.addObject("users", userService.findAll());
        }

        try {
            if (Optional.ofNullable(categoryId).isEmpty()) {
                forumCategoryService.create(category);
            } else {
                System.out.println("UPDATE");
                forumCategoryService.update(category, categoryId);
            }
        } catch (Exception e) {
            return modelAndView.addObject("errorMessage", e.getMessage())
                    .addObject("users", userService.findAll());
        }
        return new ModelAndView("redirect:/categories/all");
    }

    @GetMapping({"", "/all"})
    public ModelAndView findAll() {
        ModelAndView modelAndView = new ModelAndView("categories/dashboard");
        modelAndView.addObject("categories", forumCategoryService.findAll());
        return modelAndView;
    }

    @PostMapping("/delete")
    public ModelAndView deleteByName(@RequestParam String categoryName) {
        forumCategoryService.deleteByName(categoryName);
        return new ModelAndView("redirect:/categories/all");
    }

    @GetMapping("/find")
    public ModelAndView findByNameForm() {
        return new ModelAndView("findForumCategory");
    }

    @PostMapping("/find")
    public ModelAndView findByName(@RequestParam("categoryName") String categoryName) {
        ModelAndView modelAndView = new ModelAndView("forumCategory");
        modelAndView.addObject("category", forumCategoryService.findByName(categoryName));
        return modelAndView;
    }

//    @GetMapping("/category/{categoryName}")
//    public ModelAndView findByName(@PathVariable String categoryName) {
//        ModelAndView modelAndView = new ModelAndView("categories");
//        modelAndView.addObject("category", forumCategoryService.findByName(categoryName));
//        return modelAndView;
//    }

//    @PatchMapping("/{categoryName}")
//    public ModelAndView changeModerator(@PathVariable String categoryName, @RequestParam String username) {
//        ModelAndView modelAndView = new ModelAndView("categories");
//        modelAndView.addObject("category", forumCategoryService.changeModerator(categoryName, username));
//        return modelAndView;
//    }
}
