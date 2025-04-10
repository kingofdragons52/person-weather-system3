package com.example.weather.controller;

import com.example.weather.model.Root;
import com.example.weather.model.Main;
import com.example.weather.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${appid}")
    private String appId;

    @GetMapping
    @Cacheable("weatherCache")
    public Weather getWeather(@RequestParam double lat, @RequestParam double lon) {
        String url = String.format(
                "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s&units=metric",
                lat, lon, appId
        );

        Root response = restTemplate.getForObject(url, Root.class);

        // Конвертируем ответ от OpenWeatherMap в наш формат Weather
        return convertToWeatherModel(response);
    }

    private Weather convertToWeatherModel(Root response) {
        if (response == null || response.getMain() == null) {
            throw new RuntimeException("Weather data not available");
        }

        Main mainData = response.getMain();
        Weather weather = new Weather();

        weather.setTemp(mainData.getTemp());
        weather.setFeels_like(mainData.getFeels_like());
        weather.setTemp_min(mainData.getTemp_min());
        weather.setTemp_max(mainData.getTemp_max());
        weather.setPressure(mainData.getPressure());
        weather.setHumidity(mainData.getHumidity());
        weather.setSea_level(mainData.getSea_level());
        weather.setGrnd_level(mainData.getGrnd_level());

        return weather;
    }
}