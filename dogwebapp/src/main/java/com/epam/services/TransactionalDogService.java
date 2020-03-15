package com.epam.services;

import com.epam.dto.DogDto;

public class TransactionalDogService implements CommonCrudService {

  private final CommonCrudService dogService;

  public TransactionalDogService(CommonCrudService dogService) {
    this.dogService = dogService;
  }

  @Override
  public DogDto create(DogDto dog) {
    return dogService.create(dog);
  }

  @Override
  public DogDto update(DogDto dog) {
    return dogService.update(dog);
  }

  @Override
  public DogDto get(long id) {
    return dogService.get(id);
  }

  @Override
  public void delete(long id) {
    dogService.delete(id);
  }

}
