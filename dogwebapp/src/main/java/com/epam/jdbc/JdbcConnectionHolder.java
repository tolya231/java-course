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
    this.setConnection();
    return threadLocal.get();
  }

  @SneakyThrows
  public void setConnection() {
    threadLocal.set(dataSource.getConnection());
  }
}
