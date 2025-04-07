package com.catchtable.api.auth.controller;

import com.catchtable.api.auth.DTO.SignInRequestDTO;
import com.catchtable.api.auth.DTO.SignUpRequestDTO;
import com.catchtable.api.auth.DTO.TokenDTO;
import com.catchtable.api.auth.domain.TokenEntity;
import com.catchtable.api.auth.service.AuthService;
import com.catchtable.api.user.domain.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public void signUpUser(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        authService.signUp(signUpRequestDTO.toParams(UserRole.ROLE_USER.toString()));
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenDTO> singInUser(@RequestBody SignInRequestDTO signInRequestDTO) {
        TokenEntity token = authService.signIn(signInRequestDTO.getUserName(), signInRequestDTO.getPassword());

        return ResponseEntity.status(HttpStatus.OK)
                             .body(token.toDTO());
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refreshToken(
        @RequestHeader("Authorization") String refreshToken) {
        TokenEntity token = authService.refresh(refreshToken);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(token.toDTO());
    }

    @PostMapping("/signout")
    public void signOutUser(@RequestHeader("Authorization") String refreshToken) {
        authService.signOut(refreshToken);
    }

    @DeleteMapping("/withdraw")
    public void withdrawUser(@RequestHeader("Authorization") String accessToken) {
        authService.withdraw(accessToken);
    }

    @GetMapping("/user-name")
    public boolean checkUserName(@RequestParam("userName") String username) {
        return authService.checkUserName(username);
    }
}
