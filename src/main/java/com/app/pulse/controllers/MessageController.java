package com.app.pulse.controllers;

import com.app.pulse.dto.response.ApiResponse;
import com.app.pulse.dto.response.DirectConversationResponse;
import com.app.pulse.dto.response.DirectMessageResponse;
import com.app.pulse.services.DirectMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/pulse/api/v1/chat")
public class MessageController {

    private final DirectMessageService directMessageService;

    @GetMapping("/conversation")
    public ResponseEntity<?> getUserConversation() {
        return ResponseEntity.ok().body(ApiResponse.<List<DirectConversationResponse>>builder()
                .success(true)
                .message("Success")
                .data(directMessageService.getUserConversations())
                .build()
        );
    }

    @GetMapping("/messages/{conversationId}")
    public ResponseEntity<?> getConversationMessage(
            @PathVariable Long conversationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok().body(ApiResponse.<List<DirectMessageResponse>>builder()
                .success(true)
                .message("Success")
                .data(directMessageService.getConversationMessage(conversationId, page, size))
                .build()
        );
    }
}
