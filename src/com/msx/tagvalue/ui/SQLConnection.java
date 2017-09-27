package com.msx.tagvalue.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://127.0.0.1:3306/";
	private static String default_database = "lifeache";
	private static String name = "root";
	private static String pswd = "";
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		return getConnection(default_database);
	}
	
	public static Connection getConnection(String database){
		Connection con = null;
		try {
			con= DriverManager.getConnection(url + database, name, pswd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}
