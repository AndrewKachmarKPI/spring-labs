package com.spring.labs.lab2.dto;

import com.spring.labs.lab2.domain.ForumCategory;
import com.spring.labs.lab2.domain.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreateTopicDto {
    private String title;
    private String content;
    private User author;
    private ForumCategory forumCategory;

    public CreateTopicDto() {

    }
}
