package com.epam.repositories.mockImpl;

import com.epam.entities.Dog;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import com.epam.repositories.DogRepository;

@Component
public class MockDogRepo implements DogRepository {

  List<Dog> dogs;

  public MockDogRepo() {
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

  public List<Dog> findAll() {
    return dogs;
  }

}
