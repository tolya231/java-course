package com.epam.services;

import com.epam.dto.DogDto;

public interface DogCrudService {

  DogDto create(DogDto dog);

  DogDto update(DogDto dog);

  DogDto get(long id);

  void delete(long id);

}
