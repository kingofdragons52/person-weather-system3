package com.example.person.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue
    private int id;

    @NonNull
    private String name;

    @NonNull
    private String location;

    // Конструктор без id (для создания новых объектов)
    public Person(@NonNull String name, @NonNull String location) {
        this.name = name;
        this.location = location;
    }
}