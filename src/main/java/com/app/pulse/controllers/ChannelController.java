package com.app.pulse.controllers;

import com.app.pulse.dto.response.ApiResponse;
import com.app.pulse.dto.response.ChannelResponse;
import com.app.pulse.services.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pulse/api/v1")
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping("/channel/{serverId}")
    public ResponseEntity<?> createChannel(@PathVariable long serverId, @RequestParam("channel_name") String channelName, @RequestParam("channel_type") String channelType) {
        return ResponseEntity.ok().body(ApiResponse.<String>builder()
                .success(true)
                .message("Success")
                .data(channelService.createChannel(serverId, channelName, channelType))
                .build()
        );
    }

    @GetMapping("/channel/{serverId}")
    public ResponseEntity<?> getChannel(@PathVariable long serverId) {
        return ResponseEntity.ok().body(ApiResponse.<List<ChannelResponse>>builder()
                .success(true)
                .message("Success")
                .data(channelService.getChannelByServer(serverId))
                .build()
        );
    }

}
