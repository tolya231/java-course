package integration;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import com.epam.entities.Dog;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import java.time.LocalDate;
import org.testng.annotations.Test;

public class DogControllerIntTest {

  private String url = "http://localhost:8080/dog";

  private static Dog dog() {
    return new Dog()
        .setName("beagle")
        .setHeight(20)
        .setWeight(20)
        .setBirthDay(LocalDate.of(2020, 1, 21));
  }

  private Dog createDog() {
    return given().baseUri(url)
        .contentType(ContentType.JSON)
        .body(dog())
        .when().post().thenReturn()
        .as(Dog.class, ObjectMapperType.JACKSON_2);
  }

  @Test
  public void when_getDogWithExistingId_then_DogReturned() {
    Long id = createDog().getId();

    Dog dog = given().baseUri(url + "/" + id)
        .when().get().thenReturn()
        .as(Dog.class, ObjectMapperType.JACKSON_2);

    assertEquals(dog.getId(), id);
    assertEquals(dog.getName(), "beagle");
    assertEquals(dog.getWeight(), Integer.valueOf(20));
    assertEquals(dog.getHeight(), Integer.valueOf(20));
  }

  @Test
  public void when_getNotExistingDog_then_statusCode404() {
    given().baseUri(url + "/0")
        .when().get().then()
        .statusCode(404);
  }

  @Test
  public void when_createValidDog_then_createdSuccessfully() {
    Dog dog = createDog();

    assertEquals(dog.getName(), "beagle");
    assertEquals(dog.getWeight(), Integer.valueOf(20));
  }

  @Test
  public void when_createNotValidDog_then_statusCode400() {
    given().baseUri(url).contentType(ContentType.JSON)
        .body(dog().setWeight(-1))
        .when().post().then()
        .statusCode(400);
  }

  @Test
  public void when_updateDogSetEmptyWeight_then_updatedOnlyThisField() {
    Dog dog = createDog().setWeight(null);

    Dog updated = given().baseUri(url + "/" + dog.getId())
        .contentType(ContentType.JSON)
        .body(dog)
        .when().put().thenReturn()
        .as(Dog.class, ObjectMapperType.JACKSON_2);
    assertEquals(updated.getName(), "beagle");
    assertEquals(updated.getWeight(), null);
    assertEquals(updated.getHeight(), Integer.valueOf(20));
  }

  @Test
  public void when_updateDogSetNotValidDate_then_statusCode400() {
    Dog dog = createDog().setBirthDay(LocalDate.now());

    given().baseUri(url + "/" + dog.getId())
        .contentType(ContentType.JSON)
        .body(dog)
        .when().put().then()
        .statusCode(400);
  }

  @Test
  public void when_deleteExistingDog_then_deletedSuccessfully() {
    Long id = createDog().getId();

    given().baseUri(url + "/" + id)
        .when().delete().then()
        .statusCode(200);
  }

  @Test
  public void when_deleteNotExistingDog_then_statusCode400() {
    Long id = createDog().getId();

    given().baseUri(url + "/" + id)
        .when().delete();

    given().baseUri(url + "/" + id)
        .when().delete().then()
        .statusCode(404);
  }
}
