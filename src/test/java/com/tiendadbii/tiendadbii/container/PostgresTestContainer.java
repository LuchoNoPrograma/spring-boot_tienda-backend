package com.tiendadbii.tiendadbii.container;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {
  public static final String POSTGRES_IMAGE = "postgres:15.1";
  public static final String POSTGRES_DB_NAME = "test";
  public static PostgreSQLContainer container;

  public PostgresTestContainer() {
    super(POSTGRES_IMAGE);
  }

  public static PostgreSQLContainer getInstance() {
    if (container == null) {
      container = new PostgresTestContainer().withDatabaseName(POSTGRES_DB_NAME);
    }
    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("DB_URL", container.getJdbcUrl());
    System.setProperty("DB_USERNAME", container.getUsername());
    System.setProperty("DB_PASSWORD", container.getPassword());
  }

  @Override
  public void stop(){

  }
}
