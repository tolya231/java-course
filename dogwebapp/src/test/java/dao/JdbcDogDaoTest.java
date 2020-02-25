package dao;


import com.epam.dto.DogDto;
import com.epam.repositories.jdbcDao.JdbcDogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.util.Strings;
import utils.DogGenerator;

import java.sql.SQLException;
import java.time.LocalDate;

@WebAppConfiguration
@ContextConfiguration(locations = {"file:**/app-config.xml", "file:**/web-config.xml"})
public class JdbcDogDaoTest extends AbstractTestNGSpringContextTests {

  @Autowired
  JdbcDogDao dogDao;

  @Test
  public void when_validDog_then_noExceptions() throws SQLException {
    dogDao.create(DogGenerator.dog());
  }

  @Test(expectedExceptions = {SQLException.class})
  public void when_emptyName_then_exception() throws SQLException {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setName(""));
  }

  @Test(expectedExceptions = {SQLException.class})
  public void when_tooLongName_then_exception() throws SQLException {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setName(Strings.repeat("D", 101)));
  }

  @Test(expectedExceptions = {SQLException.class})
  public void when_nullName_then_exception() throws SQLException {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setName(null));
  }

  @Test
  @Ignore //until sql injection fixed
  public void when_sqlInjectionName_then_no_exception() throws SQLException {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setName("\"' blah"));
  }

  @Test(expectedExceptions = {SQLException.class})
  public void when_weight_0_then_exception() throws SQLException {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setWeight(0));
  }

  @Test(expectedExceptions = {SQLException.class})
  public void when_weight_null_then_exception() throws SQLException {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setWeight(null));
  }

  @Test(expectedExceptions = {SQLException.class})
  public void when_height_0_then_exception() throws SQLException {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setHeight(0));
  }

  @Test(expectedExceptions = {SQLException.class})
  public void when_height_null_then_exception() throws SQLException {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setHeight(null));
  }

  @Test(expectedExceptions = {SQLException.class})
  public void when_birthday_notPast_then_exception() throws SQLException {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setBirthDay(LocalDate.now().plusDays(1)));
  }

}
