package com.epam.repositories.inMemory;

import com.epam.dto.DogDto;
import com.epam.exceptions.ResourceNotFoundException;
import com.epam.repositories.DogDao;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryDogDao implements DogDao {

  private Map<Long, DogDto> dogs;
  private static AtomicLong uniqueLongId = new AtomicLong(3);

  public InMemoryDogDao() {
    DogDto dog1 = new DogDto()
        .setId(1L)
        .setName("Dog 1")
        .setHeight(20)
        .setWeight(20)
        .setBirthDay(LocalDate.of(2020, 1, 22));
    DogDto dog2 = new DogDto()
        .setId(2L)
        .setName("Dog 2")
        .setHeight(10)
        .setWeight(80)
        .setBirthDay(LocalDate.now().minusDays(1));
    DogDto dog3 = new DogDto()
        .setId(3L)
        .setName("Dog 3")
        .setHeight(2)
        .setWeight(23)
        .setBirthDay(LocalDate.now().minusDays(100));
    dogs = Stream.of(dog1, dog2, dog3).collect(
        Collectors.toMap(DogDto::getId, Function.identity(), (v1, v2) -> v1, ConcurrentHashMap::new));
  }

  static {
  }

  @Override
  public DogDto create(DogDto dog) {
    dog.setId(uniqueLongId.incrementAndGet());
    dogs.put(dog.getId(), dog);
    return dog;
  }

  @Override
  public DogDto update(DogDto dog) {
    DogDto foundDog = dogs.get(dog.getId());
    if (foundDog != null) {
      dogs.put(foundDog.getId(), dog);
      return dog;
    }
    throw new ResourceNotFoundException();
  }

  @Override
  public DogDto get(long id) {
    DogDto dog = dogs.get(id);
    if (dog == null) {
      throw new ResourceNotFoundException();
    } else {
      return dog;
    }
  }

  @Override
  public void delete(long id) {
    DogDto dog = dogs.remove(id);
    if (dog == null) {
      throw new ResourceNotFoundException();
    }
  }
}
