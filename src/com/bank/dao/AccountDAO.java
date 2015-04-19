/**
 * Author: Niyi Odumosu
 * Project: Online Banking App
 * Purpose: Implement CRUD methods for the Account DAO layer
 */

package com.bank.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import com.capitalone.bank.model.Account;
import com.capitalone.bank.model.Customer;
import com.capitalone.bank.model.User;


public class AccountDAO {
	private static Map<Long, Account> accounts = new HashMap<Long, Account>();

	//C - Create Account
	public Account createAccount(Account account) throws SQLException{

		Connection conn = DBUtil.connect();
		
		// INSERT statement
		System.out.println("Creating statement...");
		String query = "INSERT into account(acct_type_code, balance_amount, acct_description, customer_id) values (?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		System.out.println("Statement Created");

		pstmt.setInt(1, account.getAccountType());
		pstmt.setBigDecimal(2, account.getBalance());
		pstmt.setString(3, account.getDescription());
		pstmt.setLong(4, account.getCustomerid());
		
		//execute update
		pstmt.executeUpdate();
		long key = DBUtil.getGeneratedKey(pstmt);
		account.setAccountid(key);
		
		//Clean-up environment
		DBUtil.close(pstmt);
		DBUtil.close(conn);

		return account;
	}

	//R - Retrieve Account 
	public Account getAccountByID(long accountid) throws SQLException{

		Account account = new Account();

		Connection conn = DBUtil.connect();
		ResultSet rs = null;
		// Execute a query
		System.out.println("Creating statement...");
		Statement stmt = conn.createStatement();
		System.out.println("Statement Created");

		// Query data from account table
		String query = "SELECT * FROM account WHERE Account_id=" + accountid;
		rs = stmt.executeQuery(query);
		//
		while(rs.next()){
			account.setAccountid(accountid);
			account.setAccountType(rs.getInt("acct_type_code"));		
			account.setBalance(rs.getBigDecimal("balance_amount"));
		}
		//add query to the result set
		

		System.out.println("ID:" + account.getAccountid() +", acct_type: " + account.getAccountType() + ", balance: " + account.getBalance());		
		//Clean-up environment
		
		DBUtil.close(rs);
		DBUtil.close(stmt);
		DBUtil.close(conn);

		return account;

	}
	
	//R - Retrieve Account using the customer id
		public Account getAccountByCustomerID(long customerid) throws SQLException{

			Account account = new Account();
			
			Connection conn = DBUtil.connect();
			ResultSet rs = null;
			// Execute a query
			System.out.println("Creating statement...");
			Statement stmt = conn.createStatement();

			// Query data from account table
			String query = "SELECT * FROM account WHERE customer_id =" + customerid;
			rs = stmt.executeQuery(query);
			//
			while(rs.next()){
				
				customerid = rs.getLong("customer_id");
				int acct_type = rs.getInt("acct_type_code");
				BigDecimal balance = rs.getBigDecimal("balance_amount");
				account.setAccountType(acct_type);		
				account.setBalance(balance);
				account.setCustomerid(customerid);
			}
			
			//print acount details
			System.out.println("Customer ID:" + account.getCustomerid() +", acct_type: " + account.getAccountType() + ", balance: " + account.getBalance());		
			
			//Clean-up environment
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);

			return account;

		}

	public Account getAllAccounts() throws SQLException{
		Account account = null;
		
		Connection conn = DBUtil.connect();

		// Query all data from account table
		String query = "SELECT * FROM account ";
		Statement stmt = conn.createStatement();
		System.out.println("Statement Created");
		
		ResultSet rs = null;
		//check if result set is empty
		if(!rs.next()){
			System.out.println("No data to retrieve");
		}

		//add query to the result set
		rs = stmt.executeQuery(query);

		//Clean-up environment
		DBUtil.close(rs);
		DBUtil.close(stmt);
		DBUtil.close(conn);

		return account;
	}
	
	//U - Update Account
	public Account editAccount(Account account ) throws SQLException{

		long customerid = account.getCustomerid();
		Connection conn = DBUtil.connect();

		// Create a query
		String updateBalance = "UPDATE account " + "set acct_type_code = ?, balance_amount = ?, acct_description = ?" + "where customer_id =" + customerid+"";
		
		// Execute a query
		System.out.println("Creating statement...");
		PreparedStatement pstmt = conn.prepareStatement(updateBalance);
		System.out.println("Statement Created");

		pstmt.setInt(1, account.getAccountType());
		pstmt.setBigDecimal(2, account.getBalance());
		pstmt.setString(3, account.getDescription());	
		//execute update
		System.out.println("Key is " + customerid);
		pstmt.executeUpdate();

		//Clean-up environment
		DBUtil.close(pstmt);
		DBUtil.close(conn);
		
		return account;
	}

	//D - Delete Account
	public void removeAccount(long accountid) throws SQLException{
		Account account = new Account();
		Connection conn = DBUtil.connect();
		// Create a query
		String query = "DELETE FROM account WHERE Account_id=" + accountid;
		ResultSet rs = null;

		// Execute a query
		System.out.println("Creating statement...");
			
		PreparedStatement pstmt = conn.prepareStatement(query);
		//execute update
		pstmt.executeUpdate(query);

		while(rs.next()){
			account.setAccountid(accountid);
			account.setAccountType(rs.getInt("acct_type_code"));		
			account.setBalance(rs.getBigDecimal("balance_amount"));
		}
		//Clean-up environment
		DBUtil.close(conn);
		DBUtil.close(pstmt);
		
		System.out.println("The following has been deleted ID:" + account.getAccountid() +", acct_type: " + account.getAccountType() + ", balance: " + account.getBalance());		
	}
	
	
	
	public static Map<Long, Account> getAccount(){
	    return accounts;
	  }
}
