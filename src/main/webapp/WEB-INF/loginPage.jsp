<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Project Manager Login</title>
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
</head>
<body>
	<div class="container">
		<div class="row border-bottom">
			<h1>Project Manager</h1>
			<h3 class="text-info">A place for teams to manage projects</h3>
		</div>
		<div class="row">
			<div class="col my-2">
				<h2>Register</h2>
				<form:form action="/register" modelAttribute="newUser" method="post" >
					<form:label path="firstName" class="form-label">First Name</form:label>
					<form:errors path="firstName" class="text-danger"/>
					<form:input path="firstName" class="form-control" type="text"/>
					<form:label path="lastName" class="form-label">Last Name</form:label>
					<form:errors path="lastName" class="text-danger"/>
					<form:input path="lastName" class="form-control" type="text"/>
					<form:label path="email" class="form-label">Email</form:label>
					<form:errors path="email" class="text-danger"/>
					<form:input path="email" class="form-control" type="text"/>
					<form:label path="password" class="form-label">Password</form:label>
					<form:errors path="password" class="text-danger"/>
					<form:input path="password" class="form-control" type="text"/>
					<form:label path="confirmPassword" class="form-label">Confirm Password</form:label>
					<form:errors path="confirmPassword" class="text-danger"/>
					<form:input path="confirmPassword" class="form-control" type="text"/>
					<input type="Submit" class="btn btn-info my-2"  value="Register"/>
				</form:form>
			</div>
			<div class="col">
				<h2>Login</h2>
				<form:form action="/login" modelAttribute="newLogin" method="post">
					<form:label path="email" class="form-label">Email</form:label>
					<form:errors path="email" class="text-danger"/>
					<form:input path="email" class="form-control" type="text"/>
					<form:label path="password" class="form-label">Password</form:label>
					<form:errors path="password" class="text-danger"/>
					<form:input path="password" class="form-control" type="text"/>
					<input type="Submit" class="btn btn-info my-2"  value="Login"/>
				</form:form>
			
			</div>
		</div>
	</div>
</body>
</html>