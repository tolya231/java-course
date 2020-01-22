package unit;

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
        .birthDate(LocalDate.of(2020, 1, 22))
        .build();
  }

  @Test
  public void testValidation() {
    dog.setId(null);
    Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
  }
}
