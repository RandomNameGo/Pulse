package com.app.pulse.services;

import com.app.pulse.models.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private SecretKey getSecretKey() {
        String secret = "aec5162f0ed647d4bb3cc9c926b2fb6af809992b56055bed2130adb9a1c3de8da55bfd2ef287029cb7a7e36b0d15b14b04d68cde8b060c3e5fbc6fcc7891bbe9";
        return new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
    }

    public String generateToken(User user) {
        try {
            // Header
            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS512)
                    .type(JOSEObjectType.JWT)
                    .build();

            // Payload (claim)
            Date now = new Date();
            long validityInMs = 3600_000;
            Date exp = new Date(now.getTime() + validityInMs);
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .claim("userId", user.getId())
                    .issuer("pulse.com")
                    .issueTime(now)
                    .expirationTime(exp)
                    .build();

            SignedJWT signedJWT = new SignedJWT(header, claims);
            JWSSigner signer = new MACSigner(getSecretKey());
            signedJWT.sign(signer);

            return signedJWT.serialize();

        } catch (JOSEException e) {
            throw new RuntimeException("Cannot generate JWT", e);
        }
    }

    public String getCurrentToken() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) return null;

        HttpServletRequest request = attributes.getRequest();

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    private JWTClaimsSet extractAllClaims(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            signedJWT.verify(new MACVerifier(getSecretKey()));
            return signedJWT.getJWTClaimsSet();
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public Long extractUserId(String token) {
        Object idObj = extractAllClaims(token).getClaim("userId");
        if (idObj == null) return null;
        return Long.parseLong(idObj.toString());
    }
}
