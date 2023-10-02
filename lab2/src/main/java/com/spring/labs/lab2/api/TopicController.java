package com.spring.labs.lab2.api;

import com.spring.labs.lab2.domain.Topic;
import com.spring.labs.lab2.dto.CreateTopicDto;
import com.spring.labs.lab2.service.TopicService;
import com.spring.labs.lab2.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/topics")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @GetMapping({"/create", "/create/{topicId}"})
    public ModelAndView createForm(@PathVariable(value = "topicId", required = false) Long topicId) {
        ModelAndView modelAndView = new ModelAndView("topics/create");
        modelAndView.addObject("topic", new CreateTopicDto());
        modelAndView.addObject("author", userService.findAll());
        if (Optional.ofNullable(topicId).isPresent()) {
            Topic topic = topicService.findById(topicId);
            CreateTopicDto createTopicDto = CreateTopicDto.builder()
                    .title(topic.getTitle())
                    .content(topic.getContent())
                    .author(topic.getAuthor())
                    .forumCategory(topic.getForumCategory())
                    .build();
            modelAndView.addObject("topic", createTopicDto);
            modelAndView.addObject("topicId", topicId);
        }
        return modelAndView;
    }

    @PostMapping({"/create"})
    public ModelAndView create(@Valid @ModelAttribute("topic") CreateTopicDto topic, BindingResult bindingResult,
                               @RequestParam(value = "topic", required = false) Long topicId) {
        ModelAndView modelAndView = new ModelAndView("topics/create");
        if (bindingResult.hasFieldErrors()) {
            return modelAndView.addObject("author", userService.findAll());
        }

        try {
            if (Optional.ofNullable(topicId).isEmpty()) {
                topicService.create(topic);
            } else {
                System.out.println("UPDATE");
                topicService.updateTopic(topic, topicId);
            }
        } catch (Exception e) {
            return modelAndView.addObject("errorMessage", e.getMessage())
                    .addObject("author", userService.findAll());
        }
        return new ModelAndView("redirect:/topic/all");
    }
}