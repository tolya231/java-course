package unit;


import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import com.epam.dto.DogDto;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.DogGenerator;

public class DogUnitTest {

  private Validator validator;

  @BeforeMethod
  public void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  public void validateInvalidNames_empty_or_null() {
    Set<ConstraintViolation<DogDto>> violations = validator.validate(DogGenerator.dog());
    assertTrue(violations.isEmpty());

    violations = validator.validate(DogGenerator.dog().setName(""));
    assertFalse(violations.isEmpty());

    violations = validator.validate(DogGenerator.dog().setName(null));
    assertFalse(violations.isEmpty());
  }

  @Test
  public void validateInvalidHeight_0_or_null() {
    Set<ConstraintViolation<DogDto>> violations = validator.validate(DogGenerator.dog());
    assertTrue(violations.isEmpty());

    violations = validator.validate(DogGenerator.dog().setHeight(0));
    assertFalse(violations.isEmpty());

    violations = validator.validate(DogGenerator.dog().setHeight(null));
    assertFalse(violations.isEmpty());
  }

  @Test
  public void validateInvalidWeight_0_or_null() {
    Set<ConstraintViolation<DogDto>> violations = validator.validate(DogGenerator.dog());
    assertTrue(violations.isEmpty());

    violations = validator.validate(DogGenerator.dog().setWeight(0));
    assertFalse(violations.isEmpty());

    violations = validator.validate(DogGenerator.dog().setWeight(null));
    assertFalse(violations.isEmpty());
  }

  @Test
  public void validateInvalidBirthday_notPast() {
    Set<ConstraintViolation<DogDto>> violations = validator.validate(DogGenerator.dog());
    assertTrue(violations.isEmpty());

    violations = validator.validate(DogGenerator.dog().setBirthDay(LocalDate.now().plusDays(1)));
    assertFalse(violations.isEmpty());
  }
}
