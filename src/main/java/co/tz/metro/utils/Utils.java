package co.tz.metro.utils;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
@Component
public class Utils {
    public String generateUniqueRef(String input) {
        try {
            // Combine input with current time in nanoseconds for uniqueness
            String base = input + Instant.now().toEpochMilli();
            // Generate SHA-256 hash
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            // Encode to Base64, remove non-alphanumeric characters, take 20 chars, and uppercase
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash)
                    .replaceAll("[^A-Za-z0-9]", "")
                    .substring(0, 20)
                    .toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}
