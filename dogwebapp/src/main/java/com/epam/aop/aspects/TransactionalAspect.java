package com.epam.aop.aspects;

import com.epam.jdbc.JdbcConnectionHolder;
import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TransactionalAspect {

  private final JdbcConnectionHolder jdbcConnectionHolder;

  public TransactionalAspect(JdbcConnectionHolder jdbcConnectionHolder) {
    this.jdbcConnectionHolder = jdbcConnectionHolder;
  }

  @Pointcut("execution(* com.epam.services.DogService.*(..))")
  public void addTransactionToDogService() {
  }

  @Around("addTransactionToDogService()")
  public Object addTransactionToDogServiceAdvice(ProceedingJoinPoint pjp) {
    try {
      jdbcConnectionHolder.startTransaction();
      Object result = pjp.proceed();
      jdbcConnectionHolder.commitTransaction();
      return result;
    } catch (Throwable e) {
      jdbcConnectionHolder.rollbackTransaction();
      throw new RuntimeException("Failed to execute method  " + pjp.getSignature().getName()
          + " with args " + Arrays.toString(pjp.getArgs()), e);
    } finally {
      jdbcConnectionHolder.closeConnection();
    }
  }
}
