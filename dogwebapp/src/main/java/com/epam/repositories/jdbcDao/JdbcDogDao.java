package com.epam.repositories.jdbcDao;

import com.epam.dto.DogDto;
import com.epam.exceptions.ResourceNotFoundException;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcDogDao {

  private final DataSource dataSource;

  public JdbcDogDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public DogDto create(DogDto dog) {
    try (Connection connection = dataSource.getConnection()) {
      String insert = "INSERT INTO DOG (name, weight, height, birthDay) VALUES (?, ?, ?, ?);";
      PreparedStatement preparedStatement = connection
          .prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, dog.getName());
      preparedStatement.setObject(2, dog.getWeight(), Types.INTEGER);
      preparedStatement.setObject(3, dog.getHeight(), Types.INTEGER);
      preparedStatement.setDate(4, Date.valueOf(dog.getBirthDay()));
      preparedStatement.executeUpdate();

      ResultSet rs = preparedStatement.getGeneratedKeys();
      if (rs.next()) {
        return get(rs.getInt(1));
      } else {
        throw new RuntimeException("Dof creation failed");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public DogDto update(DogDto dog) {
    try (Connection connection = dataSource.getConnection()) {
      String update = "UPDATE DOG SET name=?, weight=?, height=?, birthDay=? WHERE id=?;";
      PreparedStatement preparedStatement = connection.prepareStatement(update);
      preparedStatement.setString(1, dog.getName());
      preparedStatement.setObject(2, dog.getWeight(), Types.INTEGER);
      preparedStatement.setObject(3, dog.getHeight(), Types.INTEGER);
      preparedStatement.setDate(4, Date.valueOf(dog.getBirthDay()));
      preparedStatement.setLong(5, dog.getId());
      int count = preparedStatement.executeUpdate();

      if (count == 0) {
        throw new ResourceNotFoundException();
      }
      return get(dog.getId());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public DogDto get(long id) {
    try (Connection connection = dataSource.getConnection()) {
      String select = "SELECT * FROM DOG WHERE id=?;";
      PreparedStatement preparedStatement = connection.prepareStatement(select);
      preparedStatement.setLong(1, id);

      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        return new DogDto().setId(rs.getLong("id"))
            .setWeight(rs.getInt("weight"))
            .setHeight(rs.getInt("height"))
            .setBirthDay(rs.getDate("birthDay").toLocalDate())
            .setName(rs.getString("name"));
      } else {
        throw new ResourceNotFoundException();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void delete(long id) {
    try (Connection connection = dataSource.getConnection()) {
      String delete = "DELETE FROM DOG WHERE id=?;";
      PreparedStatement preparedStatement = connection.prepareStatement(delete);
      preparedStatement.setLong(1, id);
      int count = preparedStatement.executeUpdate();
      if (count == 0) {
        throw new ResourceNotFoundException();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
