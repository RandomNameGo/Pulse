package com.app.pulse.controllers;

import com.app.pulse.dto.response.ApiResponse;
import com.app.pulse.dto.response.FriendRequestResponse;
import com.app.pulse.services.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pulse/api/v1")
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/friend/send-friend-request")
    public ResponseEntity<?> sendFriendRequest(@RequestParam("username") String username) {
        return ResponseEntity.ok().body(ApiResponse.<String>builder()
                .success(true)
                .message("success")
                .data(friendService.sendFriendRequest(username))
                .build()
        );
    }

    @PostMapping("/friend/accept-friend-request")
    public ResponseEntity<?> acceptFriendRequest(@RequestParam("requestId") long requestId) {
        return ResponseEntity.ok().body(ApiResponse.<String>builder()
                .success(true)
                .message("success")
                .data(friendService.acceptFriendRequest(requestId))
                .build()
        );
    }

    @PostMapping("/friend/reject-friend-request")
    public ResponseEntity<?> rejectFriendRequest(@RequestParam("requestId") long requestId) {
        return ResponseEntity.ok().body(ApiResponse.<String>builder()
                .success(true)
                .message("success")
                .data(friendService.rejectFriendRequest(requestId))
                .build()
        );
    }

    @GetMapping("/friend/show-friend-request")
    public ResponseEntity<?> getFriendRequests() {
        return ResponseEntity.ok().body(ApiResponse.<List<FriendRequestResponse>>builder()
                .success(true)
                .message("success")
                .data(friendService.getFriendRequests())
                .build()
        );
    }

}
