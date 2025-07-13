package com.user.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Base64;

/**
 * Utility class for generating and validating JWT tokens manually.
 * This implementation avoids using third-party JWT libraries (e.g., jjwt).
 */
@Component
public class JwtUtil {

    // Secret key loaded from application.properties (jwt.secret)
    @Value("${jwt.secret}")
    private String secret;

    // JWT signing algorithm used
    private static final String HMAC_ALGO = "HmacSHA256";

    // Jackson ObjectMapper to convert Java objects to JSON strings and vice versa
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Generates a JWT token using HMAC SHA-256.
     *
     * @param username the subject (user identifier) to include in the token
     * @return a JWT string (header.payload.signature)
     */
    public String generateToken(String username) {
        try {
            // JWT Header
            Map<String, Object> header = new HashMap<>();
            header.put("alg", "HS256"); // Algorithm
            header.put("typ", "JWT");   // Token type

            // JWT Payload
            Map<String, Object> payload = new HashMap<>();
            payload.put("sub", username);                       // Subject = username
            payload.put("iat", System.currentTimeMillis());    // Issued at
            payload.put("exp", System.currentTimeMillis() + 3600000); // Expiry = now + 1 hour

            // Encode header and payload to Base64URL format
            String encodedHeader = base64Encode(objectMapper.writeValueAsString(header));
            String encodedPayload = base64Encode(objectMapper.writeValueAsString(payload));

            // Create signature from header + payload
            String data = encodedHeader + "." + encodedPayload;
            String signature = hmacSha256(data, secret);

            // Return full token
            return data + "." + signature;

        } catch (Exception e) {
            throw new RuntimeException("Error while generating JWT", e);
        }
    }

    /**
     * Validates a JWT token and extracts the username (subject).
     *
     * @param token the JWT string
     * @return the username (subject) if valid
     */
    public String validateTokenAndGetUsername(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new RuntimeException("Invalid token format");
            }

            String header = parts[0];
            String payload = parts[1];
            String signature = parts[2];

            // Verify the signature
            String expectedSignature = hmacSha256(header + "." + payload, secret);
            if (!signature.equals(expectedSignature)) {
                throw new RuntimeException("Invalid token signature");
            }

            // Decode and parse payload
            String payloadJson = new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);
            Map<String, Object> payloadMap = objectMapper.readValue(payloadJson, Map.class);

            // Check expiration
            long exp = ((Number) payloadMap.get("exp")).longValue();
            if (System.currentTimeMillis() > exp) {
                throw new RuntimeException("Token expired");
            }

            // Return subject (username)
            return (String) payloadMap.get("sub");

        } catch (Exception e) {
            throw new RuntimeException("Token validation failed: " + e.getMessage());
        }
    }

    /**
     * Encodes a string to Base64URL (without padding).
     *
     * @param value the raw string to encode
     * @return encoded string
     */
    private String base64Encode(String value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Computes HMAC SHA-256 signature using the secret key.
     *
     * @param data   the string to sign (header.payload)
     * @param secret the secret key used to sign
     * @return Base64URL encoded signature
     */
    private String hmacSha256(String data, String secret) throws Exception {
        Mac mac = Mac.getInstance(HMAC_ALGO);
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_ALGO);
        mac.init(secretKey);
        byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
    }
}
