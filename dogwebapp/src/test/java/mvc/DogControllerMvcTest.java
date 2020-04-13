package mvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.dto.DogDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DogGenerator;

@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:/app-config.xml", "classpath:/web-config.xml", "classpath:/datasource-config.xml"})
public class DogControllerMvcTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private WebApplicationContext wac;
  private MockMvc mockMvc;
  private String url = "http://localhost:8080/dog";
  private ObjectMapper objectMapper;

  private DogDto createDog() throws Exception {
    String dogResponse = mockMvc.perform(MockMvcRequestBuilders.post(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(DogGenerator.dog())))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();
    return objectMapper.readValue(dogResponse, DogDto.class);
  }


  @BeforeClass
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();
  }

  @Test
  public void when_getDogWithExistingId_then_DogReturned() throws Exception {
    DogDto dog = createDog();

    String response = mockMvc.perform(MockMvcRequestBuilders.get(url + "/" + dog.getId())
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();
    Assert.assertTrue(EqualsBuilder.reflectionEquals(dog, objectMapper.readValue(response, DogDto.class),
        Collections.singletonList("id")));

  }

  @Test
  public void when_getNotExistingDog_then_statusCode404() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(url + "/0")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void when_createNotValidDog_then_statusCode400() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(DogGenerator.dog().setWeight(-1))))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void when_updateDogSetWeight1_then_updated() throws Exception {
    DogDto dog = createDog().setWeight(1);

    String updated = mockMvc.perform(MockMvcRequestBuilders.put(url + "/" + dog.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dog)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    Assert.assertTrue(EqualsBuilder.reflectionEquals(dog, objectMapper.readValue(updated, DogDto.class),
        Collections.singletonList("id")));
  }

  @Test
  public void when_updateDogSetNotValidDate_then_statusCode400() throws Exception {
    DogDto dog = createDog().setBirthDay(LocalDate.now());

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
