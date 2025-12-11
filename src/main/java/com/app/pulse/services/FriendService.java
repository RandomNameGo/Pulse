package com.app.pulse.services;

import com.app.pulse.dto.response.FriendRequestResponse;


import java.util.List;

public interface FriendService {

    String sendFriendRequest(String userName);

    String acceptFriendRequest(Long friendRequestId);

    String rejectFriendRequest(Long friendRequestId);

    List<FriendRequestResponse> getFriendRequests();
}
