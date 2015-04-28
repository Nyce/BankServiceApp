package com.bank.dao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.bank.model.*;


public class DAOClient {
	private static Connection conn = null;

	private static PreparedStatement pstmt = null;


	public static Customer createMockCustomer() {
		Customer customer = new Customer();

		customer.setFirstname("Yomi");
		customer.setLastname("Odumosu");
		customer.setAddress("930 mather Drive");
		customer.setCity("bear");
		customer.setState("DE");
		customer.setZip(98903);
		customer.setSex("M");
		customer.setEmail("niyi@email.com");
		customer.setDob("1992-05-31");


		return customer;
	}

	public static Customer updateMockCustomer(Customer customer) {

		customer.setFirstname("Brian");
		customer.setLastname("Westbrook");
		customer.setAddress("105 Main St");
		customer.setCity("Philadelphia");
		customer.setState("PA");
		customer.setZip(98903);
		customer.setSex("M");
		customer.setEmail("westbrook@email.com");
		customer.setDob("1959-02-17");

		return customer;
	}

	public static User createMockUser(Customer customer) {
		User user = new User();

		user.setUsername("Yomi");
		user.setPassword("Flow37#&");
		user.setCustomerid(customer.getCustomerid());
		return user;
	}
	public static User updateMockUser(Customer customer, User user) {

		user.setUsername("WestBrook");
		user.setPassword("pass");
		user.setCustomerid(customer.getCustomerid());
		return user;
	}

	public static Account createMockAccount(Customer customer) {
		Account account = new Account();

		String bal = "300";
		BigDecimal money = convertBalance(bal);

		account.setAccountType(100);
		account.setBalance(money);
		account.setDescription("Checking acct code is 100. Savings acct code is 200");
		account.setCustomerid(customer.getCustomerid());
		return account;
	}

	public static BigDecimal convertBalance(String bal) {
		 
		BigDecimal money = new BigDecimal(bal.replaceAll(",", ""));
		return money;
	}

	public static Account updateMockAccount(Account account, Customer customer) {
		String bal = "300";

		account.setAccountType(200);
		account.setBalance(DAOClient.convertBalance(bal));
		account.setDescription("Checking acct code is 100. Savings acct code is 200");
		account.setCustomerid(customer.getCustomerid());
		return account;
	}

	public static Transaction createMockTxn(Account account) {
		Transaction transaction = new Transaction();
		String amt = "9,780.93";
		BigDecimal money = new BigDecimal(amt.replaceAll(",", ""));

		transaction.setTxnType(300);
		transaction.setAmount(money);
		transaction.setDescription("Deposit acct code is 300. Withdrawal acct code is 400");
		transaction.setAccountid(account.getAccountid());
		return transaction;
	}
	public static Transaction updateMockTxn(Account account, Transaction transaction) {
		String amt = "5,780.93";
		BigDecimal money = new BigDecimal(amt.replaceAll(",", ""));

		transaction.setTxnType(400);
		transaction.setAmount(money);
		transaction.setDescription("Deposit acct code is 300. Withdrawal acct code is 400");
		transaction.setAccountid(account.getAccountid());
		return transaction;
	}

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub


		CustomerDAO cd = new CustomerDAO();
		UserDAO ud = new UserDAO();
		TransactionDAO td = new TransactionDAO();
		AccountDAO ad = new AccountDAO();

		Customer customer = createMockCustomer();
		cd.createCustomer(customer);

		User user = createMockUser(customer);		
		ud.createUser(user);

		Account account = createMockAccount(customer);
		ad.createAccount(account);

		Transaction txn = createMockTxn(account);
		td.createTransaction(txn);
//
//		System.out.println("Time to update the customer");
//
//		customer = updateMockCustomer(customer);
//		cd.editCustomer(customer);
//
//		user = updateMockUser(customer, user);		
//		ud.editUser(user);
//
//		account = updateMockAccount(account, customer);
//		ad.editAccount(account);
//
//		txn = updateMockTxn(account, txn);
//		td.editTransaction(txn);

//		Customer customer = new Customer();
//		cd.getCustomerByID(10065);
//		ud.getUserByID(10025);
//		td.getTransactionByID(10025);
//		ad.getAccount(100025);
		
	}

}
