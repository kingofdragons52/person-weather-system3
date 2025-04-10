package com.example.person.dto;

import lombok.Data;

@Data
public class LocationResponse {
    private String city;
    private Double latitude;
    private Double longitude;
}