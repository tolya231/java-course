package com.epam.services;

import com.epam.dto.DogDto;
import com.epam.jdbc.JdbcConnectionHolder;

public class TransactionalDogService implements DogCrudService {

  private final DogCrudService dogService;
  private final JdbcConnectionHolder jdbcConnectionHolder;

  public TransactionalDogService(DogCrudService dogService,
      JdbcConnectionHolder jdbcConnectionHolder) {
    this.dogService = dogService;
    this.jdbcConnectionHolder = jdbcConnectionHolder;
  }

  @Override
  public DogDto create(DogDto dog) {
    try {
      jdbcConnectionHolder.createOrGetConnection();
      jdbcConnectionHolder.startTransaction();
      DogDto dogDto = dogService.create(dog);
      jdbcConnectionHolder.commitTransaction();
      return dogDto;
    } catch (Exception e) {
      jdbcConnectionHolder.rollbackConnection();
      throw new RuntimeException(String.format("Create dog %s failed", dog), e);
    } finally {
      jdbcConnectionHolder.closeConnection();
    }
  }

  @Override
  public DogDto update(DogDto dog) {
    try {
      jdbcConnectionHolder.createOrGetConnection();
      jdbcConnectionHolder.startTransaction();
      DogDto dogDto = dogService.update(dog);
      jdbcConnectionHolder.commitTransaction();
      return dogDto;
    } catch (Exception e) {
      jdbcConnectionHolder.rollbackConnection();
      throw new RuntimeException(String.format("Update dog %s failed", dog), e);
    } finally {
      jdbcConnectionHolder.closeConnection();
    }
  }

  @Override
  public DogDto get(long id) {
    try {
      jdbcConnectionHolder.createOrGetConnection();
      jdbcConnectionHolder.startTransaction();
      DogDto dogDto = dogService.get(id);
      jdbcConnectionHolder.commitTransaction();
      return dogDto;
    } catch (Exception e) {
      jdbcConnectionHolder.rollbackConnection();
      throw new RuntimeException(String.format("Get dog with id=%s failed", id), e);
    } finally {
      jdbcConnectionHolder.closeConnection();
    }
  }

  @Override
  public void delete(long id) {
    try {
      jdbcConnectionHolder.createOrGetConnection();
      jdbcConnectionHolder.startTransaction();
      dogService.delete(id);
      jdbcConnectionHolder.commitTransaction();
    } catch (Exception e) {
      jdbcConnectionHolder.rollbackConnection();
      throw new RuntimeException(String.format("Delete dog with id=%s failed", id), e);
    } finally {
      jdbcConnectionHolder.closeConnection();
    }
  }

}
