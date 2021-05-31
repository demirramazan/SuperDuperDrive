package com.udacity.jwdnd.course1.cloudstorage.util;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UtilService {

    private final UserService userService;

    public Integer getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userByUserName = userService.getUserByUserName(authentication.getName());
        return userByUserName != null ? userByUserName.getUserId() : null;
    }

    public String encodeValue() {
        SecureRandom random = new SecureRandom();
        byte[] value = new byte[16];
        random.nextBytes(value);
        return Base64.getEncoder().encodeToString(value);
    }


}
