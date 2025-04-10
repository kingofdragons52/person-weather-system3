package com.example.person.controller;

import com.example.person.dto.PersonWeatherDTO;
import com.example.person.model.Person;
import com.example.person.repository.PersonRepository;
import com.example.person.service.WeatherIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private WeatherIntegrationService weatherIntegrationService;

    @GetMapping
    public Iterable<Person> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Person> findById(@PathVariable int id) {
        return repository.findById(id);
    }

    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person) {
        if (person.getId() > 0 && repository.existsById(person.getId())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(repository.findById(person.getId()).get());
        }

        Person savedPerson = repository.save(person);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedPerson);
    }

    @GetMapping("/{id}/weather")
    public ResponseEntity<PersonWeatherDTO> getWeatherForPerson(@PathVariable int id) {
        try {
            return ResponseEntity.ok(weatherIntegrationService.getWeatherByPersonId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(
            @PathVariable int id,
            @RequestBody Person personDetails) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(personDetails.getName());
                    existing.setLocation(personDetails.getLocation());
                    return ResponseEntity.ok(repository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}