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
	<title>Tickets - <c:out value="${ project.title }"/></title>
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col d-flex justify-content-between align-items-center">
				<h1>Project: <c:out value="${ project.title }"/></h1>
				<span><a href="/home">Back to dashboard</a></span>
			</div>
		</div>
		<div class="row">
			<h4>Project Lead <c:out value="${ project.teamLeader.firstName }"/></h4>
		</div>
		<div class="row">
			<div class="col">
				<form:form action="/tickets/${ project.id }/new" modelAttribute="ticket" method="post">
					<form:label path="description" class="form-label">Add a task ticket for this team:</form:label>
					<form:errors path="description" class="text-danger"/>
					<form:textarea path="description" class="form-control" rows="5"></form:textarea>
					<input type="submit" class="btn btn-secondary my-2" value="Submit Ticket"/>
				</form:form>
			</div>
		</div>
		<div class="row">
			<c:forEach var="eachTicket" items="${ project.tickets }">
				<p class="lead">
					Added By <c:out value="${ eachTicket.creator.firstName }"/> at <fmt:formatDate pattern = "hh:mm a" value="${ eachTicket.createdAt }"/> on <fmt:formatDate pattern = "MM-dd-yyyy" value="${ eachTicket.createdAt }"/>
				</p>
				<p><em>
					<c:out value="${ eachTicket.description }"></c:out>
				</em></p>
			</c:forEach>
		</div>
	</div>
</body>
</html>