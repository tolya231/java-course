package com.epam.services;

import com.epam.dto.DogDto;
import com.epam.repositories.DogDao;
import org.springframework.transaction.annotation.Transactional;

public class DogService implements DogCrudService {

  private DogDao dogDao;

  public DogService() {
  }

  public DogService(DogDao dogDao) {
    this.dogDao = dogDao;
  }

  @Transactional
  public DogDto create(DogDto dog) {
    return dogDao.create(dog);
  }

  @Transactional
  public DogDto update(DogDto dog) {
    return dogDao.update(dog);
  }

  @Transactional(readOnly = true)
  public DogDto get(long id) {
    return dogDao.get(id);
  }

  @Transactional
  public void delete(long id) {
    dogDao.delete(id);
  }
}
