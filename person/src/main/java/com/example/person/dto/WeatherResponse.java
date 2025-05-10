package com.example.person.dto;

import lombok.Data;

@Data
public class WeatherResponse {
    private Main main;

    @Data
    public static class Main {
        private double temp;
        private double feels_like;
        private double temp_min;
        private double temp_max;
        private int pressure;
        private int humidity;
        private int sea_level;
        private int grnd_level;
    }
}