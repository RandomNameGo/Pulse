package com.app.pulse.repositories;

import com.app.pulse.models.DirectMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {

    List<DirectMessage> findByConversationIdOrderByCreatedAtAsc(Long conversationId);

    Page<DirectMessage> findByConversationId(Long conversationId, Pageable pageable);
}