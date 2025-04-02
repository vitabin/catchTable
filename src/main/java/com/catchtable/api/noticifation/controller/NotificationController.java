package com.catchtable.api.noticifation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    @PostMapping("/reservations")
    public void createReservationNotification(){}

//    @GetMapping()
//    public List<NotificationDTO> getNotifications(){}
//
//    @GetMapping()
//    public NotificationDTO getNotification(@RequestParam long id){}
}
