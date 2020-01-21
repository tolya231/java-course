package com.epam.controllers;

import com.epam.entities.Dog;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.epam.repositories.DogRepository;

@RestController
@RequestMapping("/dog")
public class DogController {

  private final DogRepository dogRepository;

  public DogController(DogRepository dogRepository) {
    this.dogRepository = dogRepository;
  }

  @GetMapping
  public List<Dog> getAllDogs() {

    List<Dog> dogs = dogRepository.findAll();
    System.out.println(dogs);
    return dogs;
  }
}
