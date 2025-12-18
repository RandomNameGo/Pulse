package com.app.pulse.services;

import com.app.pulse.dto.response.FriendRequestResponse;


import java.util.List;

public interface FriendService {

    String sendFriendRequest(String userName);

    String acceptFriendRequest(long friendRequestId);

    String rejectFriendRequest(long friendRequestId);

    List<FriendRequestResponse> getFriendRequests();
}
