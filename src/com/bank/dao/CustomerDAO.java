/**
 * Author: Niyi Odumosu
 * Project: Online Banking App
 * Purpose: Implement CRUD methods for the Customer DAO layer
 */
package com.bank.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.capitalone.bank.model.Customer;
import com.capitalone.bank.model.User;

public class CustomerDAO {

	private Map<Long, Customer> custMap = new HashMap<Long, Customer>();
	//C - Create Account
	public Customer createCustomer(Customer customer) throws SQLException{

		Connection conn = DBUtil.connect();
		// Create a query
		String query = "INSERT INTO customer(firstname, lastname, address, city, state, email, dob, sex, zipcode)" 
				+ " VALUES (?,?,?,?,?,?,?,?,?)";

		//
		PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

		// Execute a query
		System.out.println("Creating prepared statement...");

		pstmt.setString(1, customer.getFirstname());
		pstmt.setString(2, customer.getLastname());
		pstmt.setString(3, customer.getAddress());
		pstmt.setString(4, customer.getCity());
		pstmt.setString(5, customer.getState());
		pstmt.setString(6, customer.getEmail());
		pstmt.setString(7, customer.getDob());
		pstmt.setString(8, customer.getSex());
		pstmt.setLong(9, customer.getZip());
		
		System.out.println(pstmt.executeUpdate());
		
		System.out.println("Object created");
		
		long key = DBUtil.getGeneratedKey(pstmt);
		customer.setCustomerid(key);

		//Clean-up environment
		DBUtil.close(pstmt);
		DBUtil.close(conn);


		return customer;
	}



	//R - Retrieve customer 
	public Customer getCustomerByID(long customerid) throws SQLException{
		Customer customer = new Customer();
		Connection conn = DBUtil.connect();
		ResultSet rs = null;
		// Execute a query
		System.out.println("Creating prepared statement...");

		// Extract data from result set
		String query = "SELECT * FROM customer WHERE customer_id=" + customerid;
		PreparedStatement stmt = conn.prepareStatement(query);
		System.out.println("Statement Created");
		//check if result set is empty
		

		//add query to the result set
		rs = stmt.executeQuery(query);

		while(rs.next()){
			
			 String firstname = rs.getString("firstname");
			 String lastname = rs.getString("lastname");
			 String address = rs.getString("address");
			 String city = rs.getString("city");
			 String state = rs.getString("firstname");
			 Long zip = rs.getLong("zipcode");
			 String email= rs.getString("email");
			 String dob = rs.getString("dob");
			 String sex= rs.getString("sex");
			 customer.setCustomerid(customerid);
			 customer.setFirstname(firstname);
			 customer.setLastname(lastname);
			 customer.setAddress(address);
			 customer.setCity(city);
			 customer.setState(state);
			 customer.setZip(zip);
			 customer.setEmail(email);
			 customer.setDob(dob);
			 customer.setSex(sex);
			 
		}
		System.out.println("ID: " + customer.getCustomerid() +", firstname: " + customer.getFirstname() + ", lastname: " + customer.getLastname() + ", address: " + customer.getAddress() 
				 + ", city: " + customer.getCity() + ", state: " + customer.getState() + ", zip: " + customer.getZip() +", email: " + customer.getEmail() + ", dob: " + customer.getDob() + ", sex: " + customer.getSex());
		//Clean-up environment
		DBUtil.close(stmt);
		DBUtil.close(rs);
		DBUtil.close(conn);

		return customer;
	}

	public Customer getAllCustomers() throws SQLException{
		Customer customer = null;

		Connection conn = DBUtil.connect();

		// Extract data from result set
		String query = "SELECT * FROM customer ";
		System.out.println("Statement Created");

		PreparedStatement stmt = conn.prepareStatement(query);
		System.out.println("Statement Created");
		ResultSet rs = null;

		rs = stmt.executeQuery(query);
		//check if result set is empty
		while(rs.next()){
			 String firstname = rs.getString("firstname");
			 String lastname = rs.getString("lastname");
			 String address = rs.getString("address");
			 String city = rs.getString("city");
			 String state = rs.getString("firstname");
			 String zip = rs.getString("zip");
			 String email= rs.getString("email");
			 String dob = rs.getString("dob");
			 String sex= rs.getString("sex");
		}

		//add query to the result set
		

		System.out.print(rs);
		//Clean-up environment
		DBUtil.close(stmt);
		DBUtil.close(rs);
		DBUtil.close(conn);

		return customer;

	}

	//U - Update customer
	public Customer editCustomer(Customer customer) throws SQLException{
		
		long customerid = customer.getCustomerid();
		
		Connection conn = DBUtil.connect();
		// Create a query
		String query = "UPDATE customer " + "set firstname = ?, lastname = ?, address = ?, city = ?, state = ?, email = ?, dob = ?, sex = ?, zipcode = ? WHERE customer_id = "+customerid+"";

		// Execute a query
		System.out.println("Preparing update statement");
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, customer.getFirstname());
		pstmt.setString(2, customer.getLastname());
		pstmt.setString(3, customer.getAddress());
		pstmt.setString(4, customer.getCity());
		pstmt.setString(5, customer.getState());
		pstmt.setString(6, customer.getEmail());
		pstmt.setString(7, customer.getDob());
		pstmt.setString(8, customer.getSex());
		pstmt.setLong(9, customer.getZip());
		pstmt.executeUpdate();
		System.out.println("Key is " + customerid);
		System.out.println("Object updated");

		
		//Clean-up environment
		DBUtil.close(pstmt);
		DBUtil.close(conn);

		return customer;
	}

	//D - Delete customer
	public Customer removeCustomer(Long customerid) throws SQLException{
		Customer customer = new Customer();
		Connection conn = DBUtil.connect();

		// Create a query
		String query = "DELETE FROM customer WHERE customer_id=" + customerid;

		// Execute a query
		System.out.println("Creating statement...");
		PreparedStatement pstmt = conn.prepareStatement(query);
		System.out.println("Statement Created");

		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			
			 String firstname = rs.getString("firstname");
			 String lastname = rs.getString("lastname");
			 String address = rs.getString("address");
			 String city = rs.getString("city");
			 String state = rs.getString("firstname");
			 Long zip = rs.getLong("zipcode");
			 String email= rs.getString("email");
			 String dob = rs.getString("dob");			 String sex= rs.getString("sex");
			 customer.setCustomerid(customerid);
			 customer.setFirstname(firstname);
			 customer.setLastname(lastname);
			 customer.setAddress(address);
			 customer.setCity(city);
			 customer.setState(state);
			 customer.setZip(zip);
			 customer.setEmail(email);
			 customer.setDob(dob);
			 customer.setSex(sex);
			 
		}
		System.out.println("ID:" + customer.getCustomerid() +", firstname: " + customer.getFirstname() + ", lastname:" + customer.getLastname() + ", address:" + customer.getAddress() 
				 + "city:" + customer.getCity() + ", state:" + customer.getState() + ", zip: " + customer.getZip() +", email: " + customer.getEmail() + ", dob: " + customer.getDob() + ", sex: " + customer.getSex());
		

		//Clean-up environment
		DBUtil.close(pstmt);
		DBUtil.close(conn);
		
		return customer;
	}
	
	public Map<Long, Customer> getCustomer(){
		return custMap;
	}
}
