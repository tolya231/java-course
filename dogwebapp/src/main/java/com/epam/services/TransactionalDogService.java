package com.epam.services;

import static com.epam.jdbc.JdbcConnectionHolder.closeConnection;
import static com.epam.jdbc.JdbcConnectionHolder.rollbackConnection;

import com.epam.dto.DogDto;
import com.epam.jdbc.JdbcConnectionHolder;
import java.sql.Connection;

public class TransactionalDogService implements CommonCrudService {

  private final CommonCrudService dogService;
  private final JdbcConnectionHolder jdbcConnectionHolder;

  public TransactionalDogService(CommonCrudService dogService,
      JdbcConnectionHolder jdbcConnectionHolder) {
    this.dogService = dogService;
    this.jdbcConnectionHolder = jdbcConnectionHolder;
  }

  @Override
  public DogDto create(DogDto dog) {
    Connection connection = null;
    try {
      connection = jdbcConnectionHolder.createConnection();
      connection.setAutoCommit(false);

      return dogService.create(dog);
    } catch (Exception e) {
      rollbackConnection(connection);
      throw new RuntimeException(String.format("Create dog %s failed", dog), e);
    } finally {
      closeConnection(connection);
    }
  }

  @Override
  public DogDto update(DogDto dog) {
    Connection connection = null;
    try {
      connection = jdbcConnectionHolder.createConnection();
      connection.setAutoCommit(false);

      return dogService.update(dog);
    } catch (Exception e) {
      rollbackConnection(connection);
      throw new RuntimeException(String.format("Update dog %s failed", dog), e);
    } finally {
      closeConnection(connection);
    }
  }

  @Override
  public DogDto get(long id) {
    Connection connection = null;
    try {
      connection = jdbcConnectionHolder.createConnection();
      connection.setAutoCommit(false);

      return dogService.get(id);
    } catch (Exception e) {
      rollbackConnection(connection);
      throw new RuntimeException(String.format("Get dog with id=%s failed", id), e);
    } finally {
      closeConnection(connection);
    }
  }

  @Override
  public void delete(long id) {
    Connection connection = null;
    try {
      connection = jdbcConnectionHolder.createConnection();
      connection.setAutoCommit(false);

      dogService.delete(id);
    } catch (Exception e) {
      rollbackConnection(connection);
      throw new RuntimeException(String.format("Delete dog with id=%s failed", id), e);
    } finally {
      closeConnection(connection);
    }
  }

}
