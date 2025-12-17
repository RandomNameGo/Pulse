package com.app.pulse.controllers;

import com.app.pulse.dto.request.ChatMessageRequest;
import com.app.pulse.dto.response.DirectMessageResponse;
import com.app.pulse.services.DirectMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.net.Socket;

@Controller
@RequiredArgsConstructor
public class MessageSocketController {

    private final DirectMessageService directMessageService;

    @MessageMapping("/chat.sendPrivate")
    public void sendPrivateMessage(@Payload ChatMessageRequest chatMessageRequest) {
        directMessageService.sendMessage(
                chatMessageRequest.getSenderId(),
                chatMessageRequest.getReceiverId(),
                chatMessageRequest.getContent()
        );
    }

}
