package com.app.pulse.repositories;

import com.app.pulse.models.DirectConversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DirectConversationRepository extends JpaRepository<DirectConversation, Long> {

    Optional<DirectConversation> findByUser1IdAndUser2Id(Long user1Id, Long user2Id);

    @Query("select c from DirectConversation c where c.user1.id = :userId or c.user2.id = :userId order by c.updatedAt desc")
    List<DirectConversation> findByUserId(long userId);

    DirectConversation findTopByOrderByIdDesc();
}