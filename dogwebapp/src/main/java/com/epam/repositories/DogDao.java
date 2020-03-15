package com.epam.repositories;

import com.epam.dto.DogDto;

public interface DogDao {

  DogDto create(DogDto dog);

  DogDto update(DogDto dog);

  DogDto get(long id);

  void delete(long id);
}
