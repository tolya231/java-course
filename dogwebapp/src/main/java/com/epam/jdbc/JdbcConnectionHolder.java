package com.epam.jdbc;

import java.sql.Connection;
import javax.sql.DataSource;
import lombok.SneakyThrows;

public class JdbcConnectionHolder {

  private final DataSource dataSource;
  private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

  public JdbcConnectionHolder(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @SneakyThrows
  public Connection createOrGetConnection() {
    Connection connection = threadLocal.get();
    if (connection == null || connection.isClosed()) {
      threadLocal.set(dataSource.getConnection());
    }
    return threadLocal.get();
  }

  @SneakyThrows
  public void startTransaction() {
    Connection connection = threadLocal.get();
    connection.setAutoCommit(false);
  }

  @SneakyThrows
  public void commitTransaction() {
    Connection connection = threadLocal.get();
    connection.commit();
  }

  @SneakyThrows
  public void closeConnection() {
    Connection connection = threadLocal.get();
      connection.close();
  }

  @SneakyThrows
  public void rollbackConnection() {
    Connection connection = threadLocal.get();
      connection.rollback();
  }
}
