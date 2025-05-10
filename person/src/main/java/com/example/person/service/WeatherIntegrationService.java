package com.example.person.service;

import com.example.person.dto.LocationResponse;
import com.example.person.dto.WeatherResponse;
import com.example.person.model.Person;
import com.example.person.model.Weather;
import com.example.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class WeatherIntegrationService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${location.service.url}")
    private String locationServiceUrl;

    @Value("${weather.service.url}")
    private String weatherServiceUrl;

    public Weather getWeatherByPersonId(Integer personId) {
        try {
            System.out.println("=== Starting weather request for personId: " + personId + " ===");

            Person person = personRepository.findById(personId)
                    .orElseThrow(() -> new RuntimeException("Person not found"));
            System.out.println("Found person: " + person.getFirstname() + " with location: " + person.getLocation());

            String locationUrl = locationServiceUrl + "/location?name=" + URLEncoder.encode(person.getLocation(), StandardCharsets.UTF_8);
            System.out.println("Requesting location from: " + locationUrl);

            LocationResponse location = restTemplate.getForObject(
                    locationUrl,
                    LocationResponse.class);

            System.out.println("Location coordinates: " + location.getLatitude() + "," + location.getLongitude());

            String weatherUrl = weatherServiceUrl + "/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude();
            System.out.println("Requesting weather from: " + weatherUrl);

            Map<String, Object> responseMap = restTemplate.getForObject(
                    weatherUrl,
                    Map.class);

            System.out.println("Raw weather response: " + responseMap);

            Weather weather = new Weather();
            weather.setTemp(parseDouble(responseMap.get("temp")));
            weather.setFeels_like(parseDouble(responseMap.get("feels_like")));
            weather.setTemp_min(parseDouble(responseMap.get("temp_min")));
            weather.setTemp_max(parseDouble(responseMap.get("temp_max")));
            weather.setPressure(parseInt(responseMap.get("pressure")));
            weather.setHumidity(parseInt(responseMap.get("humidity")));
            weather.setSea_level(parseInt(responseMap.get("sea_level")));
            weather.setGrnd_level(parseInt(responseMap.get("grnd_level")));

            System.out.println("Successfully created weather object: " + weather);
            return weather;

        } catch (Exception e) {
            System.err.println("Error in getWeatherByPersonId: " + e.getMessage());
            throw new RuntimeException("Failed to get weather data", e);
        }
    }

    private double parseDouble(Object value) {
        if (value == null) return 0.0;
        if (value instanceof Integer) return ((Integer) value).doubleValue();
        return (Double) value;
    }

    private int parseInt(Object value) {
        if (value == null) return 0;
        return (Integer) value;
    }

}