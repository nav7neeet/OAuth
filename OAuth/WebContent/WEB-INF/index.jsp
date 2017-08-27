<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">
	var responseCode=${requestScope.responseCode}
	var userInformation = ${requestScope.userInformation}
	
	if (Object.keys(userInformation).length!==0 && responseCode===200)
	{
		document.write("User has authorized to access his account information. Details below - <br>");
		if ("name" in userInformation)
			document.write("name - "+userInformation["name"]+"<br>");
		if ("email" in userInformation)
			document.write("email - "+userInformation["email"]);
	}
	else
	{
		document.write("User has not authorized to access his account information or some other problem occured");
	}
</script>
</head>
<body>
</body>
</html>