/**
 * Author: Niyi Odumosu
 * Project: Online Banking App
 * Purpose: Implement CRUD methods for the User DAO layer
 */

package com.bank.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import com.capitalone.bank.model.Customer;
import com.capitalone.bank.model.User;











import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;


public class UserDAO {
	private static Map<Long, User> users = new HashMap<Long, User>();

	//C- Create User
	public User createUser(User user) throws SQLException{

		Connection conn = DBUtil.connect();

		// INSERT statement
		String query = "INSERT INTO user( username, password, customer_id) " + "VALUES (?,?,?)";
		// Generate keys
		PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		
		pstmt.setString(1, user.getUsername());
		pstmt.setString(2, user.getPassword());
		System.out.println(user.getCustomerid());
		pstmt.setLong(3, user.getCustomerid());
		
		
		//execute update
		pstmt.executeUpdate();
		long key = DBUtil.getGeneratedKey(pstmt);
		user.setUserid(key);
		//Clean-up environment
		DBUtil.close(pstmt);
		DBUtil.close(conn);
		
		return user;
	}

	//R - Retrieve user 
	public User getUserByID(long userid) throws SQLException{

		User user = new User();
		Connection conn = DBUtil.connect();
		
		ResultSet rs = null;

		// Extract data from result set
		String query = "SELECT * FROM user WHERE user_id = " + userid;
		PreparedStatement pstmt = conn.prepareStatement(query);
		rs = pstmt.executeQuery(query);
		while(rs.next()){
			
			String un = rs.getString("username");
			String pw = rs.getString("password");
			long customerid = rs.getLong("customer_id");
			user.setUserid(userid);
			user.setUsername(un);		
			user.setPassword(pw);	
			user.setCustomerid(customerid);
		}
		
		//Clean-up environment
		DBUtil.close(conn);
		DBUtil.close(pstmt);
		DBUtil.close(rs);

		return user;
	}
	
	public User getUserByUN(String un) throws SQLException{

		User user = new User();
		Connection conn = DBUtil.connect();
		
		ResultSet rs = null;
		// Execute a query
		System.out.println("Retrieving user...");

		// Extract data from result set
		String query = "SELECT * FROM user WHERE username = " + "\'"+ un +"\'";
		PreparedStatement pstmt = conn.prepareStatement(query);
		rs = pstmt.executeQuery(query);
		
		while(rs.next()){
			
			un = rs.getString("username");
//			if(rs.next() == false){
////				String	message = "<p><font color='red'>" + "Username does not exist! Please try again or open a new account."+"</font></p>";
////				break;
//				
//				}
			String pw = rs.getString("password");
			Long customerid = rs.getLong("customer_id");
			user.setUsername(un);		
			user.setPassword(pw);	
			user.setCustomerid(customerid);
		}
		

		//Clean-up environment
		DBUtil.close(conn);
		DBUtil.close(pstmt);
		DBUtil.close(rs);

		return user;
	}

	public static Map<Long, User> getAllUsers() throws SQLException{
		User user = new User();
		
		Connection conn = DBUtil.connect();

		// Extract data from result set
		String query = "SELECT * FROM user ";
		PreparedStatement pstmt = conn.prepareStatement(query);
		
		ResultSet rs = pstmt.executeQuery(query);
		//check if result set is empty
		Map<Long, User> users = new HashMap<Long, User>();
		while(rs.next()){
			String userid = rs.getString("user_id");
			String un = rs.getString("username");
			String pw = rs.getString("password");
			user.setUserid(Long.valueOf(userid));
			user.setUsername(un);		
			user.setPassword(pw);
			users.put(user.getUserid(), user);
		}
		
		//Clean-up environment
		DBUtil.close(conn);
		DBUtil.close(pstmt);
		DBUtil.close(rs);
		return users;
	}
	
	//U - Update user
	public User editUser(User user) throws SQLException{

		long customerid = user.getCustomerid();
		Connection conn = DBUtil.connect();

		// Create a query
		String query = "UPDATE user set username = ?, password = ? WHERE customer_id = " + customerid +"";
		
		// Execute a query
		PreparedStatement pstmt = conn.prepareStatement(query);

		pstmt.setString(1, user.getUsername());
		pstmt.setString(2, user.getPassword());
		
		System.out.println("Key is " + customerid);
		//execute update
		pstmt.executeUpdate();

		//Clean-up environment
		DBUtil.close(pstmt);
		DBUtil.close(conn);

		return user;
	}

	//D - Delete user
	public void removeUser(long userid) throws SQLException{

		User user = new User();
		Connection conn = DBUtil.connect();
		// Create a query
		String query = "DELETE FROM user WHERE user_id=" + userid;
		
		// Execute a query
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery(query);
		while(rs.next()){
			
			String un = rs.getString("username");
			String pw = rs.getString("password");
			user.setUserid(userid);
			user.setUsername(un);		
			user.setPassword(pw);	
		}
		

		//execute update
		pstmt.executeUpdate();
		//Clean-up environment
		DBUtil.close(pstmt);
		DBUtil.close(conn);

	}

	public static Map<Long, User> getUsers(){
	    return users;
	  }
}
