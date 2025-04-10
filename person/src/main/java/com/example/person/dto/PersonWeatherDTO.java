package com.example.person.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonWeatherDTO {
    private int personId;
    private String personName;
    private String city;
    private String weatherDescription;
    private Double temperature;
}