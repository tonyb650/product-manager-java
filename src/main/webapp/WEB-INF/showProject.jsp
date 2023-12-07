<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Details - <c:out value="${ project.title }"/></title>
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">
				<h1>Project Details</h1>
				<a href="/home">Back to dashboard</a>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<table class="table">
					<tr>
						<th>Project</th>
						<td><c:out value="${ project.title }"/></td>
					</tr>
					<tr>
						<th>Description</th>
						<td><c:out value="${ project.description }"/></td>
					</tr>
					<tr>
						<th>Due Date:</th>
						<td>
							<fmt:formatDate pattern = "yyyy-MM-dd" value="${ project.dueDate }"/>
						</td>
					</tr>
					<tr>
						<th>Actions</th>
						<td class="d-flex px-2">
							<a href="/projects/${ project.id }/tickets" class="btn btn-secondary">See Tasks</a>
							<form action="/projects/delete" method="post">
								<input type="hidden" name="_method" value="delete"/>
								<input type="hidden" name="id" value="${ project.id }" />
								<input type="submit" class="btn btn-secondary" value="Delete"/>
							</form>
						</td>
					</tr>
				</table>
			</div>
		</div>
	
	</div>
</body>
</html>