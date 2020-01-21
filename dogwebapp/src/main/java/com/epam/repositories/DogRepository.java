package com.epam.repositories;

import com.epam.entities.Dog;
import java.util.List;

public interface DogRepository {

  List<Dog> findAll();

}
