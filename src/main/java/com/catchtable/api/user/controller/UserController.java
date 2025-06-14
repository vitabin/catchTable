package com.catchtable.api.user.controller;

import com.catchtable.annotation.Pagination;
import com.catchtable.api.user.DTO.UserDTO;
import com.catchtable.api.user.DTO.UserSegmentResponseDTO;
import com.catchtable.api.user.service.UserService;
import com.catchtable.response.SuccessResponse;
import com.catchtable.response.success.SuccessCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping()
    public void addAvartar() {
    }

    @PostMapping("/bookmarks")
    public void addBookmark() {
    }

    @GetMapping("/reservations")
    public void getUserReservations() {
    }

    @GetMapping()
    public UserDTO getUser() {
        return new UserDTO();
    }

    @GetMapping("/{userId}/segments")
    public SuccessResponse<List<Long>> getUserSegments(@PathVariable("userId") Long userId,
        @Pagination Pageable page) {
        UserSegmentResponseDTO response = userService.getUserSegments(userId, page);
        return SuccessResponse.of(SuccessCode.SUCCESS, response.getSegmentIds());
    }
}
