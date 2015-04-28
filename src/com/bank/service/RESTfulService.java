package com.bank.service;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import com.bank.dao.AccountDAO;
import com.bank.dao.CustomerDAO;
import com.bank.dao.TransactionDAO;
import com.bank.dao.UserDAO;
import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.model.Transaction;
import com.bank.model.User;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
@Path("/service")
public class RESTfulService extends HttpServlet  { 

	@Context
	UriInfo uriInfo;
	@Context
	HttpServletRequest request;
	HttpServletResponse response;

	private CustomerDAO customerDAO;
	private UserDAO userDAO;
	private AccountDAO acctDAO;
	private TransactionDAO txnDAO;
	final static Logger logger = Logger.getLogger(RESTfulService.class);

	public RESTfulService() {
		this.customerDAO = new CustomerDAO();
		this.userDAO = new UserDAO();
		this.acctDAO = new AccountDAO();
		this.txnDAO = new TransactionDAO();


	}

	@Path("create")
	@POST
	@Consumes(MediaType.WILDCARD)
	@Produces(MediaType.TEXT_HTML)
	public String openAccount(String newAccountDetails) throws SQLException, JSONException{

		//open new account properties
		JSONTokener token = new JSONTokener(newAccountDetails);
		JSONObject custObj = new JSONObject(token);
		String firstname = (String)custObj.get("firstname");
		String lastname = (String)custObj.get("lastname");
		String address = (String)custObj.get("address");
		String city = (String)custObj.get("city");
		String state = (String)custObj.get("state");
		String zip = (String)custObj.get("zip");
		String dob = (String)custObj.get("dob");
		String email = (String)custObj.get("email");
		String sex = (String)custObj.get("sex");
		Integer accountType = (Integer)custObj.get("accountType");
		String acct_description = (String)custObj.get("acct_description");
		String txn_description = (String)custObj.get("txn_description");
		Integer balance = (Integer)custObj.get("balance");
		Integer amount = (Integer)custObj.get("amount");
		Integer txnType = (Integer)custObj.get("txnType");
		String username = (String)custObj.get("username");
		String password = (String)custObj.get("password");

		//create gson object to convert jsonObject to Model Object
		Gson gson = new Gson();

		//customer properties
		custObj.put("firstname", firstname);
		custObj.put("lastname", lastname);
		custObj.put("address", address);
		custObj.put("city", city);
		custObj.put("state", state);
		custObj.put("zip", zip);
		custObj.put("dob", dob);
		custObj.put("email", email);
		custObj.put("sex", sex);

		Customer customer = gson.fromJson(custObj.toString(), Customer.class);  
		customerDAO.createCustomer(customer);

		//use user properties to create user credentials
		JSONTokener token1 = new JSONTokener(newAccountDetails);
		JSONObject userObj = new JSONObject(token1);

		userObj.put("username", username);
		userObj.put("password", password);
		System.out.println(customer.getCustomerid());

		//create user object from json 
		User user = gson.fromJson(userObj.toString(), User.class);

		user.setCustomerid(customer.getCustomerid());
		userDAO.createUser(user);

		//use account properties to create account credentials
		JSONTokener token2 = new JSONTokener(newAccountDetails);
		JSONObject accountObj = new JSONObject(token2);

		accountObj.put("accountType",accountType);
		accountObj.put("balance", balance);
		Account account = gson.fromJson(accountObj.toString(), Account.class);
		account.setCustomerid(customer.getCustomerid());
		account.setDescription(acct_description);
		acctDAO.createAccount(account);
		System.out.println(account);

		//use transaction properties to create transaction credentials
		JSONTokener token3 = new JSONTokener(newAccountDetails);
		JSONObject txnObj = new JSONObject(token3);

		txnObj.put("amount", amount);
		txnObj.put("txnType",txnType);
		Transaction txn = gson.fromJson(txnObj.toString(), Transaction.class);
		txn.setAccountid(account.getAccountid());
		txn.setDescription(txn_description);
		txnDAO.createTransaction(txn);

		logger.info("New customer "  + customer.getFirstname() + ", " + customer.getLastname() + " has been created");
		return newAccountDetails; 
	}


	@Path("/customer/{id}")
	@GET
	//	@Produces({MediaType.APPLICATION_JSON})
	public Customer getCustomer(@PathParam("id")long customerid) throws SQLException{
		Customer customer = new Customer();
		CustomerDAO cd = new CustomerDAO();
		cd.createCustomer(customer);
		cd.getCustomerByID(customerid);
		return customer;
	}
	@Path("/user/{id}")
	@GET
	//	@Produces({MediaType.APPLICATION_JSON})
	public User getUser(@PathParam("id")Long Userid) throws SQLException{
		User User = new User();
		UserDAO udao = new UserDAO();
		User = udao.getUserByID(Userid);
		return User;
	}
	@Path("/account/{id}")
	@GET
	//	@Produces({MediaType.APPLICATION_JSON})
	public Account getAccount(@PathParam("id")Long customerid) throws SQLException, JSONException{

		Account account = new Account();
		AccountDAO adao = new AccountDAO();
		account = adao.getAccountByCustomerID(customerid);

		return account;
	}


