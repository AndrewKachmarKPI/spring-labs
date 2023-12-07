package com.spring.labs.lab6.serviceImpl;

import com.spring.labs.lab6.domain.PostEntity;
import com.spring.labs.lab6.dto.PostDto;
import com.spring.labs.lab6.dto.create.CreatePostDto;
import com.spring.labs.lab6.exceptions.ResourceAlreadyExistsException;
import com.spring.labs.lab6.exceptions.ResourceNotFoundException;
import com.spring.labs.lab6.mapper.BusinessMapper;
import com.spring.labs.lab6.repositories.PostRepository;
import com.spring.labs.lab6.repositories.TopicRepository;
import com.spring.labs.lab6.repositories.UserRepository;
import com.spring.labs.lab6.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final BusinessMapper businessMapper;


    @Override
    public PostDto update(CreatePostDto createPost, Long postId) {
        if (postRepository.existsByName(createPost.getName())) {
            throw new ResourceAlreadyExistsException("Post with name " + createPost.getName() + " already exists");
        }
        PostEntity post = findEntityById(postId);
        post = post.toBuilder()
                .name(createPost.getName())
                .content(createPost.getContent())
                .topic(topicRepository.findByName(createPost.getTopicTitle()).get())
                .author(userRepository.findByUsername(createPost.getAuthor()).get())
                .upVotes(post.getUpVotes())
                .downVotes(post.getDownVotes())
                .description(createPost.getDescription())
                .build();
        return businessMapper.getPost(postRepository.save(post));
    }

    @Override
    @Transactional
    public void deleteByName(String postName) {
        if (!postRepository.existsByName(postName)) {
            throw new ResourceNotFoundException("Post with name " + postName + " is not found");
        }
        postRepository.deleteByName(postName);
    }
    @Override
    public List<PostDto> findByTopicName(String topicTitle) {
        return businessMapper.collectionToList(postRepository.findByTopicTitle(topicTitle), businessMapper.postEntityToDto);
    }

    @Override
    public PostDto findById(Long id) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id:" + id + " is not found"));
        return businessMapper.getPost(post);
    }
    @Override
    public PostDto findByPostName(String postName) {
        PostEntity postEntity = postRepository.findByName(postName)
                .orElseThrow(() -> new ResourceNotFoundException("Post with name:" + postName + " is not found"));
        return businessMapper.getPost(postEntity);
    }

    @Override
    public PostDto createPost(CreatePostDto postDto, String topicTitle) {
        if (postRepository.existsByName(postDto.getName())) {
            throw new ResourceAlreadyExistsException("Post with name " + postDto.getName() + " already exists");
        }
        PostEntity post = PostEntity.builder()
                .content(postDto.getContent())
                .description(postDto.getDescription())
                .name(postDto.getName())
                .author(userRepository.findByUsername(postDto.getAuthor()).get())
                .creationDate(LocalDateTime.now())
                .topic(topicRepository.findByName(topicTitle)
                        .orElseThrow(() -> new ResourceNotFoundException("Topic with name:" + topicTitle + " is not found")))
                .build();
        postRepository.save(post);
        return businessMapper.getPost(post);
    }


    @Override
    public List<PostDto> findAll() {
        return businessMapper.collectionToList(postRepository.findAll(), businessMapper.postEntityToDto);
    }
    public PostEntity findEntityById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id:" + id + " is not found"));
    }
}
