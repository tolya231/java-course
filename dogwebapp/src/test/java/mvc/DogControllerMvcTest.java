package mvc;

import com.epam.controllers.DogController;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeMethod;

public class DogControllerMvcTest {

  private MockMvc mockMvc;

  @BeforeMethod
  public void setUp() throws Exception {
    MockMvcBuilders.standaloneSetup(DogController.class);
    mockMvc = MockMvcBuilders.standaloneSetup(DogController.class).build();
  }
}
