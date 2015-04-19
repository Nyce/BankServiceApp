<html>
<head>
<title>Online Banking</title>
<style type="text/css">
body {
	background-image: url('http://crunchify.com/bg.png');
}
</style>
</head>
<body>
	<br>
	<div style="text-align:center">
		<h2>
			Hello <%=request.getAttribute("username")%><br> <br>
		</h2>
		<form name="register" action="openAccount" method=post>
		<p><%=request.getAttribute("balance")%></p>
		</form>
	</div>
</body>
</html>