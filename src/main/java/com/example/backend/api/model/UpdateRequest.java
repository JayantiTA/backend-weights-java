package com.example.backend.api.model;

import javax.validation.constraints.NotNull;

import com.example.backend.validator.MaxGreaterThanOrEqualMin;

import lombok.Data;

@Data
@MaxGreaterThanOrEqualMin(value = {UpdateRequest.class})
public class UpdateRequest {
    @NotNull(message = "id is required")
    private Integer id;

    private String date;

    @NotNull(message = "max is required")
    private Integer max;

    @NotNull(message = "min is required")
    private Integer min;
}
