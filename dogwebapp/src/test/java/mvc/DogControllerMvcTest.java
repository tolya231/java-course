package mvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.Assert.assertEquals;

import com.epam.controllers.DogController;
import com.epam.entities.Dog;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DogControllerMvcTest {

  private MockMvc mockMvc;
  private String url = "http://localhost:8080/dog";
  private ObjectMapper objectMapper;


  private static Dog dog() {
    return new Dog()
        .setName("beagle")
        .setHeight(20)
        .setWeight(20)
        .setBirthDay(LocalDate.of(2020, 1, 21));
  }

  private Dog createDog() throws Exception {
    String dogResponse = mockMvc.perform(MockMvcRequestBuilders.post(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dog())))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();
    return objectMapper.readValue(dogResponse, Dog.class);
  }

  @BeforeMethod
  public void setUp() throws Exception {
    MockMvcBuilders.standaloneSetup(DogController.class);
    mockMvc = MockMvcBuilders.standaloneSetup(DogController.class).build();
    objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();
  }

  @Test
  public void when_getDogWithExistingId_then_DogReturned() throws Exception {
    Dog dog = createDog();

    String response = mockMvc.perform(MockMvcRequestBuilders.get(url + "/" + dog.getId())
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();
    assertEquals(dog, objectMapper.readValue(response, Dog.class));

  }

  @Test
  public void when_getNotExistingDog_then_statusCode404() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(url + "/0")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void when_createValidDog_then_createdSuccessfully() throws Exception {
    Dog dog = createDog();
    assertEquals(dog().setId(dog.getId()), dog);

  }

  @Test
  public void when_createNotValidDog_then_statusCode400() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dog().setWeight(-1))))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void when_updateDogSetEmptyWeight_then_updatedOnlyThisField() throws Exception {
    Dog dog = createDog().setWeight(null);

    String updated = mockMvc.perform(MockMvcRequestBuilders.put(url + "/" + dog.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dog)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    assertEquals(dog, objectMapper.readValue(updated, Dog.class));
  }

  @Test
  public void when_updateDogSetNotValidDate_then_statusCode400() throws Exception {
    Dog dog = createDog().setBirthDay(LocalDate.now());

    mockMvc.perform(MockMvcRequestBuilders.put(url + "/" + dog.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dog)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void when_deleteExistingDog_then_deletedSuccessfully() throws Exception {
    Long id = createDog().getId();

    mockMvc.perform(MockMvcRequestBuilders.delete(url + "/" + id))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
  }

  @Test
  public void when_deleteNotExistingDog_then_statusCode400() throws Exception {
    Long id = createDog().getId();

    mockMvc.perform(MockMvcRequestBuilders.delete(url + "/" + id))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());

    mockMvc.perform(MockMvcRequestBuilders.delete(url + "/" + id))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isNotFound());
  }
}
