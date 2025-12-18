package com.app.pulse.repositories;

import com.app.pulse.models.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    List<Channel> findByServerId(Long serverId);
}