package com.example.demo.model;

public class ApiChatResponse {
    private String response;
    private String timestamp;
    private String originalMessage;
    private int responseId;

    // Constructor, Getters, and Setters
    public ApiChatResponse(String response, String timestamp, String originalMessage, int responseId) {
        this.response = response;
        this.timestamp = timestamp;
        this.originalMessage = originalMessage;
        this.responseId = responseId;
    }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getOriginalMessage() { return originalMessage; }
    public void setOriginalMessage(String originalMessage) { this.originalMessage = originalMessage; }

    public int getResponseId() { return responseId; }
    public void setResponseId(int responseId) { this.responseId = responseId; }
}