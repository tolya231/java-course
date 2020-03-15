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

  public Connection getConnection() {
    return threadLocal.get();
  }

  @SneakyThrows
  public Connection createConnection() {
    Connection connection = dataSource.getConnection();
    threadLocal.set(connection);
    return connection;
  }

  @SneakyThrows
  public static void closeConnection(Connection connection) {
    if (connection != null) {
      connection.close();
    }
  }

  @SneakyThrows
  public static void rollbackConnection(Connection connection) {
    if (connection != null) {
      connection.rollback();
    }
  }
}
