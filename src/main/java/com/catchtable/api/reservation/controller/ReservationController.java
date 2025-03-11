package com.catchtable.api.reservation.controller;

import com.catchtable.api.user.DTO.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @GetMapping()
    public void getReservations() {}

    @PostMapping()
    public void createReservation() {
    }

    @DeleteMapping()
    public void deleteReservation(@RequestParam long id) {}

    @GetMapping("users")
    public List<UserDTO> getUsers() {}

    @PostMapping("/users")
    public UserDTO createUser(@RequestParam long id) {}

    @PatchMapping("/users")
    public void updateUser(@RequestBody UserDTO userDTO) {}

    @DeleteMapping("/Users")
    public void deleteUser(@RequestParam long id) {}

    @PostMapping("/wating")
    public void createWating(@RequestBody UserDTO userDTO) {}

    @GetMapping("/wating")
    public List<UserDTO> getWatingUsers() {}

    @PatchMapping("/wating")
    public void updateWating(@RequestParam long id) {}

    @DeleteMapping("/wating")
    public void deleteWating(@RequestParam long id) {}
}
