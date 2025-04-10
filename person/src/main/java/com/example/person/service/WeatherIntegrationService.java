package com.example.person.service;

import com.example.person.dto.PersonWeatherDTO;
import com.example.person.dto.LocationResponse;
import com.example.person.dto.WeatherResponse;
import com.example.person.model.Person;
import com.example.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    public PersonWeatherDTO getWeatherByPersonId(int personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        // Запрос к location-сервису
        LocationResponse location = restTemplate.getForObject(
                locationServiceUrl + "/location?name={name}",
                LocationResponse.class,
                person.getLocation()
        );

        // Запрос к weather-сервису
        WeatherResponse weather = restTemplate.getForObject(
                weatherServiceUrl + "/weather?lat={lat}&lon={lon}",
                WeatherResponse.class,
                location.getLatitude(),
                location.getLongitude()
        );

        return new PersonWeatherDTO(
                person.getId(),
                person.getName(),
                person.getLocation(),
                weather.getDescription(),
                weather.getTemperature()
        );
    }
}