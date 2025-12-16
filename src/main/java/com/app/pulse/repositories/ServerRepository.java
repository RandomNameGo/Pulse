package com.app.pulse.repositories;

import com.app.pulse.models.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServerRepository extends JpaRepository<Server, Long> {

    Server findTopByOrderByIdDesc();

    @Query("select s.server from ServerMember s where s.user.id = :userId")
    List<Server> findByUserId(long userId);
}