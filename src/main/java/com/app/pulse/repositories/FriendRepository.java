package com.app.pulse.repositories;

import com.app.pulse.models.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    Boolean existsByUserIdAndFriendId(Long userId, Long friendId);

}