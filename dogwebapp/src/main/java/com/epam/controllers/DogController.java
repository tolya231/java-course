package com.epam.controllers;

import com.epam.entities.Dog;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dog")
public class DogController {

  private Map<Long, Dog> dogs;
  private static AtomicLong uniqueLongId = new AtomicLong(3);

  public DogController() {
    Dog dog1 = new Dog()
        .setId(1L)
        .setName("Dog 1")
        .setHeight(20)
        .setWeight(20)
        .setBirthDay(LocalDate.of(2020, 1, 22));
    Dog dog2 = new Dog()
        .setId(2L)
        .setName("Dog 2")
        .setHeight(10)
        .setWeight(80)
        .setBirthDay(LocalDate.now().minusDays(1));
    Dog dog3 = new Dog()
        .setId(3L)
        .setName("Dog 3")
        .setHeight(2)
        .setWeight(23)
        .setBirthDay(LocalDate.now().minusDays(100));
    dogs = Stream.of(dog1, dog2, dog3).collect(
        Collectors.toMap(Dog::getId, Function.identity(), (v1, v2) -> v1, ConcurrentHashMap::new));
  }

  @GetMapping(value = "/{id}")
  public Dog getDog(@PathVariable Long id) {
    return dogs.get(id);
  }

  @PostMapping
  public Dog createDog(@RequestBody @Valid Dog dog) {
    dog.setId(uniqueLongId.incrementAndGet());
    dogs.put(dog.getId(), dog);
    return dog;
  }

  @PutMapping(value = "/{id}")
  public Dog putDog(@PathVariable Long id, @RequestBody @Valid Dog dog) {
    Dog foundDog = dogs.get(id);
    if (foundDog != null) {
      dogs.put(foundDog.getId(), dog);
      return dog;
    }
    return null;
  }

  @DeleteMapping(value = "/{id}")
  public void deleteDog(@PathVariable Long id) {
    dogs.remove(id);
  }
}
