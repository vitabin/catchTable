package com.catchtable.api.auth.service;

import com.catchtable.api.auth.DTO.SignInUserDTO;
import com.catchtable.api.user.domain.UserEntity;
import com.catchtable.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

//    @Transactional
    public UserEntity signIn(SignInUserDTO signInUserDTO) {

    }
}
