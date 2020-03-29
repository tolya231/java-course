package com.epam.aop.aspects;

import com.epam.jdbc.JdbcDataSourceUtils;
import java.util.Arrays;
import javax.sql.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TransactionalAspect {

  private final DataSource dataSource;

  public TransactionalAspect(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Pointcut("execution(* com.epam.services.DogService.*(..))")
  public void addTransactionToDogService() {
  }

  @Around("addTransactionToDogService()")
  public Object addTransactionToDogServiceAdvice(ProceedingJoinPoint pjp) {
    try {
      JdbcDataSourceUtils.startTransaction(dataSource);
      Object result = pjp.proceed();
      JdbcDataSourceUtils.commitTransaction(dataSource);
      return result;
    } catch (Throwable e) {
      JdbcDataSourceUtils.rollbackTransaction(dataSource);
      throw new RuntimeException("Failed to execute method  " + pjp.getSignature().getName()
          + " with args " + Arrays.toString(pjp.getArgs()), e);
    } finally {
      JdbcDataSourceUtils.closeConnection(dataSource);
    }
  }
}
