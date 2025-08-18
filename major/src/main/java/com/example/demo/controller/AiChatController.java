// src/main/java/com/example/demo/controller/AiChatController.java
package com.example.demo.controller;

import com.example.demo.model.ApiChatResponse;
import com.example.demo.model.ChatRequest;
import com.example.demo.model.ChatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://127.0.0.1:3000",
        "http://localhost:5173",
        "http://127.0.0.1:5173",
        "https://orbi-uit.vercel.app/ai"
})
public class AiChatController {

    private static final List<String> STATIC_RESPONSES = Arrays.asList(
            "Hello! I'm your AI assistant. I'm currently running in test mode with static responses.",
            "That's an interesting question! Let me think about that for a moment...",
            "I understand what you're asking. Here's my perspective on that topic.",
            "Thanks for sharing that with me. I appreciate you taking the time to explain.",
            "I'm here to help! Is there anything specific you'd like assistance with?",
            "That's a great point. I hadn't considered it from that angle before.",
            "I'm processing your request. Please give me a moment to formulate a response.",
            "Based on what you've told me, here's what I would recommend...",
            "I see what you mean. That's definitely something worth exploring further.",
            "Let me break that down for you in a way that might be easier to understand.",
            "That's a complex topic with several different aspects to consider.",
            "I appreciate your patience as I work through this response.",
            "From my understanding, there are a few key points to address here.",
            "That's an excellent question that deserves a thoughtful answer.",
            "I'm glad you brought that up - it's an important consideration."
    );

    private final AtomicInteger responseIndex = new AtomicInteger(0);
    private final int PORT = 8080; // Default Spring Boot port, change if needed

    private void simulateDelay() {
        try {
            long delay = ThreadLocalRandom.current().nextLong(500, 2001);
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "healthy",
                "message", "AI Chat Test Server is running",
                "timestamp", Instant.now().toString(),
                "port", PORT
        ));
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        System.out.printf("[%s] Received message: \"%s\"%n", Instant.now(), request.getMessage());

        simulateDelay();

        int currentIndex = responseIndex.getAndIncrement();
        String responseMessage = STATIC_RESPONSES.get(currentIndex % STATIC_RESPONSES.size());

        System.out.printf("[%s] Sending response: \"%s\"%n", Instant.now(), responseMessage);

        ChatResponse response = new ChatResponse(
                responseMessage,
                Instant.now().toString(),
                request.getMessage(),
                currentIndex + 1,
                "test-mode"
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/chat")
    public ResponseEntity<ApiChatResponse> alternativeChat(@RequestBody ChatRequest request) {
        System.out.printf("[%s] API Received message: \"%s\"%n", Instant.now(), request.getMessage());

        simulateDelay();

        int currentIndex = responseIndex.getAndIncrement();
        String responseMessage = STATIC_RESPONSES.get(currentIndex % STATIC_RESPONSES.size());

        ApiChatResponse response = new ApiChatResponse(
                responseMessage,
                Instant.now().toString(),
                request.getMessage(),
                currentIndex + 1
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        return ResponseEntity.ok(Map.of(
                "server", "AI Chat Test Server (Java/Spring Boot)",
                "version", "1.0.0",
                "status", "running",
                "port", PORT,
                "endpoints", List.of("/health", "/chat", "/api/chat", "/status"),
                "totalResponses", responseIndex.get(),
                "availableResponses", STATIC_RESPONSES.size(),
                "timestamp", Instant.now().toString()
        ));
    }
}