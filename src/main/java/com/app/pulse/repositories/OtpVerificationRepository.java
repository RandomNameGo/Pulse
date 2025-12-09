package com.app.pulse.repositories;

import com.app.pulse.models.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {
    OtpVerification findTopByEmailOrderByExpiresAtDesc(String email);

}