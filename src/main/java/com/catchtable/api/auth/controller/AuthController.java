package com.catchtable.api.auth.controller;

import com.catchtable.api.auth.service.AuthService;
import com.catchtable.api.auth.service.DTO.SignInRequestDTO;
import com.catchtable.api.auth.service.DTO.SignUpRequestDTO;
import com.catchtable.api.auth.service.DTO.TokenDTO;
import com.catchtable.api.user.service.DTO.UserDTO;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
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

    @PostMapping("/singup")
    public ResponseEntity<UserDTO> signUpUser(@RequestBody SignUpRequestDTO signUpRequestDTO) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.OK).body(authService.signUp(signUpRequestDTO));
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenDTO> singInUser(@RequestBody SignInRequestDTO signInRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.signIn(signInRequestDTO));
    }

    @PostMapping("/refresh")
    public TokenDTO refreshToken(@RequestHeader("Authorization") String refreshToken) {
        return authService.refresh(refreshToken);
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
