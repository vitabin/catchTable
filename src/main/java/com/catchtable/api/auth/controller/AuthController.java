package com.catchtable.api.auth.controller;

import com.catchtable.api.auth.service.DTO.*;
import com.catchtable.api.auth.service.AuthService;
import com.catchtable.api.user.service.DTO.UserDTO;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/singup")
    public ResponseEntity<UserDTO> signUpUser(@RequestBody SignUpRequestDTO signUpRequestDTO) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.OK).body(this.authService.signUp(signUpRequestDTO));
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenDTO> singInUser(@RequestBody SignInRequestDTO signInRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(this.authService.signIn(signInRequestDTO));
    }

    @PostMapping("/refresh")
    public TokenDTO refreshToken(@RequestHeader("Authorization") String refreshToken) {
        return this.authService.refresh(refreshToken);
    }

    @PostMapping("/signout")
    public void signOutUser(@RequestHeader("Authorization") String refreshToken) {
        this.authService.signOut(refreshToken);
    }

    @DeleteMapping("/withdraw")
    public void withdrawUser(@RequestHeader("Authorization") String accessToken) {
        this.authService.withdraw(accessToken);
    }

    @GetMapping("/user-name")
    public boolean checkUserName(@RequestParam("userName") String username) {
        return this.authService.checkUserName(username);
    }
}
