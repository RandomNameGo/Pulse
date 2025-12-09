package com.app.pulse.repositories;

import com.app.pulse.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByDisplayName(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}