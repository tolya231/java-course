package com.epam.repositories.jdbcDao;

import com.epam.dto.DogDto;
import com.epam.exceptions.ResourceNotFoundException;
import com.epam.jdbc.JdbcConnectionHolder;
import com.epam.repositories.DogDao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import lombok.SneakyThrows;

public class JdbcDogDao implements DogDao {

  private final JdbcConnectionHolder jdbcConnectionHolder;

  public JdbcDogDao(JdbcConnectionHolder jdbcConnectionHolder) {
    this.jdbcConnectionHolder = jdbcConnectionHolder;
  }

  @SneakyThrows
  @Override
  public DogDto create(DogDto dog) {
    Connection connection = null;
    try {
      connection = jdbcConnectionHolder.getConnection();
      connection.setAutoCommit(false);

      String insert = "INSERT INTO DOG (name, weight, height, birthDay) VALUES (?, ?, ?, ?);";
      PreparedStatement preparedStatement = connection
          .prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, dog.getName());
      preparedStatement.setObject(2, dog.getWeight(), Types.INTEGER);
      preparedStatement.setObject(3, dog.getHeight(), Types.INTEGER);
      preparedStatement.setDate(4, Date.valueOf(dog.getBirthDay()));
      preparedStatement.executeUpdate();
      connection.commit();

      ResultSet rs = preparedStatement.getGeneratedKeys();
      if (rs.next()) {
        return get(rs.getInt(1));
      } else {
        throw new RuntimeException("Dog creation failed");
      }
    } catch (SQLException e) {
      if (connection != null) {
        connection.rollback();
      }
      throw new RuntimeException(e);
    } finally {
      if (connection != null) {
        connection.close();
      }
    }
  }

  @SneakyThrows
  @Override
  public DogDto update(DogDto dog) {
    Connection connection = null;
    try {
      connection = jdbcConnectionHolder.getConnection();
      connection.setAutoCommit(false);

      String update = "UPDATE DOG SET name=?, weight=?, height=?, birthDay=? WHERE id=?;";
      PreparedStatement preparedStatement = connection.prepareStatement(update);
      preparedStatement.setString(1, dog.getName());
      preparedStatement.setObject(2, dog.getWeight(), Types.INTEGER);
      preparedStatement.setObject(3, dog.getHeight(), Types.INTEGER);
      preparedStatement.setDate(4, Date.valueOf(dog.getBirthDay()));
      preparedStatement.setLong(5, dog.getId());
      int count = preparedStatement.executeUpdate();
      connection.commit();

      if (count == 0) {
        throw new ResourceNotFoundException();
      }
      return get(dog.getId());
    } catch (SQLException e) {
      if (connection != null) {
        connection.rollback();
      }
      throw new RuntimeException(e);
    } finally {
      if (connection != null) {
        connection.close();
      }
    }
  }

  @SneakyThrows
  @Override
  public DogDto get(long id) {
    Connection connection = null;
    try {
      connection = jdbcConnectionHolder.getConnection();
      connection.setAutoCommit(false);

      String select = "SELECT * FROM DOG WHERE id=?;";
      PreparedStatement preparedStatement = connection.prepareStatement(select);
      preparedStatement.setLong(1, id);

      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        DogDto dogDto = new DogDto().setId(rs.getLong("id"))
            .setWeight(rs.getInt("weight"))
            .setHeight(rs.getInt("height"))
            .setBirthDay(rs.getDate("birthDay").toLocalDate())
            .setName(rs.getString("name"));
        connection.commit();
        return dogDto;
      } else {
        throw new ResourceNotFoundException();
      }
    } catch (SQLException e) {
      if (connection != null) {
        connection.rollback();
      }
      throw new RuntimeException(e);
    } finally {
      if (connection != null) {
        connection.close();
      }
    }
  }

  @SneakyThrows
  @Override
  public void delete(long id) {
    Connection connection = null;
    try {
      connection = jdbcConnectionHolder.getConnection();
      connection.setAutoCommit(false);

      String delete = "DELETE FROM DOG WHERE id=?;";
      PreparedStatement preparedStatement = connection.prepareStatement(delete);
      preparedStatement.setLong(1, id);
      int count = preparedStatement.executeUpdate();
      connection.commit();
      if (count == 0) {
        throw new ResourceNotFoundException();
      }
    } catch (SQLException e) {
      if (connection != null) {
        connection.rollback();
      }
      throw new RuntimeException(e);
    } finally {
      if (connection != null) {
        connection.close();
      }
    }
  }
}
