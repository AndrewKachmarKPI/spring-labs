package com.spring.labs.lab2.api;

import ch.qos.logback.core.model.Model;
import com.spring.labs.lab2.domain.ForumCategory;
import com.spring.labs.lab2.domain.User;
import com.spring.labs.lab2.dto.CreateForumCategoryDto;
import com.spring.labs.lab2.service.ForumCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class ForumCategoryController {
    @Autowired
    private ForumCategoryService forumCategoryService;

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("createCategory");
        modelAndView.addObject("category", new CreateForumCategoryDto());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute CreateForumCategoryDto forumCategory) {
        forumCategoryService.create(forumCategory);
        return new ModelAndView("redirect:/categories/all");
    }

    @GetMapping({"/", "/all"})
    public ModelAndView findAll() {
        ModelAndView modelAndView = new ModelAndView("forumCategories");
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
