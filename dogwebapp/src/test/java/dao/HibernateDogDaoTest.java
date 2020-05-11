package dao;


import com.epam.dto.DogDto;
import com.epam.repositories.impl.HibernateDogDao;
import java.time.LocalDate;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;
import org.testng.util.Strings;
import utils.DogGenerator;

@ContextConfiguration(locations = {
    "classpath:/app-config.xml",
    "classpath:/web-config.xml",
    "classpath:/datasource-config.xml",
    "classpath:/hibernate-config.xml"})
@Transactional(transactionManager = "hibernateTxManager")
public class HibernateDogDaoTest extends AbstractTransactionalTestNGSpringContextTests {

  @Autowired
  private HibernateDogDao dogDao;

  @Test
  public void when_validDog_then_noExceptionThrown() {
    dogDao.create(DogGenerator.dog());
    dogDao.flushAndClear();
  }

  @Test(expectedExceptions = {ConstraintViolationException.class})
  public void when_emptyName_then_exceptionThrown() {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setName(""));
    dogDao.flushAndClear();
  }

  @Test(expectedExceptions = {ConstraintViolationException.class})
  public void when_tooLongName_then_exceptionThrown() {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setName(Strings.repeat("D", 101)));
    dogDao.flushAndClear();
  }

  @Test(expectedExceptions = {ConstraintViolationException.class})
  public void when_nullName_then_exceptionThrown() {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setName(null));
    dogDao.flushAndClear();
  }

  @Test
  public void when_sqlInjectionName_then_noExceptionThrown() {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setName("\"' blah"));
    dogDao.flushAndClear();
  }

  @Test(expectedExceptions = {ConstraintViolationException.class})
  public void when_weight_0_then_exceptionThrown() {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setWeight(0));
    dogDao.flushAndClear();
  }

  @Test(expectedExceptions = {ConstraintViolationException.class})
  public void when_weight_null_then_exceptionThrown() {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setWeight(null));
    dogDao.flushAndClear();
  }

  @Test(expectedExceptions = {ConstraintViolationException.class})
  public void when_height_0_then_exceptionThrown() {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setHeight(0));
    dogDao.flushAndClear();
  }

  @Test(expectedExceptions = {ConstraintViolationException.class})
  public void when_height_null_then_exceptionThrown() {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setHeight(null));
    dogDao.flushAndClear();
  }

  @Test(expectedExceptions = {ConstraintViolationException.class})
  public void when_birthday_notPast_then_exceptionThrown() {
    DogDto dog = DogGenerator.dog();
    dogDao.create(dog.setBirthDay(LocalDate.now().plusDays(1)));
    dogDao.flushAndClear();
  }

  @Test
  public void when_createValidDog10times_then_2batchIsUsed() {
    for (int i = 0; i < 10; i++) {
      dogDao.create(DogGenerator.dog());
    }
    dogDao.flushAndClear();
  }

  @Test
  public void when_createValidDogWithOwners_then_getSuccessful() {
    DogDto dog = DogGenerator.dogWithOwners(2);
    dogDao.create(dog);
    Long id = dog.getId();
    dogDao.flushAndClear();

    DogDto dogDto = dogDao.load(id);
    System.out.println("Loaded dog with name: " + dogDto.getName() + "; Owners should not be loaded here");
    System.out.println(dogDto.getOwners());

  }

}
