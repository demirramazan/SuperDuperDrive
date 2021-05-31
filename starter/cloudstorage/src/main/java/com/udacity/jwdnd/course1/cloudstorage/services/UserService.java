package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final HashService hashService;

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username).isPresent();
    }

    public int createUser(User user) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] saltByte = new byte[16];
        secureRandom.nextBytes(saltByte);
        String encodeSalt = Base64.getEncoder().encodeToString(saltByte);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodeSalt);
        User saveUser = User.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName()).lastName(user.getLastName())
                .salt(encodeSalt).password(hashedPassword)
                .build();
        return userMapper.insertUser(saveUser);
    }

    public User getUserByUserName(String username) {
        return userMapper.getUser(username).orElse(null);
    }
}
