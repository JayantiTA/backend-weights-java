package com.example.backend.api.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateRequest {
    @NotNull(message = "id is required")
    private Integer id;
    private String date;
    private Integer max;
    private Integer min;
}
