<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %> 


<html>
	<head>
		<title>New blog</title>
		<link rel="stylesheet" type="text/css" href="/blog/css/mystyle.css">
	</head>
	<body>
		<c:choose>
			<c:when test="<%= session.getAttribute(\"current.user.id\") instanceof Long %>">
				<p>
					<%= session.getAttribute("current.user.fn")%> <%= session.getAttribute("current.user.ln")%>,
					<a href="/blog/servleti/logout">odjava</a>
				</p>
			</c:when>
		</c:choose>
		
		<c:choose>
		<c:when test="${mode == \"new\" }">
			<h1>Add new blog entry</h1>
			<form action="/blog/servleti/author/${autor}/new" method="post">
		</c:when>
		<c:otherwise>
			<h1>Edit blog entry</h1>
			<form action="/blog/servleti/author/${autor}/edit" method="post">
		</c:otherwise>
		</c:choose>
		<table>
			<tr>
				<td>ID</td> 
			 	<td>
			 		${form.id}<br>
			  	</td>
			  	<c:if test="${mode == \"edit\"}">
			  		<input type="hidden" name="id" value='<c:out value="${form.id}"></c:out>'>
			  	</c:if>
			</tr>
			<tr>
			 	<td>Title</td> 
			 	<td><input type="text" name="title" value='<c:out value="${form.title}"></c:out>' size="70"><br>
				  	<c:if test="${form.hasError('title')}">
				  		<div><c:out value="${form.fetchError('title')}"></c:out> </div>
				  	</c:if>
			  	</td>
			</tr>
			<tr>
				<td>Text</td>
				<td>
					<textarea name="text" rows="16" cols="70">${form.text}</textarea>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="submit" name="metoda-Blog" value="Save">
					<input type="submit" name="metoda-Blog" value="Cancel">
				</td>
			</tr>
		</table>
		</form>
	</body>
</html>