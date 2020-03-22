package dao;


import com.epam.dto.DogDto;
import com.epam.jdbc.JdbcConnectionHolder;
import com.epam.repositories.DogDao;
import java.time.LocalDate;
import org.h2.jdbc.JdbcSQLDataException;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.util.Strings;
import utils.DogGenerator;

@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:/app-config.xml", "classpath:/web-config.xml"})
public class JdbcDogDaoTest extends AbstractTestNGSpringContextTests {

  @Autowired
  @Qualifier("jdbcDogDao")
  private DogDao dogDao;

  @Autowired
  private JdbcConnectionHolder jdbcConnectionHolder;

  @BeforeClass
  public void setUp() {
    jdbcConnectionHolder.createOrGetConnection();
  }

  @Test
  public void when_validDog_then_noExceptionThrown() {
    dogDao.create(DogGenerator.dog());
  }

  @Test(expectedExceptions = {JdbcSQLIntegrityConstraintViolationException.class})
  public void when_emptyName_then_exceptionThrown() {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setName(""));
  }

  @Test(expectedExceptions = {JdbcSQLDataException.class})
  public void when_tooLongName_then_exceptionThrown() {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setName(Strings.repeat("D", 101)));
  }

  @Test(expectedExceptions = {JdbcSQLIntegrityConstraintViolationException.class})
  public void when_nullName_then_exceptionThrown() {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setName(null));
  }

  @Test
  public void when_sqlInjectionName_then_noExceptionThrown() {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setName("\"' blah"));
  }

  @Test(expectedExceptions = {JdbcSQLIntegrityConstraintViolationException.class})
  public void when_weight_0_then_exceptionThrown() {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setWeight(0));
  }

  @Test(expectedExceptions = {JdbcSQLIntegrityConstraintViolationException.class})
  public void when_weight_null_then_exceptionThrown() {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setWeight(null));
  }

  @Test(expectedExceptions = {JdbcSQLIntegrityConstraintViolationException.class})
  public void when_height_0_then_exceptionThrown() {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setHeight(0));
  }

  @Test(expectedExceptions = {JdbcSQLIntegrityConstraintViolationException.class})
  public void when_height_null_then_exceptionThrown() {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setHeight(null));
  }

  @Test(expectedExceptions = {JdbcSQLIntegrityConstraintViolationException.class})
  public void when_birthday_notPast_then_exceptionThrown() {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setBirthDay(LocalDate.now().plusDays(1)));
  }

}
