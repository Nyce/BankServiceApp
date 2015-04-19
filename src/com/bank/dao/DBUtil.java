package com.bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.capitalone.bank.model.*;

public class DBUtil {
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private static final String DB_URL = "jdbc:mysql://localhost/bankofniyi";

	//  Database credentials
	private static final String USER = "niyi";
	private static final String PASS = "flow37";
	
		
	public static Connection connect(){
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			
			//Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Conneced to database...");
			System.out.println(conn);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not find Driver class");
			e.printStackTrace();
			}
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not connect to MySQL db");
			e.printStackTrace();
		}
		return conn;
	}
	
	public static long getGeneratedKey(PreparedStatement pstmt) throws SQLException {
		
		ResultSet keys = pstmt.getGeneratedKeys();    
		keys.next();  
		long key = keys.getLong(1);
		
		System.out.println("key: " + key);
		return key;
	}
	

	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
				
			} catch (SQLException e) {
				/*log or print or ignore*/
			}
		}
	}

	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				/*log or print or ignore*/
			}
		}
	}

	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				/*log or print or ignore*/
			}
		}
	}
}
