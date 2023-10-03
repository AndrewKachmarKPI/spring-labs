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
        modelAndView.addObject("authors", userService.findAll());
        if (Optional.ofNullable(topicId).isPresent()) {
            Topic topic = topicService.findById(topicId);
            CreateTopicDto createTopicDto = CreateTopicDto.builder()
                    .title(topic.getTitle())
                    .content(topic.getContent())
                    .author(topic.getAuthor().getUsername())
                    .forumCategory(topic.getForumCategory())
                    .build();
            modelAndView.addObject("topic", createTopicDto);
            modelAndView.addObject("topicId", topicId);
        }
        return modelAndView;
    }

    @PostMapping({"/create"})
    public ModelAndView create(@Valid @ModelAttribute("topic") CreateTopicDto topic, BindingResult bindingResult,
                               @RequestParam(value = "id", required = false) Long id) {
        ModelAndView modelAndView = new ModelAndView("topics/create");
        if (bindingResult.hasFieldErrors()) {
            return modelAndView.addObject("authors", userService.findAll());
        }

        try {
            if (Optional.ofNullable(id).isEmpty()) {
                topicService.create(topic);
            } else {
                System.out.println("UPDATE");
                topicService.updateTopic(topic, id);
            }
        } catch (Exception e) {
            return modelAndView.addObject("errorMessage", e.getMessage())
                    .addObject("authors", userService.findAll());
        }
        return new ModelAndView("redirect:/topics/all");
    }

    @GetMapping({"", "/all"})
    public ModelAndView findAll() {
        ModelAndView modelAndView = new ModelAndView("topics/view");
        modelAndView.addObject("topics", topicService.findAll());
        return modelAndView;
    }

    @GetMapping("/find")
    public ModelAndView findByNameForm() {
        return new ModelAndView("findTopic");
    }
    @PostMapping("/find")
    public ModelAndView findByName(@RequestParam("title") String title) {
        ModelAndView modelAndView = new ModelAndView("topic");
        modelAndView.addObject("topic", topicService.findByName(title));
        return modelAndView;
    }
}
