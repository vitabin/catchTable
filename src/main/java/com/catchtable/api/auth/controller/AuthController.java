package com.catchtable.api.auth.controller;

import com.catchtable.api.user.DTO.UserDTO;
import com.catchtable.api.user.repository.User;
import org.springframework.web.bind.annotation.*;

@RestController  // @Controller + @ResponseBody
@RequestMapping("/auth")
public class AuthController {

    @PostMapping
    public String createToken() {
        return "success";
    }

    @PostMapping("/singup")
    public UserDTO signUpUser(@RequestBody User user) {
        return UserDTO.of(user);
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
