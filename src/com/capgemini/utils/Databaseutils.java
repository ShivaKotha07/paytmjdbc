package com.capgemini.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Databaseutils {
	private static Connection connection;

	private static Databaseutils object = new Databaseutils();

	private Databaseutils() {

	}

	public synchronized static Databaseutils getInstance() {
		return object;

	}

	public void openDatabaseConnections(String username, String password) throws SQLException, ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "username", "password");

	}

	public Connection openDatabaseConnection() throws SQLException, ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "seed");
		return connection;
	}

	public void closeDatabaseConnection() throws SQLException {

		if (connection != null)
			connection.close();
	}

}
