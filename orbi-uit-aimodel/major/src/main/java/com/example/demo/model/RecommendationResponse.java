// src/main/java/com/example/demo/model/RecommendationResponse.java
package com.example.demo.model;

import java.util.List;

public class RecommendationResponse{
    private List<Recommendation> recommendations;

    // Standard Getters and Setters
    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }
}