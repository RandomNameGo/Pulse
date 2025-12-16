package com.app.pulse.services;

import com.app.pulse.dto.response.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ServerService {

    String createServer(MultipartFile icon, String serverName) throws IOException;

    List<ServerResponse> getServers();
}
