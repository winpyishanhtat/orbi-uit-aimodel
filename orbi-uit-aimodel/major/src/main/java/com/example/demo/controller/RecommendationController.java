package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
// ADD THIS IMPORT STATEMENT
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.RecommendationResponse;
import com.example.demo.model.UserInput;

@RestController
@RequestMapping("/api")
// ADD THIS ANNOTATION BLOCK
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://127.0.0.1:3000",
    "http://localhost:5173",
    "http://127.0.0.1:5173"
})
public class RecommendationController {

    private final RestTemplate restTemplate;

    @Autowired
    public RecommendationController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/recommend")
    public RecommendationResponse getRecommendation(@RequestBody UserInput userInput) {
        String flaskUrl = "http://localhost:5000/predict";
        return restTemplate.postForObject(flaskUrl, userInput, RecommendationResponse.class);
    }
}