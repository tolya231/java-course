package com.epam.services;

import com.epam.dto.DogDto;
import com.epam.repositories.DogDao;

public class DogService implements CommonCrudService {

  private final DogDao dogDao;

  public DogService(DogDao dogDao) {
    this.dogDao = dogDao;
  }

  public DogDto create(DogDto dog) {
    return dogDao.create(dog);
  }

  public DogDto update(DogDto dog) {
    return dogDao.update(dog);
  }

  public DogDto get(long id) {
    return dogDao.get(id);
  }

  public void delete(long id) {
    dogDao.delete(id);
  }
}
