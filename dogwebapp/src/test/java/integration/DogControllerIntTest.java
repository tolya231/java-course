package integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;

import com.epam.entities.Dog;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class DogControllerIntTest {

  private Dog dog;
  private String url = "http://localhost:8081/dog";
  ResponseSpecification successResponseSpec = new ResponseSpecBuilder().expectStatusCode(200)
      .expectContentType(ContentType.JSON).build();

  @Before
  public void setUp() {
    dog = Dog.builder()
        .id(1L)
        .name("beagle")
        .height(20)
        .weight(20)
        .birthDay(LocalDate.of(2020, 1, 21))
        .build();
  }

  @Test
  public void testGet() {
    given().baseUri(url + "/1")
        .when().get().then()
        .spec(successResponseSpec)
        .body("id", equalTo(1));

    given().baseUri(url + "0")
        .when().get().then()
        .statusCode(404);
  }

  @Test
  public void testPost() {
    given().baseUri(url).contentType(ContentType.JSON)
        .body(dog)
        .when().post().then()
        .spec(successResponseSpec)
        .body("name", equalTo("beagle"));

    dog.setWeight(-1);
    given().baseUri(url).contentType(ContentType.JSON)
        .body(dog)
        .when().post().then()
        .statusCode(400);
  }

  @Test
  public void testPut() {
    dog.setWeight(null);
    given().baseUri(url + "/1").contentType(ContentType.JSON)
        .body(dog)
        .when().put().then()
        .spec(successResponseSpec)
        .body("name", equalTo("beagle"))
        .body("weight", nullValue());

    dog.setId(-1L);
    given().baseUri(url).contentType(ContentType.JSON)
        .body(dog)
        .when().put().then()
        .statusCode(400);
  }

  @Test
  public void testDelete() {
    given().baseUri(url + "/1")
        .when().delete().then()
        .statusCode(200);

    given().baseUri(url + "0")
        .when().delete().then()
        .statusCode(404);
  }
}
