package integration;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertNotNull;

import com.epam.dto.DogDto;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import java.time.LocalDate;
import java.util.Collections;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.DogGenerator;

public class DogControllerIntTest {

  private String url = "http://localhost:8080/dog";

  private DogDto createDog() {
    return given().baseUri(url)
        .contentType(ContentType.JSON)
        .body(DogGenerator.dog())
        .when().post().thenReturn()
        .as(DogDto.class, ObjectMapperType.JACKSON_2);
  }

  @Test
  public void when_getDogWithExistingId_then_DogReturned() {
    DogDto createdDog = createDog();

    DogDto dog = given().baseUri(url + "/" + createdDog.getId())
        .when().get().thenReturn()
        .as(DogDto.class, ObjectMapperType.JACKSON_2);

    Assert.assertTrue(EqualsBuilder.reflectionEquals(dog, createdDog, Collections.singletonList("id")));
  }

  @Test
  public void when_getNotExistingDog_then_statusCode404() {
    given().baseUri(url + "/0")
        .when().get().then()
        .statusCode(404);
  }

  @Test
  public void when_createValidDog_then_createdSuccessfully() {
    DogDto dog = DogGenerator.dog();
    DogDto createdDog = given().baseUri(url)
        .contentType(ContentType.JSON)
        .body(dog)
        .when().post().thenReturn()
        .as(DogDto.class, ObjectMapperType.JACKSON_2);

    Assert.assertTrue(EqualsBuilder.reflectionEquals(dog, createdDog, Collections.singletonList("id")));
    assertNotNull(createdDog.getId());
  }

  @Test
  public void when_createNotValidDog_then_statusCode400() {
    given().baseUri(url).contentType(ContentType.JSON)
        .body(DogGenerator.dog().setWeight(-1))
        .when().post().then()
        .statusCode(400);
  }

  @Test
  public void when_updateDogSetWeight1_then_updated() {
    DogDto dog = createDog().setWeight(1);

    DogDto updatedDog = given().baseUri(url + "/" + dog.getId())
        .contentType(ContentType.JSON)
        .body(dog)
        .when().put().thenReturn()
        .as(DogDto.class, ObjectMapperType.JACKSON_2);
    Assert.assertTrue(EqualsBuilder.reflectionEquals(dog, updatedDog, Collections.singletonList("id")));

  }

  @Test
  public void when_updateDogSetNotValidDate_then_statusCode400() {
    DogDto dog = createDog().setBirthDay(LocalDate.now());

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
