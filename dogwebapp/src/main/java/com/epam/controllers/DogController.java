package com.epam.controllers;

import com.epam.entities.Dog;

import java.time.LocalDate;
import java.util.*;

import javax.validation.Valid;
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
        .birthDay(LocalDate.of(2020, 1, 22))
        .build();
    Dog dog2 = Dog.builder()
        .id(2L)
        .name("Dog 2")
        .height(10)
        .weight(80)
        .birthDay(LocalDate.now().minusDays(1))
        .build();
    Dog dog3 = Dog.builder()
        .id(3L)
        .name("Dog 3")
        .height(2)
        .weight(23)
        .birthDay(LocalDate.now().minusDays(100))
        .build();
    this.dogs = new ArrayList<>(Arrays.asList(dog1, dog2, dog3));
  }

  @GetMapping(value = "/{id}")
  public Dog getDog(@PathVariable Long id) {
    Optional<Dog> dogOptional = dogs.stream().filter(dog -> dog.getId().equals(id)).findFirst();
    return dogOptional.orElse(null);
  }

  @PostMapping
  public Dog createDog(@RequestBody @Valid Dog dog) {
    dog.setId(new Random().nextLong());
    dogs.add(dog);
    return dog;
  }

  @PutMapping(value = "/{id}")
  public Dog putDog(@PathVariable Long id, @RequestBody @Valid Dog dog) {
    Dog foundDog = dogs.stream().filter(d -> dog.getId().equals(id)).findFirst().get();
    dogs.set(dogs.indexOf(foundDog), dog);
    return dog;
  }

  @DeleteMapping(value = "/{id}")
  public void deleteDog(@PathVariable Long id) {
    dogs.removeIf(dog -> dog.getId().equals(id));
  }
}
