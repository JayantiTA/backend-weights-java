package com.example.backend.api.model;

import java.util.List;

import lombok.Data;

@Data
public class GetWeightsResponse {
    private List<WeightData> data;
    private String message;
}
