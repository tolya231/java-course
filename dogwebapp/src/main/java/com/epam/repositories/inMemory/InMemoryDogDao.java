package com.epam.repositories.inMemory;

import com.epam.entities.Dog;
import com.epam.exceptions.ResourceNotFoundException;
import com.epam.repositories.DogDao;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;

@Repository("inMemoryDogDao")
public class InMemoryDogDao implements DogDao {

  private Map<Long, Dog> dogs;
  private static AtomicLong uniqueLongId = new AtomicLong(3);

  public InMemoryDogDao() {
    Dog dog1 = new Dog()
        .setId(1L)
        .setName("Dog 1")
        .setHeight(20)
        .setWeight(20)
        .setBirthDay(LocalDate.of(2020, 1, 22));
    Dog dog2 = new Dog()
        .setId(2L)
        .setName("Dog 2")
        .setHeight(10)
        .setWeight(80)
        .setBirthDay(LocalDate.now().minusDays(1));
    Dog dog3 = new Dog()
        .setId(3L)
        .setName("Dog 3")
        .setHeight(2)
        .setWeight(23)
        .setBirthDay(LocalDate.now().minusDays(100));
    dogs = Stream.of(dog1, dog2, dog3).collect(
        Collectors.toMap(Dog::getId, Function.identity(), (v1, v2) -> v1, ConcurrentHashMap::new));
  }

  static {
  }

  @Override
  public Dog create(Dog dog) {
    dog.setId(uniqueLongId.incrementAndGet());
    dogs.put(dog.getId(), dog);
    return dog;
  }

  @Override
  public Dog update(Dog dog, Long id) {
    Dog foundDog = dogs.get(id);
    if (foundDog != null) {
      dogs.put(foundDog.getId(), dog);
      return dog;
    }
    throw new ResourceNotFoundException();
  }

  @Override
  public Dog get(Long id) {
    Dog dog = dogs.get(id);
    if (dog == null) {
      throw new ResourceNotFoundException();
    } else {
      return dog;
    }
  }

  @Override
  public void delete(Long id) {
    Dog dog = dogs.remove(id);
    if (dog == null) {
      throw new ResourceNotFoundException();
    }
  }
}
