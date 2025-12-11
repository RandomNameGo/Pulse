package com.app.pulse.repositories;

import com.app.pulse.models.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    Boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);

    List<FriendRequest> findByReceiverId(Long receiverId);
}