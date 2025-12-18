package com.app.pulse.services;

import com.app.pulse.dto.response.ChannelResponse;
import com.app.pulse.mappers.ChannelMapper;
import com.app.pulse.models.Channel;
import com.app.pulse.models.Server;
import com.app.pulse.models.User;
import com.app.pulse.repositories.ChannelRepository;
import com.app.pulse.repositories.ServerRepository;
import com.app.pulse.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;

    private final ChannelMapper channelMapper;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final ServerRepository serverRepository;

    @Override
    public List<ChannelResponse> getChannelByServer(long serverId) {

        List<Channel> channels = channelRepository.findByServerId(serverId);

        List<ChannelResponse> responses = new ArrayList<>();
        for (Channel channel : channels) {
            responses.add(channelMapper.toChannelResponse(channel));
        }

        return responses;
    }

    @Override
    public String createChannel(long serverId, String name, String type) {

        String token = jwtService.getCurrentToken();

        long currentUserId = jwtService.extractUserId(token);

        Optional<User> admin = userRepository.isServerAdmin(currentUserId, serverId);

        Optional<Server> server = serverRepository.findById(serverId);

        if (admin.isPresent() && server.isPresent()) {
            Channel channel = new Channel();
            channel.setServer(server.get());
            channel.setName(name);
            channel.setType(type);
            channel.setCreatedAt(Instant.now());
            channel.setUpdatedAt(Instant.now());
            channelRepository.save(channel);
            return "Created channel successfully";
        }

        return "Only Admin can create a new channel";
    }
}