	@Path("/bal")
	@POST
	@Consumes(MediaType.WILDCARD)
	@Produces(MediaType.TEXT_HTML)
	public String getBalance(String customerid) throws SQLException, JSONException{
		JSONTokener token = new JSONTokener(customerid);
		JSONObject json = new JSONObject(token);
		Integer customerID = null;
		try{
			customerID = (Integer) json.get("customerid");
		}
		catch(JSONException je){
			logger.error("Json key  is not found " + je.getMessage());
		}
		Account account = new Account();
		AccountDAO adao = new AccountDAO();
		logger.debug("Customer id is: " + customerID);
		account.setCustomerid(customerID);
		logger.debug("Customer id is: " + account.getCustomerid());
		account = getAccount(account.getCustomerid());

		json.put("balance", account.getBalance().toString());
		logger.debug("Customer id is: " + account.getBalance().toString());
		json.put("accountNumber", account.getCustomerid());
		json.put("acctType", account.getAccountType());

		String acctObject = json.toString();
		return acctObject;
	}

	@Path("/txn/{id}")
	@GET
	//	@Produces({ MediaType.APPLICATION_JSON})
	public Transaction getTransaction(@PathParam("id")Long Transactionid) throws SQLException{
		Transaction Transaction = new Transaction();
		TransactionDAO td = new TransactionDAO();
		Transaction = td.getTransactionByID(Transactionid);
		return Transaction;
	}


	// Return the list of Users for applications
	@Path("/users")
	@GET
	//	@Produces({ MediaType.APPLICATION_JSON })
	public List<User> getUsers(String username, String password) throws SQLException {
		List<User> Users = new ArrayList<User>();
		Users.addAll(UserDAO.getAllUsers().values());
		return Users;
	}

	public List<User> getUserss(String username, String password) throws SQLException {
		List<User> Users = new ArrayList<User>();
		Users.addAll(UserDAO.getAllUsers().values());
		return Users;
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.WILDCARD)
	@Produces(MediaType.TEXT_HTML)
	public String getAccount(String user
			) throws SQLException, ServletException, IOException, JSONException {
		JSONTokener token = new JSONTokener(user);
		JSONObject json = new JSONObject(token);

		String message = "";

		String username = (String) json.get("username");
		String password = (String) json.get("password");

		Boolean exception = false;
		User userCredentials = userDAO.getUserByUN(username);
		
		//if un and pw are not in db, it returns a null string to the client
		if(!username.equals(userCredentials.getUsername()) ||  !password.equals(userCredentials.getPassword()) ){
			logger.error("Invalid username or password. Customer id is: " + userCredentials.getCustomerid());
			return message;
		}


		//checks if user's customer id exists in the db 
		if(userCredentials.getCustomerid() == null){
			logger.error("User does not exists. Customer is null: " + userCredentials.getCustomerid());
			exception = true;
			return message;
		}


		Account account =  new Account();
		logger.debug("Customer id is: " + userCredentials.getCustomerid());
		account.setCustomerid(userCredentials.getCustomerid());
		logger.debug("Customer id is: " + account.getCustomerid());
		account = getAccount(account.getCustomerid());

		json.put("balance", account.getBalance().toString());
		json.put("accountNumber", account.getCustomerid());
		json.put("acctType", account.getAccountType());

		String acctObject = json.toString();
		return acctObject;

	}
	@POST
	@Path("/username")
	@Consumes(MediaType.WILDCARD)
	@Produces(MediaType.TEXT_HTML)
	public String checkUsername(String user
			) throws SQLException, ServletException, IOException, JSONException {
		JSONTokener token = new JSONTokener(user);
		JSONObject json = new JSONObject(token);
		
		String message = "";
		
		String username = (String) json.get("username");
		String password = (String) json.get("password");
		User userCredentials = userDAO.getUserByUN(username);
		
		//checks if user's customer id exists in the db 
		if(userCredentials.getUsername() != null){
			logger.error("User already exists. Customerid is: " + userCredentials.getCustomerid());
			message="exists";
			return message;
		}
		
		//checks if user's customer id exists in the db 
		if(userCredentials.getCustomerid() == null){
			logger.error("User does not exists. Customer is null: " + userCredentials.getCustomerid());
			return message;
		}
		
		
		Account account =  new Account();
		logger.debug("Customer id is: " + userCredentials.getCustomerid());
		account.setCustomerid(userCredentials.getCustomerid());
		logger.debug("Customer id is: " + account.getCustomerid());
		account = getAccount(account.getCustomerid());
		
		json.put("balance", account.getBalance().toString());
		json.put("accountNumber", account.getCustomerid());
		json.put("acctType", account.getAccountType());
		
		String acctObject = json.toString();
		return acctObject;
		
	}


}

