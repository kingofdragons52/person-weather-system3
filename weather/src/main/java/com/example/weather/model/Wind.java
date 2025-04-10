package com.example.weather.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wind{
    private double speed;
    private int deg;
    private double gust;
}