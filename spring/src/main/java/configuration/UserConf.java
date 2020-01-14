package configuration;

import entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource( { "classpath:beans.xml" } )
public class UserConf {

  @Bean
  public User getUser() {
    return new User();
  }
}
