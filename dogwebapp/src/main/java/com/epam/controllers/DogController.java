package com.epam.controllers;

import com.epam.dto.DogDto;
import com.epam.services.DogCrudService;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@RequestMapping("/dog")
public class DogController {

  private final DogCrudService dogService;

  public DogController(DogCrudService dogService) {
    this.dogService = dogService;
  }

  @GetMapping(value = "/{id}")
  public DogDto getDog(@PathVariable long id) {
    return dogService.get(id);
  }

  @PostMapping
  public DogDto createDog(@RequestBody @Valid DogDto dog) {
    return dogService.create(dog);
  }

  @PutMapping(value = "/{id}")
  public DogDto putDog(@PathVariable long id, @RequestBody @Valid DogDto dog) {
    return dogService.update(dog.setId(id));
  }

  @DeleteMapping(value = "/{id}")
  public void deleteDog(@PathVariable long id) {
    dogService.delete(id);
  }
}
