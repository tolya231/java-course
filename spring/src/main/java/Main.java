import configuration.UserConf;
import java.util.Arrays;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

  public static void main(String[] args) {
    ApplicationContext xmlContext = new ClassPathXmlApplicationContext("beans.xml");
    System.out.println(Arrays.toString(xmlContext.getBeanDefinitionNames()));
    ApplicationContext javaContext = new AnnotationConfigApplicationContext(UserConf.class);
    System.out.println(Arrays.toString(javaContext.getBeanDefinitionNames()));

  }

}
