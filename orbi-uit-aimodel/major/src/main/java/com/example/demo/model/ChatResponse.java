package com.example.demo.model;

public class ChatResponse {
    private String message;
    private String timestamp;
    private String originalMessage;
    private int responseId;
    private String serverStatus;

    // Constructor, Getters, and Setters
    public ChatResponse(String message, String timestamp, String originalMessage, int responseId, String serverStatus) {
        this.message = message;
        this.timestamp = timestamp;
        this.originalMessage = originalMessage;
        this.responseId = responseId;
        this.serverStatus = serverStatus;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getOriginalMessage() { return originalMessage; }
    public void setOriginalMessage(String originalMessage) { this.originalMessage = originalMessage; }

    public int getResponseId() { return responseId; }
    public void setResponseId(int responseId) { this.responseId = responseId; }

    public String getServerStatus() { return serverStatus; }
    public void setServerStatus(String serverStatus) { this.serverStatus = serverStatus; }
}
