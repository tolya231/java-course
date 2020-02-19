package com.epam.repositories;

import com.epam.entities.Dog;

public interface DogDao {

  public Dog create(Dog dog);

  public Dog update(Dog dog, Long id);

  public Dog get(Long id);

  public void delete(Long id);

}
