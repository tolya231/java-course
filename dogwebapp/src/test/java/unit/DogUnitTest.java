package unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.epam.entities.Dog;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

public class DogUnitTest {

  private Dog dog = new Dog(1L, "beagle", LocalDate.of(2020, 1, 22), 20, 20);
  private Validator validator;

  @Before
  public void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    dog = Dog.builder()
        .id(1L)
        .name("beagle")
        .height(20)
        .weight(20)
        .birthDay(LocalDate.of(2020, 1, 21))
        .build();
  }

  @Test
  public void testIdValidation() {
    dog.setId(null);
    Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
    assertFalse(violations.isEmpty());
    dog.setId(-1L);
    violations = validator.validate(dog);
    assertFalse(violations.isEmpty());
    dog.setId(129L);
    violations = validator.validate(dog);
    assertTrue(violations.isEmpty());
  }

  @Test
  public void testNameValidation() {
    Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
    assertTrue(violations.isEmpty());
    dog.setName("");
    violations = validator.validate(dog);
    assertFalse(violations.isEmpty());
  }

  @Test
  public void testHeightValidation() {
    Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
    assertTrue(violations.isEmpty());
    dog.setHeight(-5);
    violations = validator.validate(dog);
    assertFalse(violations.isEmpty());
  }

  @Test
  public void testWeightValidation() {
    Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
    assertTrue(violations.isEmpty());
    dog.setWeight(-17);
    violations = validator.validate(dog);
    assertFalse(violations.isEmpty());
  }

  @Test
  public void testBirthdayValidation() {
    Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
    assertTrue(violations.isEmpty());
    dog.setBirthDay(LocalDate.now().plusDays(1));
    violations = validator.validate(dog);
    assertFalse(violations.isEmpty());
  }
}
