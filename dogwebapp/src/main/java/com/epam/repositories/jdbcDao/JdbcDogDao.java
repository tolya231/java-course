package com.epam.repositories.jdbcDao;

import com.epam.dto.DogDto;
import com.epam.exceptions.ResourceNotFoundException;

import java.sql.*;
import javax.sql.DataSource;

public class JdbcDogDao {

  private final DataSource dataSource;

  public JdbcDogDao(DataSource dataSource) throws SQLException {
    this.dataSource = dataSource;
    try (Connection connection = dataSource.getConnection()) {
      String createSchemaSql = "CREATE TABLE IF NOT EXISTS DOG "
          + "("
          + "    id IDENTITY NOT NULL PRIMARY KEY, "
          + "    name     VARCHAR(100) NOT NULL CHECK (length(name) >= 1), "
          + "    weight   INT NOT NULL CHECK (weight >= 1), "
          + "    height   INT NOT NULL CHECK (height >= 1), "
          + "    birthDay DATE CHECK (birthDay < CURRENT_DATE)"
          + "); ";
      Statement statement = connection.createStatement();
      statement.execute(createSchemaSql);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Database creation failed");
    }
  }

  public DogDto create(DogDto dog) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      String insert = "insert into DOG (name, weight, height, birthDay) values (?, ?, ?, ?);";
      PreparedStatement preparedStatement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, dog.getName());
      preparedStatement.setObject(2, dog.getWeight(), Types.INTEGER);
      preparedStatement.setObject(3, dog.getHeight(), Types.INTEGER);
      preparedStatement.setDate(4, Date.valueOf(dog.getBirthDay()));
      preparedStatement.executeUpdate();

      ResultSet rs = preparedStatement.getGeneratedKeys();
      if (rs.next()) {
        return get(rs.getInt(1));
      } else {
        throw new SQLException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Dog creation failed");
    }
  }

  public DogDto update(DogDto dog) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      String update = "update DOG set name=?, weight=?, height=?, birthDay=? where id=?;";
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
      e.printStackTrace();
      throw new SQLException("Dog update failed");
    }
  }

  public DogDto get(long id) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      String select = "select * from DOG where id=?;";
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
      e.printStackTrace();
      throw new SQLException("Dog get failed");
    }
  }

  public void delete(long id) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      String delete = "delete from DOG where id=?;";
      PreparedStatement preparedStatement = connection.prepareStatement(delete);
      preparedStatement.setLong(1, id);
      int count = preparedStatement.executeUpdate();
      if (count == 0) {
        throw new ResourceNotFoundException();
      }
    } catch (SQLException e) {
      throw new SQLException("Dog get failed");
    }
  }
}
