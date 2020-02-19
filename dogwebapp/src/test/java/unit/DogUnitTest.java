package unit;


import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import com.epam.entities.Dog;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DogUnitTest {

  private static Dog dog() {
    return new Dog()
        .setName("beagle")
        .setHeight(20)
        .setWeight(20)
        .setBirthDay(LocalDate.of(2020, 1, 21));
  }

  private Validator validator;

  @BeforeMethod
  public void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  public void dogNameValidate() {
    Set<ConstraintViolation<Dog>> violations = validator.validate(dog());
    assertTrue(violations.isEmpty());

    violations = validator.validate(dog().setName(""));
    assertFalse(violations.isEmpty());
  }

  @Test
  public void dogHeightValidate() {
    Set<ConstraintViolation<Dog>> violations = validator.validate(dog());
    assertTrue(violations.isEmpty());

    violations = validator.validate(dog().setHeight(0));
    assertFalse(violations.isEmpty());
  }

  @Test
  public void dogWeightValidate() {
    Set<ConstraintViolation<Dog>> violations = validator.validate(dog());
    assertTrue(violations.isEmpty());

    violations = validator.validate(dog().setWeight(0));
    assertFalse(violations.isEmpty());
  }

  @Test
  public void dogBirthdayValidate() {
    Set<ConstraintViolation<Dog>> violations = validator.validate(dog());
    assertTrue(violations.isEmpty());

    violations = validator.validate(dog().setBirthDay(LocalDate.now().plusDays(1)));
    assertFalse(violations.isEmpty());
  }
}
