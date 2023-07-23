package com.example.backend.api.model;

import lombok.Data;

@Data
public class GetWeightResponse {
    private WeightData data;
    private String message;
}   

