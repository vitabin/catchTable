package com.catchtable.api.auth.controller;

import com.catchtable.api.auth.DTO.SignInRequestDTO;
import com.catchtable.api.auth.DTO.SignUpRequestDTO;
import com.catchtable.api.auth.DTO.TokenDTO;
import com.catchtable.api.auth.domain.TokenEntity;
import com.catchtable.api.auth.service.AuthService;
import com.catchtable.api.user.domain.UserRole;
import com.catchtable.response.SuccessResponse;
import com.catchtable.response.success.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public SuccessResponse<Object> signUpUser(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        authService.signUp(signUpRequestDTO.toParams(UserRole.ROLE_USER));
        return SuccessResponse.of(SuccessCode.WITHOUT_CONTENT);
    }

    @PostMapping("/signin")
    public SuccessResponse<TokenDTO> singInUser(@RequestBody SignInRequestDTO signInRequestDTO) {
        TokenEntity token = authService.signIn(signInRequestDTO.getUserName(), signInRequestDTO.getPassword());
        return SuccessResponse.of(SuccessCode.SUCCESS, token.toDTO());
    }

    @PostMapping("/refresh")
    public SuccessResponse<TokenDTO> refreshToken(
        @RequestHeader("Authorization") String refreshToken) {
        TokenEntity token = authService.refresh(refreshToken);
        return SuccessResponse.of(SuccessCode.SUCCESS, token.toDTO());
    }

    @PostMapping("/signout")
    public SuccessResponse<Object> signOutUser(
        @RequestHeader("Authorization") String refreshToken) {
        authService.signOut(refreshToken);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @DeleteMapping("/withdraw")
    public SuccessResponse<Object> withdraw(@RequestHeader("Authorization") String accessToken) {
        authService.withdraw(accessToken);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @GetMapping("/user-name/{username}")
    public SuccessResponse<Boolean> checkUserName(@PathVariable String username) {
        return SuccessResponse.of(SuccessCode.SUCCESS, authService.checkUserName(username));
    }
}
