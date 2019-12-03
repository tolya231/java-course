import java.io.File;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class Application {

  public static void main(String[] args) throws Exception {

    String webappDirLocation = "servletsapp/src/main/webapp/";
    Tomcat tomcat = new Tomcat();

    tomcat.setPort(9090);

    StandardContext ctx = (StandardContext) tomcat
        .addWebapp("/", new File(webappDirLocation).getAbsolutePath());

    File additionWebInfClasses = new File("servletsapp/target/classes/servlets");
    WebResourceRoot resources = new StandardRoot(ctx);
    resources.addPreResources(
        new DirResourceSet(resources, "/", additionWebInfClasses.getAbsolutePath(), "/"));
    ctx.setResources(resources);

    tomcat.start();
    tomcat.getServer().await();
  }

}
