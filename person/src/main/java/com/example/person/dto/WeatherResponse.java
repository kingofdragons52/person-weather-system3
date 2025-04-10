package com.example.person.dto;

import lombok.Data;

@Data
public class WeatherResponse {
    private String description;
    private Double temperature;
}