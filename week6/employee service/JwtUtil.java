package com.employee.employeeservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.HashMap;

@Component // Marks this class as a Spring-managed component for dependency injection
public class JwtUtil {

    // Injects the secret key from application properties (e.g., application.properties or .yml)
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Validates the JWT token and extracts the 'sub' (subject/username) claim.
     *
     * @param token The JWT token string
     * @return The username extracted from the token
     * @throws RuntimeException if the token is invalid or malformed
     */
    public String validateTokenAndGetUsername(String token) {
        try {
            // JWT structure: <header>.<payload>.<signature>
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new RuntimeException("Invalid JWT token format");
            }

            String header = parts[0];      // Base64-encoded header
            String payload = parts[1];     // Base64-encoded payload (claims)
            String signature = parts[2];   // Base64-encoded signature

            String dataToSign = header + "." + payload;
            String expectedSignature = hmacSha256(dataToSign, secret); // Recalculate signature

            // Check if the calculated signature matches the token's signature
            if (!expectedSignature.equals(signature)) {
                throw new RuntimeException("Invalid JWT signature");
            }

            // Decode and parse the payload
            String payloadJson = new String(Base64.getUrlDecoder().decode(payload));
            Map<String, Object> claims = parseClaims(payloadJson);

            // Ensure the 'sub' (subject/username) claim exists
            if (!claims.containsKey("sub")) {
                throw new RuntimeException("Subject (username) missing in token");
            }

            return claims.get("sub").toString(); // Return the username
        } catch (Exception e) {
            throw new RuntimeException("Token validation failed: " + e.getMessage());
        }
    }

    /**
     * Generates the HMAC SHA-256 signature for the given data using the secret key.
     *
     * @param data      The header + payload string
     * @param secretKey The secret key used to sign the token
     * @return The Base64-encoded signature
     */
    private String hmacSha256(String data, String secretKey) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmac = mac.doFinal(data.getBytes());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hmac); // JWT uses URL-safe Base64
    }

    /**
     * Parses a JSON string (very basic, manual parsing) into a Map of claims.
     *
     * @param json The decoded JSON payload string
     * @return A map of key-value pairs representing claims
     */
    private Map<String, Object> parseClaims(String json) {
        Map<String, Object> claims = new HashMap<>();

        // Remove braces and quotes, e.g. {"sub":"john","exp":123456} → sub:john, exp:123456
        json = json.replaceAll("[{}\"]", "");
        String[] entries = json.split(",");

        for (String entry : entries) {
            String[] keyValue = entry.split(":", 2); // Limit to 2 to allow colons in values
            if (keyValue.length == 2) {
                claims.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }

        return claims;
    }
}
