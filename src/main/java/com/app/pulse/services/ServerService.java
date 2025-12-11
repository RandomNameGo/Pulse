package com.app.pulse.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ServerService {

    String createServer(MultipartFile icon, String serverName) throws IOException;
}
