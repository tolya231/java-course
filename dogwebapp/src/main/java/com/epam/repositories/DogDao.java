package com.epam.repositories;

import com.epam.aop.LogMe;
import com.epam.dto.DogDto;

public interface DogDao {

  DogDto create(DogDto dog);

  DogDto update(DogDto dog);

  @LogMe
  DogDto get(long id);

  void delete(long id);
}
