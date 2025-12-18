package com.app.pulse.services;

import com.app.pulse.dto.response.FriendRequestResponse;
import com.app.pulse.models.Friend;
import com.app.pulse.models.FriendRequest;
import com.app.pulse.models.User;
import com.app.pulse.repositories.FriendRepository;
import com.app.pulse.repositories.FriendRequestRepository;
import com.app.pulse.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRequestRepository friendRequestRepository;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final FriendRepository friendRepository;

    @Override
    public String sendFriendRequest(String username) {

        String token = jwtService.getCurrentToken();

        long senderId = jwtService.extractUserId(token);

        Optional<User> sender = userRepository.findById(senderId);

        Optional<User> receiver = userRepository.findByUsername(username);
        if (receiver.isEmpty()) {
            return "User does not exist";
        }

        if (sender.isEmpty()) {
            return "User does not exist";
        }

        if(Objects.equals(sender.get().getId(), receiver.get().getId())) {
            return "You can not send request to your self";
        }

        if(friendRepository.existsByUserIdAndFriendId(sender.get().getId(), receiver.get().getId())) {
            return "You are already friends";
        }

        if(friendRepository.existsByUserIdAndFriendId(receiver.get().getId(), sender.get().getId()) ||
           friendRepository.existsByUserIdAndFriendId(sender.get().getId(), receiver.get().getId())) {
            return "Friend request already been sent";
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender.get());
        friendRequest.setReceiver(receiver.get());
        friendRequest.setCreatedAt(Instant.now());
        friendRequestRepository.save(friendRequest);

        return "Friend request sent";
    }

    @Override
    @Transactional
    public String acceptFriendRequest(long friendRequestId) {

        String token = jwtService.getCurrentToken();

        long receiverId = jwtService.extractUserId(token);

        FriendRequest friendRequest = friendRequestRepository.findById(friendRequestId).
                         orElseThrow(() -> new RuntimeException("Request not found"));

        if(friendRequest.getReceiver().getId() != receiverId) {
            throw new AccessDeniedException("Unauthorized");
        }

        User sender = friendRequest.getSender();
        User receiver = friendRequest.getReceiver();

        Friend friend1 = new Friend();
        friend1.setUser(sender);
        friend1.setFriend(receiver);
        friend1.setCreatedAt(Instant.now());
        friendRepository.save(friend1);

        Friend friend2 = new Friend();
        friend2.setUser(receiver);
        friend2.setFriend(sender);
        friend2.setCreatedAt(Instant.now());
        friendRepository.save(friend2);

        friendRequestRepository.delete(friendRequest);

        return "Accepted friend request";
    }

    @Override
    public String rejectFriendRequest(long friendRequestId) {
        String token = jwtService.getCurrentToken();

        long receiverId = jwtService.extractUserId(token);

        FriendRequest friendRequest = friendRequestRepository.findById(friendRequestId).
                orElseThrow(() -> new RuntimeException("Request not found"));

        if(friendRequest.getReceiver().getId() != receiverId) {
            throw new AccessDeniedException("Unauthorized");
        }

        friendRequestRepository.delete(friendRequest);

        return "Rejected friend request";
    }

    @Override
    public List<FriendRequestResponse> getFriendRequests() {

        String token = jwtService.getCurrentToken();

        long currentUserId = jwtService.extractUserId(token);

        List<FriendRequest> friendRequests = friendRequestRepository.findByReceiverId(currentUserId);

        List<FriendRequestResponse> responses = new ArrayList<>();

        for (FriendRequest friendRequest : friendRequests) {
            FriendRequestResponse friendRequestResponse = new FriendRequestResponse();
            friendRequestResponse.setId(friendRequest.getId());
            friendRequestResponse.setSender(friendRequest.getSender().getUsername());
            friendRequestResponse.setSendAt(friendRequest.getCreatedAt());
            responses.add(friendRequestResponse);
        }

        return responses;
    }
}
