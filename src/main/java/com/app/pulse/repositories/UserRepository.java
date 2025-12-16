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

    @Query("select s.user from ServerMember s where s.server.id = :serverId")
    List<User> findByServer(long serverId);

}