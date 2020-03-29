package com.epam.repositories.jdbcDao;

import com.epam.dto.DogDto;
import com.epam.exceptions.ResourceNotFoundException;
import com.epam.repositories.DogDao;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcDogDao implements DogDao {

  private final JdbcTemplate jdbcTemplate;

  public JdbcDogDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  @SneakyThrows
  public DogDto create(DogDto dog) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    String insert = "INSERT INTO DOG (name, weight, height, birthDay) VALUES (?, ?, ?, ?);";
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(insert, new String[]{"id"});
      ps.setString(1, dog.getName());
      ps.setObject(2, dog.getWeight(), Types.INTEGER);
      ps.setObject(3, dog.getHeight(), Types.INTEGER);
      ps.setDate(4, Date.valueOf(dog.getBirthDay()));
      return ps;
    }, keyHolder);

    Number key = keyHolder.getKey();
    if (key != null) {
      return get(key.longValue());
    } else {
      throw new RuntimeException("Dog creation failed");
    }
  }

  @Override
  @SneakyThrows
  public DogDto update(DogDto dog) {
    String update = "UPDATE DOG SET name=?, weight=?, height=?, birthDay=? WHERE id=?;";
    Object[] args = {dog.getName(), dog.getWeight(), dog.getHeight(),
        Date.valueOf(dog.getBirthDay()), dog.getId()};
    int count = jdbcTemplate.update(update, args);
    if (count == 0) {
      throw new ResourceNotFoundException();
    }
    return get(dog.getId());
  }

  @Override
  @SneakyThrows
  public DogDto get(long id) {
    String select = "SELECT * FROM DOG WHERE id=?;";
    List<DogDto> dogDtos = jdbcTemplate
        .query(select, new Object[]{id}, new BeanPropertyRowMapper<>(DogDto.class));

    if (dogDtos.size() > 0) {
      return dogDtos.get(0);
    } else {
      throw new ResourceNotFoundException();
    }
  }

  @Override
  @SneakyThrows
  public void delete(long id) {
    String delete = "DELETE FROM DOG WHERE id=?;";
    int count = jdbcTemplate.update(delete, id);
    if (count == 0) {
      throw new ResourceNotFoundException();
    }
  }

  @SneakyThrows
  private DogDto mapDogRow(ResultSet rs, int rowNum) {
    return new DogDto().setId(rs.getLong("id"))
        .setWeight(rs.getInt("weight"))
        .setHeight(rs.getInt("height"))
        .setBirthDay(rs.getDate("birthDay").toLocalDate())
        .setName(rs.getString("name"));
  }
}
