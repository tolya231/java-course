package com.epam.jdbc;

import java.sql.Connection;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.springframework.jdbc.datasource.DataSourceUtils;

public abstract class JdbcDataSourceUtils extends DataSourceUtils {

  @SneakyThrows
  public static void startTransaction(DataSource dataSource) {
    Connection connection = DataSourceUtils.getConnection(dataSource);
    connection.setAutoCommit(false);
  }

  @SneakyThrows
  public static void commitTransaction(DataSource dataSource) {
    Connection connection = DataSourceUtils.getConnection(dataSource);
    connection.commit();
  }

  @SneakyThrows
  public static void rollbackTransaction(DataSource dataSource) {
    Connection connection = DataSourceUtils.getConnection(dataSource);
    connection.rollback();
  }

  @SneakyThrows
  public static void closeConnection(DataSource dataSource) {
    Connection connection = DataSourceUtils.getConnection(dataSource);
    DataSourceUtils.releaseConnection(connection, dataSource);
  }
}
