package com.app.pulse.repositories;

import com.app.pulse.models.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServerRepository extends JpaRepository<Server, Long> {

    Server findTopByOrderByIdDesc();

}