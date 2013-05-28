package com.svetlio.salon.databasesConnection;

public class DerbyDBConnect implements Database{
	private String clientDriver = "org.apache.derby.jdbc.ClientDriver";
	private String host = "jdbc:derby://localhost:1527/SvetlioSalonReservations";
	private String user = "APP";
	private String password = "user";
	
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
	public String getClientDriver(){
		return clientDriver;
	}
	public String getConnection(){
		return host;
	}
	

}
