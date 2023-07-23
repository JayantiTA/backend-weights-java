package com.example.backend.api.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CreateRequest {
    @NotNull(message = "date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "max is required")
    private Integer max;

    @NotNull(message = "min is required")
    private Integer min;
}
