package com.spring.labs.lab6.serviceImpl;

import com.spring.labs.lab6.domain.UserEntity;
import com.spring.labs.lab6.dto.UserDto;
import com.spring.labs.lab6.dto.create.CreateUserDto;
import com.spring.labs.lab6.enums.UserRole;
import com.spring.labs.lab6.exceptions.ResourceAlreadyExistsException;
import com.spring.labs.lab6.exceptions.ResourceNotFoundException;
import com.spring.labs.lab6.mapper.BusinessMapper;
import com.spring.labs.lab6.repositories.UserRepository;
import com.spring.labs.lab6.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public final UserRepository userRepository;
    private final BusinessMapper businessMapper;

    @Override
    public UserDto findUserByName(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with name:" + username + " is not found"));
        return businessMapper.getUserDto(user);
    }

    @Override
    public Boolean existByName(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public List<UserDto> findAll() {
        return businessMapper.collectionToList(userRepository.findAll(), businessMapper.userEntityToDto);
    }

    @Override
    public UserDto saveUser(CreateUserDto createUserDto) {
        if (userRepository.existsByUsername(createUserDto.getUsername())) {
            throw new ResourceAlreadyExistsException("User with name:" + createUserDto.getUsername() + " already exist");
        }
        UserEntity userEntity = UserEntity.builder()
                .registrationDate(LocalDateTime.now())
                .profilePicture(createUserDto.getProfilePicture())
                .role(createUserDto.getRole())
                .password(createUserDto.getPassword())
                .email(createUserDto.getEmail())
                .biography(createUserDto.getBiography())
                .username(createUserDto.getUsername())
                .build();
        return businessMapper.getUserDto(userRepository.save(userEntity));
    }
}
