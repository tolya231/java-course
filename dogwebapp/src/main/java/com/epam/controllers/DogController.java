package com.epam.controllers;

import com.epam.entities.Dog;
import com.epam.repositories.DogDao;
import java.sql.SQLException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
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

  private DogDao dogDao;

  public DogController(@Qualifier("jdbcDogDao") DogDao dogDao) {
    this.dogDao = dogDao;
  }

  @GetMapping(value = "/{id}")
  public Dog getDog(@PathVariable Long id) throws SQLException {
    return dogDao.get(id);
  }

  @PostMapping
  public Dog createDog(@RequestBody @Valid Dog dog) throws SQLException {
    return dogDao.create(dog);
  }

  @PutMapping(value = "/{id}")
  public Dog putDog(@PathVariable Long id, @RequestBody @Valid Dog dog) {
    return dogDao.update(dog, id);
  }

  @DeleteMapping(value = "/{id}")
  public void deleteDog(@PathVariable Long id) {
    dogDao.delete(id);
  }
}
