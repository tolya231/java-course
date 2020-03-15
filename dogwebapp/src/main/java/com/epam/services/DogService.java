package com.epam.services;

import static com.epam.jdbc.JdbcConnectionHolder.closeConnection;
import static com.epam.jdbc.JdbcConnectionHolder.rollbackConnection;

import com.epam.dto.DogDto;
import com.epam.jdbc.JdbcConnectionHolder;
import com.epam.repositories.DogDao;
import java.sql.Connection;

public class DogService implements CommonCrudService {

  private final DogDao dogDao;
  private final JdbcConnectionHolder jdbcConnectionHolder;


  public DogService(DogDao dogDao, JdbcConnectionHolder jdbcConnectionHolder) {
    this.dogDao = dogDao;
    this.jdbcConnectionHolder = jdbcConnectionHolder;
  }

  public DogDto create(DogDto dog) {
    Connection connection = null;
    try {
      connection = jdbcConnectionHolder.createConnection();
      connection.setAutoCommit(false);

      return dogDao.create(dog);
    } catch (Exception e) {
      rollbackConnection(connection);
      throw new RuntimeException(String.format("Create dog %s failed", dog), e);
    } finally {
      closeConnection(connection);
    }
  }

  public DogDto update(DogDto dog) {
    Connection connection = null;
    try {
      connection = jdbcConnectionHolder.createConnection();
      connection.setAutoCommit(false);

      return dogDao.update(dog);
    } catch (Exception e) {
      rollbackConnection(connection);
      throw new RuntimeException(String.format("Update dog %s failed", dog), e);
    } finally {
      closeConnection(connection);
    }
  }

  public DogDto get(long id) {
    Connection connection = null;
    try {
      connection = jdbcConnectionHolder.createConnection();
      connection.setAutoCommit(false);

      return dogDao.get(id);
    } catch (Exception e) {
      rollbackConnection(connection);
      throw new RuntimeException(String.format("Get dog with id=%s failed", id), e);
    } finally {
      closeConnection(connection);
    }
  }

  public void delete(long id) {
    Connection connection = null;
    try {
      connection = jdbcConnectionHolder.createConnection();
      connection.setAutoCommit(false);

      dogDao.delete(id);
    } catch (Exception e) {
      rollbackConnection(connection);
      throw new RuntimeException(String.format("Delete dog with id=%s failed", id), e);
    } finally {
      closeConnection(connection);
    }
  }
}
