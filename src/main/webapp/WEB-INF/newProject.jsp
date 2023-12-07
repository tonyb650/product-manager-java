<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Project Manager - New Project</title>
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
</head>
<body>
	<div class="container">
		<h1>Create a New Project </h1>
		<form:form action="/projects/create" modelAttribute="project" method="post">
			<form:label path="title" class="form-label">Project title:</form:label>
			<form:errors path="title" class="text-danger"/>
			<form:input path="title" type="text" class="form-control"/>
			<form:label path="description" class="form-label">Project description:</form:label>
			<form:errors path="description" class="text-danger"/>
			<form:textarea path="description" class="form-control" rows="4"/></textarea>
			<form:label path="dueDate" class="form-label">Due date:</form:label>
			<form:errors path="dueDate" class="text-danger"/>
			<form:input path="dueDate" type="date" class="form-control"/>
			<div class="row">
				<div class="col my-2">
					<a href="/home" class="btn btn-secondary">Cancel</a>
					<input type="submit" class="btn btn-secondary" value="Create"/>
				</div>
			</div>

		</form:form>
	</div>
</body>
</html>