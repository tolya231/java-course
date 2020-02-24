package com.epam.controllers;

import com.epam.dto.DogDto;
import com.epam.repositories.jdbcDao.JdbcDogDao;
import java.sql.SQLException;
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

  private final JdbcDogDao dogDao;

  public DogController(JdbcDogDao dogDao) {
    this.dogDao = dogDao;
  }

  @GetMapping(value = "/{id}")
  public DogDto getDog(@PathVariable long id) throws SQLException {
    return dogDao.get(id);
  }

  @PostMapping
  public DogDto createDog(@RequestBody @Valid DogDto dog) throws SQLException {
    return dogDao.create(dog);
  }

  @PutMapping(value = "/{id}")
  public DogDto putDog(@PathVariable long id, @RequestBody @Valid DogDto dog) throws SQLException {
    return dogDao.update(dog.setId(id));
  }

  @DeleteMapping(value = "/{id}")
  public void deleteDog(@PathVariable long id) throws SQLException {
    dogDao.delete(id);
  }
}
