package com.epam.services.transactions;

import com.epam.jdbc.JdbcConnectionHolder;
import com.epam.services.DogService;
import java.lang.reflect.Method;
import java.util.Arrays;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibTransactionalDogService implements MethodInterceptor {

  private final JdbcConnectionHolder jdbcConnectionHolder;
  private final Object target;

  private CglibTransactionalDogService(JdbcConnectionHolder jdbcConnectionHolder, Object target) {
    this.jdbcConnectionHolder = jdbcConnectionHolder;
    this.target = target;
  }

  public static Object createProxy(JdbcConnectionHolder jdbcConnectionHolder, Object target) {
    CglibTransactionalDogService service = new CglibTransactionalDogService(
        jdbcConnectionHolder, target);
    return Enhancer.create(DogService.class, service);
  }

  @Override
  public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
    Object result;
    if (method.getDeclaringClass() != Object.class) {
      try {
        jdbcConnectionHolder.startTransaction();
        result = methodProxy.invoke(target, args);
        jdbcConnectionHolder.commitTransaction();
        return result;
      } catch (Exception e) {
        jdbcConnectionHolder.rollbackTransaction();
        throw new RuntimeException("Failed to execute method with args: " + Arrays.toString(args),
            e);
      } finally {
        jdbcConnectionHolder.closeConnection();
      }
    } else {
      return methodProxy.invoke(target, args);
    }
  }
}
