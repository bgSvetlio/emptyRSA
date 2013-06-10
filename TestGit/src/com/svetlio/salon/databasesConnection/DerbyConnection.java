package com.svetlio.salon.databasesConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyConnection implements ConnectionProvider {
	
	private Connection conn=null;
	private String clientDriver = "org.apache.derby.jdbc.ClientDriver";
	private String host = "jdbc:derby://localhost:1527/SvetlioSalonReservations";
	private String user = "APP";
	private String password = "user";
	
	public DerbyConnection(){
		
		try {
            Class.forName(clientDriver).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Connection getConnection() {
	
		try {
			conn = DriverManager.getConnection(host, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
