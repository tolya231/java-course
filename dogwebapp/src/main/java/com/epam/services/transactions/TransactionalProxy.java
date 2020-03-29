package com.epam.services.transactions;

import com.epam.jdbc.JdbcDataSourceUtils;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import javax.sql.DataSource;

public class TransactionalProxy implements InvocationHandler {

  private final DataSource dataSource;
  private final Object target;

  private TransactionalProxy(DataSource dataSource, Object target) {
    this.dataSource = dataSource;
    this.target = target;
  }

  public static Object createProxy(DataSource dataSource, Object target,
      Class<?> interfaceToProxy) {
    return Proxy.newProxyInstance(target.getClass().getClassLoader(),
        new Class<?>[]{interfaceToProxy},
        new TransactionalProxy(dataSource, target));
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    try {
      JdbcDataSourceUtils.startTransaction(dataSource);
      Object result = method.invoke(target, args);
      JdbcDataSourceUtils.commitTransaction(dataSource);
      return result;
    } catch (Exception e) {
      JdbcDataSourceUtils.rollbackTransaction(dataSource);
      throw new RuntimeException("Failed to execute method with args: " + Arrays.toString(args), e);
    } finally {
      JdbcDataSourceUtils.closeConnection(dataSource);
    }
  }
}
