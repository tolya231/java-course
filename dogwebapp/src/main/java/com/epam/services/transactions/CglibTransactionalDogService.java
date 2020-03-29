package com.epam.services.transactions;

import com.epam.jdbc.JdbcDataSourceUtils;
import com.epam.services.DogService;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.sql.DataSource;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibTransactionalDogService implements MethodInterceptor {

  private final DataSource dataSource;
  private final Object target;

  private CglibTransactionalDogService(DataSource dataSource, Object target) {
    this.dataSource = dataSource;
    this.target = target;
  }

  public static Object createProxy(DataSource dataSource, Object target) {
    CglibTransactionalDogService service = new CglibTransactionalDogService(
        dataSource, target);
    return Enhancer.create(DogService.class, service);
  }

  @Override
  public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
    Object result;
    if (method.getDeclaringClass() != Object.class) {
      try {
        JdbcDataSourceUtils.startTransaction(dataSource);
        result = methodProxy.invoke(target, args);
        JdbcDataSourceUtils.commitTransaction(dataSource);
        return result;
      } catch (Exception e) {
        JdbcDataSourceUtils.rollbackTransaction(dataSource);
        throw new RuntimeException("Failed to execute method with args: " + Arrays.toString(args),
            e);
      } finally {
        JdbcDataSourceUtils.closeConnection(dataSource);
      }
    } else {
      return methodProxy.invoke(target, args);
    }
  }
}
