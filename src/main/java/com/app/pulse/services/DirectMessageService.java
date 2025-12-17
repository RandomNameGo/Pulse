package com.app.pulse.services;

import com.app.pulse.dto.response.DirectConversationResponse;
import com.app.pulse.dto.response.DirectMessageResponse;
import com.app.pulse.models.DirectConversation;
import com.app.pulse.models.DirectMessage;

import java.util.List;

public interface DirectMessageService {

    DirectConversation getOrCreateConversation(Long userAId, Long userBId);

    DirectMessageResponse sendMessage(Long senderId, Long receiverId, String content);

    List<DirectConversationResponse> getUserConversations();

    List<DirectMessageResponse> getConversationMessage(Long conversationId, int page, int size);
}
