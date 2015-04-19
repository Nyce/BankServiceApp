/**
 * Author: Niyi Odumosu
 * Project: Online Banking App
 * Purpose: Implement CRUD methods for the Transaction DAO layer
 */
package com.bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.capitalone.bank.model.Account;
import com.capitalone.bank.model.Customer;
import com.capitalone.bank.model.Transaction;

public class TransactionDAO {
	
	private static Map<Long, Transaction> txns = new HashMap<Long, Transaction>();

	
	public Transaction createTransaction(Transaction txn) throws SQLException{

		Connection conn = DBUtil.connect();
		// Execute a query
		System.out.println("Creating statement...");

		System.out.println("Statement Created");

		// Create a query
		String query = "INSERT into transaction(txn_type_code, txn_amount, txn_description, account_id)" + "values (?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		
		
		pstmt.setInt(1, txn.getTxnType());
		pstmt.setBigDecimal(2, txn.getAmount());
		pstmt.setString(3, txn.getDescription());
		pstmt.setLong(4, txn.getAccountid());
		
		//execute update
		pstmt.executeUpdate();
		long key = DBUtil.getGeneratedKey(pstmt);
		txn.setTxnid(key);
		
		//Clean-up environment
		DBUtil.close(pstmt);
		DBUtil.close(conn);
		
		return txn;
	}

	//R - Retrieve Transaction 
	public Transaction getTransactionByID(long transactionid) throws SQLException{

		Transaction txn = new Transaction();
		
		Connection conn = DBUtil.connect();

		ResultSet rs = null;
		// Execute a query
		System.out.println("Creating statement...");

		System.out.println("Statement Created");

		// Extract data from result set
		String query = "SELECT * FROM transaction WHERE txn_id=" + transactionid;

		PreparedStatement stmt = conn.prepareStatement(query);
		
		//add query to the result set
		rs = stmt.executeQuery();
		//check if result set is empty
		while(rs.next()){
			txn.setTxnid(transactionid);
			txn.setTxnType(rs.getInt("txn_type_code"));		
			txn.setAmount(rs.getBigDecimal("txn_amount"));	
		}
		
		System.out.println("ID:" + txn.getTxnid() +", txn_type: " + txn.getTxnType() + ", amount: " + txn.getAmount());		
		
		//Clean-up environment
		DBUtil.close(rs);
		DBUtil.close(stmt);
		DBUtil.close(conn);

		return txn;

	}
	
	public Transaction getAllTransactions() throws SQLException{
		Transaction transaction = null;
		
		Connection conn = DBUtil.connect();

		// Extract data from result set
		String query = "SELECT * FROM transaction ";
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

		return transaction;
	}

	//U - Update Transaction
	public Transaction editTransaction(Transaction txn) throws SQLException{

		long acct_id = txn.getAccountid();

		Connection conn = DBUtil.connect();
		// Create a query
		String query = "UPDATE transaction " + "set txn_type_code = ?, txn_amount = ?, txn_description = ? where account_id = " + acct_id +"" ;
		
		// Execute a query
		System.out.println("Creating statement...");
		PreparedStatement pstmt = conn.prepareStatement(query);
		System.out.println("Statement Created");

		pstmt.setInt(1, txn.getTxnType());
		pstmt.setBigDecimal(2, txn.getAmount());
		pstmt.setString(3, txn.getDescription());

		//execute update
		System.out.println("Key is " + acct_id);
		pstmt.executeUpdate();

		//Clean-up environment
		DBUtil.close(pstmt);
		DBUtil.close(conn);
		
		return txn;
	}

	//D - Delete Transaction
	public void removeTransaction(long transactionid) throws SQLException{
		Transaction txn = new Transaction();

		Connection conn = DBUtil.connect();

			// Create a query
			String query = "DELETE FROM transaction WHERE txn_id=" + transactionid;
		ResultSet rs = null;

			// Execute a query
			System.out.println("Creating statement...");
			PreparedStatement pstmt = conn.prepareStatement(query);
			while(rs.next()){
				txn.setTxnid(transactionid);
				txn.setTxnType(rs.getInt("txn_type_code"));		
				txn.setAmount(rs.getBigDecimal("txn_amount"));	
			}
			
			System.out.println("ID:" + txn.getTxnid() +", txn_type: " + txn.getTxnType() + ", amount: " + txn.getAmount());		
			
			//Clean-up environment
			DBUtil.close(pstmt);
			DBUtil.close(conn);
					
	}
	
	public static Map<Long, Transaction> getTxn(){
	    return txns;
	  }
	
	
}
