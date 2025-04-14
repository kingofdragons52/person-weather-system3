package com.example.location.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.example.location.model.Location;
import com.example.location.model.Weather;
import com.example.location.repository.LocationRepository;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationRepository repository;
    private final RestTemplate restTemplate;

    @Autowired
    public LocationController(LocationRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public List<Location> getAllLocations() {
        return (List<Location>) repository.findAll();
    }

    @GetMapping(params = "name")
    public Location getLocationByName(@RequestParam String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Location not found"));
    }

    @PostMapping
    public Location addLocation(@RequestBody Location location) {
        return repository.save(location);
    }

    @PutMapping(params = "name")
    public Location updateLocation(@RequestParam String name, @RequestBody Location updatedLocation) {
        Location existing = repository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        existing.setLatitude(updatedLocation.getLatitude());
        existing.setLongitude(updatedLocation.getLongitude());

        return repository.save(existing);
    }

    @DeleteMapping(params = "name")
    @Transactional  // Добавьте эту аннотацию
    public ResponseEntity<String> deleteLocation(@RequestParam String name) {
        if (!repository.existsByName(name)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Location '" + name + "' not found");
        }

        repository.deleteByName(name);
        return ResponseEntity.ok("Location '" + name + "' deleted");
    }

    @GetMapping("/weather")
    public Weather getWeatherForLocation(@RequestParam String name) {
        System.out.println("Запрос погоды для локации: " + name); // Лог 1

        Location location = repository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Location not found"));
        System.out.println("Найдена локация: " + location); // Лог 2

        String url = String.format(
                "http://weather-info-service/weather?lat=%s&lon=%s",
                location.getLatitude(),
                location.getLongitude()
        );
        System.out.println("URL запроса: " + url); // Лог 3

        Weather weather = restTemplate.getForObject(url, Weather.class);
        System.out.println("Ответ от Weather сервиса: " + weather); // Лог 4

        return weather;
    }
}