package com.example.backend.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class WeightDTO {
    private LocalDateTime date;
    private Integer max;
    private Integer min;
}
