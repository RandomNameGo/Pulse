package com.app.pulse.services;

import com.app.pulse.dto.response.DirectConversationResponse;
import com.app.pulse.dto.response.DirectMessageResponse;
import com.app.pulse.models.DirectConversation;
import com.app.pulse.models.DirectMessage;
import com.app.pulse.repositories.DirectConversationRepository;
import com.app.pulse.repositories.DirectMessageRepository;
import com.app.pulse.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DirectMessageServiceImpl implements DirectMessageService {

    private final DirectConversationRepository directConversationRepository;

    private final DirectMessageRepository directMessageRepository;

    private final UserRepository userRepository;

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final JwtService jwtService;

    @Override
    public DirectConversation getOrCreateConversation(Long userAId, Long userBId) {

        long uId1 = Math.min(userAId, userBId);
        long uId2 = Math.max(userAId, userBId);

        Optional<DirectConversation> conversation = directConversationRepository.findByUser1IdAndUser2Id(uId1, uId2);
        if (conversation.isPresent()) {
            return conversation.get();
        }

        DirectConversation newDirectConversation = new DirectConversation();
        newDirectConversation.setUser1(userRepository.getReferenceById(uId1));
        newDirectConversation.setUser2(userRepository.getReferenceById(uId2));
        newDirectConversation.setCreatedAt(Instant.now());
        newDirectConversation.setUpdatedAt(Instant.now());

        return directConversationRepository.save(newDirectConversation);
    }

    @Override
    @Transactional
    public DirectMessageResponse sendMessage(Long senderId, Long receiverId, String content) {

        DirectConversation conversation = getOrCreateConversation(senderId, receiverId);

        DirectMessage directMessage = new DirectMessage();
        directMessage.setConversation(conversation);
        directMessage.setSender(userRepository.getReferenceById(senderId));
        directMessage.setContent(content);
        directMessage.setCreatedAt(Instant.now());
        directMessage.setIsRead(false);

        DirectMessage savedDirectMessage = directMessageRepository.save(directMessage);

        conversation.setUpdatedAt(Instant.now());
        directConversationRepository.save(conversation);

        DirectMessageResponse response = convertToDto(savedDirectMessage);

        simpMessagingTemplate.convertAndSend(
                "/topic/dm/" + conversation.getId(),
                response
        );

        return response;
    }

    @Override
    public List<DirectConversationResponse> getUserConversations() {

        String token = jwtService.getCurrentToken();

        long currentUserId = jwtService.extractUserId(token);

        List<DirectConversation> conversation = directConversationRepository.findByUserId(currentUserId);

        List<DirectConversationResponse> responses = new ArrayList<>();

        for (DirectConversation directConversation : conversation) {
            DirectConversationResponse directConversationResponse = new DirectConversationResponse();
            directConversationResponse.setDirectConversationId(directConversation.getId());
            directConversationResponse.setUser1Id(directConversation.getUser1().getId());
            directConversationResponse.setUser2Id(directConversation.getUser2().getId());
            directConversationResponse.setUpdatedAt(directConversation.getUpdatedAt());
            responses.add(directConversationResponse);
        }

        return responses;
    }

    @Override
    public List<DirectMessageResponse> getConversationMessage(Long conversationId, int page, int size) {

        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());

        // 2. GỌI REPOSITORY
        Page<DirectMessage> pageResult = directMessageRepository.findByConversationId(conversationId, paging);

        // 3. CONVERT ENTITY -> DTO
        // Lấy list entity ra khỏi pageResult
        List<DirectMessage> entities = pageResult.getContent();

        // Map từng phần tử sang DTO
        List<DirectMessageResponse> responses = entities.stream()
                .map(this::convertToDto) // Gọi hàm mapper bên dưới
                .collect(Collectors.toList());

        // 4. ĐẢO NGƯỢC LIST (QUAN TRỌNG)
        // Vì bước 1 ta sort DESC (Tin mới ở đầu: [Tin mới, ..., Tin cũ])
        // Nhưng Frontend chat cần hiển thị theo thời gian xuôi (Tin cũ ở trên, Tin mới ở dưới).
        // Nên ta đảo ngược lại thành: [Tin cũ, ..., Tin mới]
        Collections.reverse(responses);

        return responses;

    }

    private DirectMessageResponse convertToDto(DirectMessage msg) {
        return DirectMessageResponse.builder()
                .id(msg.getId())
                .content(msg.getContent())
                .senderId(msg.getSender().getId())
                .senderName(msg.getSender().getUsername())
                .senderAvatar(msg.getSender().getAvatarUrl())
                .sentAt(msg.getCreatedAt().toString())
                .build();
    }
}
