package com.bank.test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.SQLException;




import org.junit.Test;

import com.bank.dao.AccountDAO;
import com.bank.dao.CustomerDAO;
import com.bank.dao.TransactionDAO;
import com.bank.dao.UserDAO;
import com.capitalone.bank.model.Account;
import com.capitalone.bank.model.Customer;
import com.capitalone.bank.model.Transaction;
import com.capitalone.bank.model.User;


public class MockitoTest {


	@Mock
	//Mock Objects are declared
	CustomerDAO customer;
	Customer cust = new Customer();
	UserDAO userD;
	User user = new User();;
	AccountDAO account;
	Account acct =  new Account();;
	TransactionDAO transaction;
	Transaction txn = new Transaction();

	//Mock objects are instantiated
	protected void setUp() throws Exception {
		customer = Mockito.mock(CustomerDAO.class);
		userD =  Mockito.mock(UserDAO.class);
		account = Mockito.mock(AccountDAO.class);
		transaction = Mockito.mock(TransactionDAO.class);
	}

	


	@Test
	public void testMockCustomer() throws SQLException  {
		//Use spy to call real methods with the mock object
		
		//		cust = new Customer();

		cust.setFirstname("James");
		cust.setLastname("Evans");
		cust.setAddress("105 South St");
		cust.setCity("Philadelphia");
		cust.setState("PA");
		cust.setZip(98903);
		cust.setSex("M");
		cust.setEmail("evans@email.com");
		cust.setDob("1980-02-17");
		Mockito.when(customer.createCustomer(cust)).thenReturn(cust);


	}


	@Test
	public void testMockUser() throws SQLException {
		//Use spy to call real methods with the mock object
		userD = Mockito.spy(new UserDAO());
//		user = new User();

		user.setUsername("JEvan");
		user.setPassword("password");
		user.setCustomerid(10055);
		Mockito.when(userD.createUser(user)).thenReturn(user);

	}

	@Test
	public void  testMockAccount() throws SQLException {
		//Use spy to call real methods with the mock object
		account = Mockito.spy(new AccountDAO());

		String bal = "3,409.93";
		BigDecimal money = new BigDecimal(bal.replaceAll(",", ""));

		acct.setAccountType(200);
		acct.setBalance(money);
		acct.setDescription("Checking acct code is 100. Savings acct code is 200");
		acct.setCustomerid(10055);
		Mockito.when(account.createAccount(acct)).thenReturn(acct);
	}

	@Test
	public void  testMockTxn() throws SQLException {
		//Use spy to call real methods with the mock object
		transaction = Mockito.spy(new TransactionDAO());
//		txn = new Transaction();
		
		String bal = "780.93";
		BigDecimal money = new BigDecimal(bal.replaceAll(",", ""));

		txn.setTxnType(300);
		txn.setAmount(money);
		txn.setDescription("Deposit acct code is 300. Withdrawal acct code is 400");
		txn.setAccountid(100015);
		Mockito.when(transaction.createTransaction(txn)).thenReturn(txn);
	}
	//
	//
	//
}
