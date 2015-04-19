<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!doctype html>
<html>

<script type="text/javascript" src="js/validation.js">
	function validation() {
		//first name
		var fn = document.forms["register"]["first name"].value;
		if (fn == '' || fn == null) {
			alert("You must type in a first name");
			return false;
		}

		//last name
		var ln = document.forms["register"]["last name"].value;
		if (ln == '' || ln == null) {
			alert("You must type in a last name");
			return false;
		}

		//Home Address
		var address = document.forms["register"]["home address"].value;
		if (address == '' || address == null) {
			alert("You must type in a home address");
			return false;
		}

		//City
		var city = document.forms["register"]["City"].value;
		if (city == '' || city == null) {
			alert("You must type in a city");
			return false;
		}

		//State
		var state = document.forms["register"]["State"].value;
		if (state == '' || state == null) {
			alert("You must choose a state");
			return false;
		}

		//Zip Code Validation
		var zipcode_regex = /^\d{5}$/;
		var zip = document.forms["register"]["Zip Code"].value;
		if (zip == '' || zip == null) {
			alert("You must add your zip code");
			return false;
		}
		//
		if (zipcode_regex.test($.trim($('Zip Code').val())) == false) {
			alert('invalid zipcode');
			return false
		}

		//Email Validation
		var email = document.forms["register"]["email address"].value;
		if (email == '' || email == null) {
			alert("You must type in a vaild email address");
			return false;
		}

		//username Validation
		var unr = document.forms["register"]["username"].value;
		if (unr == "" || unr == null) {
			alert("You must type in a user name");
			return false;
		}

		//password Validation
		var pwr = document.forms["register"]["password"].value;
		if (pwr == "" || pwr == null) {
			alert("You must type in a user name");
			return false;
		}

		//balance validation
		var bal = document.forms["register"]["balance"].value;
		if (bal == "" || bal == null) {
			alert("You must type in a balance");
			return false;
		}

		document.getElementsByName("nameHid")[0].value = document.forms["register"]["username"].value;
		document.getElementsByName("balanceHid")[0].value = document.forms["register"]["balance"].value;
		return true;
	}
</script>
<head>

<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="css/login.css">
<title>Create a New Account</title>
</head>
<body>
	<h2>Hello, please create a new account:</h2>
	<br>
	<br>
	<form name="register" action="openAccount" method=post
		onSubmit="return validation()">
		<p>
			<strong>Please Enter Your First Name: </strong>
		</p>
		<input type="text" name="first name">
		<p>
			<strong>Please Enter Your Last Name: </strong>
		</p>
		<input type="text" name="last name">
		<p>
			<strong>Please Enter Home Address: </strong>
		</p>
		<input type="text" name="home address">
		<p>
			<strong>Please Enter Your City: </strong>
		</p>
		<input type="text" name="City">
		<p>
			<strong>Please Choose Your State: </strong>
		</p>
		<select name="state" id="state">
			<option value="AL">Alabama</option>
			<option value="AK">Alaska</option>
			<option value="AZ">Arizona</option>
			<option value="AR">Arkansas</option>
			<option value="CA">California</option>
			<option value="CO">Colorado</option>
			<option value="CT">Connecticut</option>
			<option value="DE">Delaware</option>
			<option value="DC">District of Columbia</option>
			<option value="FL">Florida</option>
			<option value="GA">Georgia</option>
			<option value="HI">Hawaii</option>
			<option value="ID">Idaho</option>
			<option value="IL">Illinois</option>
			<option value="IN">Indiana</option>
			<option value="IA">Iowa</option>
			<option value="KS">Kansas</option>
			<option value="KY">Kentucky</option>
			<option value="LA">Louisiana</option>
			<option value="ME">Maine</option>
			<option value="MD">Maryland</option>
			<option value="MA">Massachusetts</option>
			<option value="MI">Michigan</option>
			<option value="MN">Minnesota</option>
			<option value="MS">Mississippi</option>
			<option value="MO">Missouri</option>
			<option value="MT">Montana</option>
			<option value="NE">Nebraska</option>
			<option value="NV">Nevada</option>
			<option value="NH">New Hampshire</option>
			<option value="NJ">New Jersey</option>
			<option value="NM">New Mexico</option>
			<option value="NY">New York</option>
			<option value="NC">North Carolina</option>
			<option value="ND">North Dakota</option>
			<option value="OH">Ohio</option>
			<option value="OK">Oklahoma</option>
			<option value="OR">Oregon</option>
			<option value="PA">Pennsylvania</option>
			<option value="RI">Rhode Island</option>
			<option value="SC">South Carolina</option>
			<option value="SD">South Dakota</option>
			<option value="TN">Tennessee</option>
			<option value="TX">Texas</option>
			<option value="UT">Utah</option>
			<option value="VT">Vermont</option>
			<option value="VA">Virginia</option>
			<option value="WA">Washington</option>
			<option value="WV">West Virginia</option>
			<option value="WI">Wisconsin</option>
			<option value="WY">Wyoming</option>
		</select>
		<p>
			<strong>Please Enter Your Zip Code: </strong>
		</p>
		<input maxlength="5" name="Zip Code">
		<p>
			<strong>Please Enter Your Email Address: </strong>
		</p>
		<input type="email" name="email address">
		<p>
			<strong>Please Enter Your Username: </strong>
		</p>
		<input type="text" name="username">
		<p>
		<p>
			<strong>Please Enter Your Password: </strong>
		</p>
		<input type="password" name="password">
		<p>
			<strong>Enter Your Current Balance: </strong>
		</p>
		<input type="text" name="balance"> <input type="hidden"
			name="balanceHid" /> <input type="hidden" name="nameHid" />
		<p>
			<input type="submit" value="Submit"> <input type="reset"
				value="Reset">
	</form>
</body>
</html>