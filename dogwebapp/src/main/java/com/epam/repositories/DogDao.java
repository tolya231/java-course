package com.epam.repositories;

import com.epam.entities.Dog;
import java.sql.SQLException;

public interface DogDao {

  public Dog create(Dog dog) throws SQLException;

  public Dog update(Dog dog, Long id);

  public Dog get(Long id) throws SQLException;

  public void delete(Long id);

}
