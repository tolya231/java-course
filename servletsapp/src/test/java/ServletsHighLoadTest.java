import org.testng.annotations.Test;

public class ServletsHighLoadTest {

  @Test
  public void testAttributeServletFixedTP() {
    HighLoadHelper.executeTasksInThreadPoolWithTimeBenchmark(5, 100000, 100000, "fixed");
    HighLoadHelper.executeTasksInThreadPoolWithTimeBenchmark(10, 100000, 100000, "fixed");
    HighLoadHelper.executeTasksInThreadPoolWithTimeBenchmark(20, 100000, 100000, "fixed");
    HighLoadHelper.executeTasksInThreadPoolWithTimeBenchmark(5, 100000, 1, "fixed");
    HighLoadHelper.executeTasksInThreadPoolWithTimeBenchmark(5, 1, 100000, "fixed");
  }

  @Test
  public void testAttributeServletCachedTP() {
//    HighLoadHelper.executeTasksInThreadPoolWithTimeBenchmark(5, 100000, 100000, "cached");
//    Exception in thread "pool-1-thread-83" java.lang.OutOfMemoryError: unable to create new native thread
  }

  @Test
  public void testAttributeServletWorkStealingdTP() {
    HighLoadHelper.executeTasksInThreadPoolWithTimeBenchmark(5, 100000, 100000, "workstealing");
    HighLoadHelper.executeTasksInThreadPoolWithTimeBenchmark(5, 100000, 1, "workstealing");
    HighLoadHelper.executeTasksInThreadPoolWithTimeBenchmark(5, 1, 100000, "workstealing");
  }

//  default tomcat thread pool
// https://github.com/quickhack/tomcat/blob/master/src/main/java/org/apache/catalina/core/StandardThreadExecutor.java
}
