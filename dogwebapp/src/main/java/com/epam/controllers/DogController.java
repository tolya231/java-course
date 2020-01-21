package com.epam.controllers;

import com.epam.entities.Dog;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dog")
public class DogController {

  private List<Dog> dogs;

  public DogController() {
    Dog dog1 = Dog.builder()
        .name("Dog 1")
        .height(20)
        .weight(20)
        .birthDate(LocalDate.now())
        .build();
    Dog dog2 = Dog.builder()
        .name("Dog 2")
        .height(10)
        .weight(80)
        .birthDate(LocalDate.now().minusDays(1))
        .build();
    Dog dog3 = Dog.builder()
        .name("Dog 3")
        .height(2)
        .weight(23)
        .birthDate(LocalDate.now().minusDays(100))
        .build();
    this.dogs = new ArrayList<>(Arrays.asList(dog1, dog2, dog3));
  }

  @GetMapping
  public List<Dog> getAllDogs() {
    return dogs;
  }

  @PostMapping
  public Dog createDog() {
    Dog dog = Dog.builder()
        .name("New dog")
        .height(17)
        .weight(30)
        .birthDate(LocalDate.now())
        .build();
    dogs.add(dog);
    return dog;
  }
}
