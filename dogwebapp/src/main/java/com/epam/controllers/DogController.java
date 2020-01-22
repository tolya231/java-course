package com.epam.controllers;

import com.epam.entities.Dog;

import java.time.LocalDate;
import java.util.*;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dog")
public class DogController {

  private List<Dog> dogs;

  public DogController() {
    Dog dog1 = Dog.builder()
        .id(1L)
        .name("Dog 1")
        .height(20)
        .weight(20)
        .birthDate(LocalDate.now())
        .build();
    Dog dog2 = Dog.builder()
        .id(2L)
        .name("Dog 2")
        .height(10)
        .weight(80)
        .birthDate(LocalDate.now().minusDays(1))
        .build();
    Dog dog3 = Dog.builder()
        .id(3L)
        .name("Dog 3")
        .height(2)
        .weight(23)
        .birthDate(LocalDate.now().minusDays(100))
        .build();
    this.dogs = new ArrayList<>(Arrays.asList(dog1, dog2, dog3));
  }

  @GetMapping(value = "/{id}")
  public Dog getDog(@PathVariable Long id) {
    return dogs.stream().filter(dog -> dog.getId().equals(id)).findFirst().get();
  }

  @PostMapping(value = "/{id}")
  public Dog createDog(@PathVariable Long id) {
    if (dogs.stream().noneMatch(dog -> dog.getId().equals(id))) {
      Dog dog = Dog.builder()
          .id(id)
          .name("New dog")
          .height(17)
          .weight(30)
          .birthDate(LocalDate.now())
          .build();
      dogs.add(dog);
      return dog;
    }
    System.out.println("Dog already exists");
    return null;
  }

  @PutMapping(value = "/{id}")
  public void putDog(@PathVariable Long id, @RequestBody Dog dog) {
    Dog foundDog = dogs.stream().filter(d -> dog.getId().equals(id)).findFirst().get();
    dogs.set(dogs.indexOf(foundDog), dog);
  }

  @DeleteMapping(value = "/{id}")
  public void deleteDog(@PathVariable Long id) {
    dogs.removeIf(dog -> dog.getId().equals(id));
  }
}
