package com.epam.repositories.jdbcDao;

import com.epam.entities.Dog;
import com.epam.repositories.DogDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

@Repository("jdbcDogDao")
public class JdbcDogDao implements DogDao {

  private DataSource dataSource;
  private static final String TABLE_NAME = "dog";

  public JdbcDogDao(DataSource dataSource) {
    this.dataSource = dataSource;
    try {
      String createSchemaSql = "create table " + TABLE_NAME
          + " ("
          + "  id       identity not null primary key,"
          + "  name     varchar(100),"
          + "  weight   int,"
          + "  height   int,"
          + "  birthDay date,"
          + "  check(weight >= 1),"
          + "  check(height >= 1)"
          + ");";
      Statement statement = dataSource.getConnection().createStatement();
      statement.execute(createSchemaSql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Dog create(Dog dog) throws SQLException {
    Statement statement = null;
    try {
      statement = dataSource.getConnection().createStatement();
      statement
          .execute("insert into " + TABLE_NAME + " (name, weight, height, birthDay)"
              + " values ('" + dog.getName() + "', '" + dog.getWeight() + "', '"
              + dog.getHeight()+ "', '" + dog.getBirthDay() + "');");
//      if (rs.next()) {
//        return new Dog().setId(rs.getLong("id"))
//            .setWeight(rs.getInt("weight"))
//            .setHeight(rs.getInt("height"))
//            .setBirthDay(rs.getDate("birthDay").toLocalDate())
//            .setName(rs.getString("name"));
//      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (statement != null) {
        statement.close();
      }
    }
    return null;
  }

  @Override
  public Dog update(Dog dog, Long id) {
    return null;
  }

  @Override
  public Dog get(Long id) throws SQLException {
    Statement statement = null;
    try {
      statement = dataSource.getConnection().createStatement();
      ResultSet rs = statement
          .executeQuery("select * from " + TABLE_NAME + " where id=" + id + ";");
      if (rs.next()) {
        return new Dog().setId(rs.getLong("id"))
            .setWeight(rs.getInt("weight"))
            .setHeight(rs.getInt("height"))
            .setBirthDay(rs.getDate("birthDay").toLocalDate())
            .setName(rs.getString("name"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (statement != null) {
        statement.close();
      }
    }
    return null;
  }

  @Override
  public void delete(Long id) {

  }
}
