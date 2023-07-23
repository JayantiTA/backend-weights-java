package com.example.backend.api.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class WeightData {
    private Integer id;
    private LocalDateTime date;
    private Integer max;
    private Integer min;
}
