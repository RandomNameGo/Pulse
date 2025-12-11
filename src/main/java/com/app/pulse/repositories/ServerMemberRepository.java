package com.app.pulse.repositories;

import com.app.pulse.models.ServerMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerMemberRepository extends JpaRepository<ServerMember, Long> {
}