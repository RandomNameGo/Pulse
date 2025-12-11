package com.app.pulse.controllers;

import com.app.pulse.dto.response.ApiResponse;
import com.app.pulse.services.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pulse/api/v1")
public class ServerController {

    private final ServerService serverService;

    @PostMapping("/server")
    public ResponseEntity<?> createServer(@RequestParam("file") MultipartFile file, @RequestParam("server name") String serverName) throws IOException {
        return ResponseEntity.ok().body(ApiResponse.<String>builder()
                .success(true)
                .message("Success")
                .data(serverService.createServer(file, serverName))
                .build()
        );
    }

}
