package com.app.pulse.repositories;

import com.app.pulse.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByDisplayName(String displayName);

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    @Query("select sm.user from ServerMember sm where sm.server.id = :serverId")
    List<User> findByServer(Long serverId);

    @Query("select sm.user from ServerMember sm where sm.user.id = :userId and sm.server.id = :serverId and sm.role = 'SERVER_ADMIN'")
    Optional<User> isServerAdmin(Long userId, Long serverId);
}