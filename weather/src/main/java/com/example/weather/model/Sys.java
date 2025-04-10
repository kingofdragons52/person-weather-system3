package com.example.weather.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sys{
    private String country;
    private int sunrise;
    private int sunset;
}