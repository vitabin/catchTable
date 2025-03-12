package com.catchtable.api.auth.controller;

import com.catchtable.api.auth.DTO.SignInUserDTO;
import com.catchtable.api.auth.service.AuthService;
import com.catchtable.api.user.DTO.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController  // @Controller + @ResponseBody
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public String createToken() {
        return "success";
    }

    @PostMapping("/singup")
    public UserDTO signUpUser(@RequestBody SignInUserDTO signInUserDTO) {
        return authService.signIn(signInUserDTO);
    }

    @PostMapping("/signin")
    public String singInUser(@RequestBody UserDTO user) {
        return "index";
    }

    @PostMapping("/refresh")
    public String refreshToken(@RequestBody UserDTO user) {
        return "index";
    }

    @PostMapping("/signout")
    public String signOutUser(@RequestBody UserDTO user) {
        return "index";
    }

    @DeleteMapping("/withdraw")
    public String withdrawUser(@RequestBody UserDTO user) {
        return "index";
    }
}
