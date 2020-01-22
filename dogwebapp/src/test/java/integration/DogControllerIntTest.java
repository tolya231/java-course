package integration;

import com.epam.entities.Dog;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.junit.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class DogControllerIntTest {

  private Dog dog = new Dog(1L, "beagle", LocalDate.of(2020, 1, 22), 20, 20);
  private String url = "http://localhost:8081/dog";
  ResponseSpecification successResponseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();


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
    given().baseUri(url).body(dog)
        .when().post().then()
        .spec(successResponseSpec)
        .body("name", equalTo("beagle"));
  }

  @Test
  public void testPut() {
  }

  @Test
  public void testDelete() {
  }
}
