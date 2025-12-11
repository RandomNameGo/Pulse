package com.app.pulse.services;


import com.app.pulse.models.Server;
import com.app.pulse.models.ServerMember;
import com.app.pulse.models.User;
import com.app.pulse.repositories.ServerMemberRepository;
import com.app.pulse.repositories.ServerRepository;
import com.app.pulse.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;

    private final ServerMemberRepository serverMemberRepository;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final CloudinaryService cloudinaryService;

    @Override
    public String createServer(MultipartFile icon, String serverName) throws IOException {

        String token = jwtService.getCurrentToken();

        long currentUserId = jwtService.extractUserId(token);

        Optional<User> user = userRepository.findById(currentUserId);

        User owner = null;
        if (user.isPresent()) {
            owner = user.get();
        }

        Server server = new Server();

        String iconUrl = cloudinaryService.uploadFile(icon);

        server.setName(serverName);
        server.setIconUrl(iconUrl);
        server.setOwner(owner);
        server.setCreatedAt(Instant.now());
        serverRepository.save(server);

        Server tempServer = serverRepository.findTopByOrderByIdDesc();
        ServerMember serverMember = new ServerMember();
        serverMember.setServer(tempServer);
        serverMember.setUser(owner);
        serverMember.setRole("SERVER_ADMIN");
        serverMember.setJoinedAt(Instant.now());
        serverMemberRepository.save(serverMember);

        return "Created server successfully";
    }
}
