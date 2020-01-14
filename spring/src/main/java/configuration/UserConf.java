package configuration;

import entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConf {

  @Bean
  public User getUser() {
    return new User();
  }
}
