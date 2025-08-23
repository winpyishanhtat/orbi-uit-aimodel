// src/main/java/com/example/demo/model/Recommendation.java
package com.example.demo.model;

public class Recommendation {
    private String major;
    private double probability;

    // Standard Getters and Setters
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}