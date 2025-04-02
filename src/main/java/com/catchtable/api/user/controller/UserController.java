package com.catchtable.api.user.controller;

import com.catchtable.api.user.service.DTO.UserDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @PatchMapping()
    public void addAvartar() {
    }

    @PostMapping("/bookmarks")
    public void addBookmark() {
    }

    @GetMapping("/reservations")
    public void getUserReservations() {}

    @GetMapping()
    public UserDTO getUser() {
        return new UserDTO();
    }
}
