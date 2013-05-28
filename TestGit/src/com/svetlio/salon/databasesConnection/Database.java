package com.svetlio.salon.databasesConnection;

public interface Database {
	public String getUser();
	public void setUser(String user);
	public String getPassword();
	public void setPassword(String password);
	public String getClientDriver();
	public String getConnection();

}
