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
	<title>Project Manager Dashboard</title>
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
</head>
<body>
	<div class="container">
		<div class="row align-items-center">
			<div class="col">
					<h1>Welcome, <c:out value="${ firstName }"/>!</h1>
			</div>
			<div class="col">
				<a href="/logout">Logout</a>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<h4>All Projects</h4>
			</div>
			<div class="col">
				<a href="/projects/new" class="btn btn-secondary">New Project</a>
			</div>
		</div>
		<div class="row">
			<table class="table">
				<thead>
					<tr>
						<th>Project</th>
						<th>Team Lead</th>
						<th>Due Date</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="project" items="${ nonUserProjects }">
						<tr>
							<td><a href="/projects/${ project.id }/show"> <c:out value="${ project.title }"/></a></td>
							<td><c:out value="${ project.teamLeader.firstName }"/></td>
							<td> <fmt:formatDate pattern = "yyyy-MM-dd" value="${ project.dueDate }"/></td>
							<td><a href="/projects/${ project.id }/join">Join Team</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
			<div class="row">
				<div class="col">
					<h4>Your Projects</h4>
				</div>
			</div>
		<div class="row">
			<table class="table">
				<thead>
					<tr>
						<th>Project</th>
						<th>Team Lead</th>
						<th>Due Date</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="project" items="${ userProjects }">
						<tr>
							<td><a href="/projects/${ project.id }/show"> <c:out value="${ project.title }"/></a></td>
							<td><c:out value="${ project.teamLeader.firstName }"/></td>
							<td> <fmt:formatDate pattern = "yyyy-MM-dd" value="${ project.dueDate }"/></td>
							<td>
								<c:if test="${ project.teamLeader.id == id }">
									<a href="/projects/${ project.id }/update">Edit</a>
								</c:if>
								<c:if test="${ project.teamLeader.id != id }">
									<a href="/projects/${ project.id }/leave">Leave team</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		
	</div>
</body>
</html>