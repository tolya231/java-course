package com.epam.services.transactions;

import com.epam.jdbc.JdbcConnectionHolder;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class TransactionalProxy implements InvocationHandler {

  private final JdbcConnectionHolder jdbcConnectionHolder;
  private final Object target;

  public TransactionalProxy(JdbcConnectionHolder jdbcConnectionHolder, Object target) {
    this.jdbcConnectionHolder = jdbcConnectionHolder;
    this.target = target;
  }

  public static Object createProxy(JdbcConnectionHolder jdbcConnectionHolder, Object target,
      Class<?> interfaceToProxy) {
    return Proxy.newProxyInstance(target.getClass().getClassLoader(),
        new Class<?>[]{interfaceToProxy},
        new TransactionalProxy(jdbcConnectionHolder, target));
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    try {
      jdbcConnectionHolder.createOrGetConnection();
      jdbcConnectionHolder.startTransaction();
      Object result = method.invoke(target, args);
      jdbcConnectionHolder.commitTransaction();
      return result;
    } catch (Exception e) {
      jdbcConnectionHolder.rollbackTransaction();
      throw new RuntimeException("Failed to execute method with args: " + Arrays.toString(args), e);
    } finally {
      jdbcConnectionHolder.closeConnection();
    }
  }
}
