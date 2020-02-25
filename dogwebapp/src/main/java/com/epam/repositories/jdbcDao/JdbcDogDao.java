package com.epam.repositories.jdbcDao;

import com.epam.dto.DogDto;
import com.epam.exceptions.ResourceNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

public class JdbcDogDao {

  private final DataSource dataSource;
  private static final String TABLE_NAME = "DOG";

  public JdbcDogDao(DataSource dataSource) throws SQLException {
    this.dataSource = dataSource;
    try (Connection connection = dataSource.getConnection()) {
      String createSchemaSql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " "
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
      Statement statement = connection.createStatement();
      String insert = String.format(
          "insert into %s (name, weight, height, birthDay) values ('%s', '%s', '%s', '%s');",
          TABLE_NAME, dog.getName(), dog.getWeight(), dog.getHeight(), dog.getBirthDay());
      insert = insert.replaceAll("'null'", "null");
      statement.executeUpdate(insert,
          Statement.RETURN_GENERATED_KEYS);

      ResultSet rs = statement.getGeneratedKeys();
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
      Statement statement = connection.createStatement();
      String update = String.format(
          "update %s set name='%s', weight='%s', height='%s', birthDay='%s' where id=%s;",
          TABLE_NAME, dog.getName(), dog.getWeight(), dog.getHeight(), dog.getBirthDay(),
          dog.getId());
      update = update.replaceAll("'null'", "null");
      int count = statement.executeUpdate(update);
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
      Statement statement = connection.createStatement();
      ResultSet rs = statement
          .executeQuery(String.format("select * from %s where id=%s;", TABLE_NAME, id));
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
      Statement statement = connection.createStatement();
      int count = statement
          .executeUpdate(String.format("delete from %s where id=%s;", TABLE_NAME, id));
      if (count == 0) {
        throw new ResourceNotFoundException();
      }
    } catch (SQLException e) {
      throw new SQLException("Dog get failed");
    }
  }
}
