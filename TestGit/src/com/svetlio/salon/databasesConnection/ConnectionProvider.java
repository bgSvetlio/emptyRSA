package com.svetlio.salon.databasesConnection;

import java.sql.Connection;

public interface ConnectionProvider {

	public Connection getConnection();
}
