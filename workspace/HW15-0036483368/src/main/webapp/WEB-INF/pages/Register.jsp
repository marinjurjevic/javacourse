<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %> 

<html>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<head>
		<title>Register</title>
		<link rel="stylesheet" type="text/css" href="/blog/css/mystyle.css">
	</head>
   
   <body>
	<p class="loginout">
	<c:choose>
		<c:when test="<%= session.getAttribute(\"current.user.id\") instanceof Long %>">
				<%= session.getAttribute("current.user.fn")%> <%= session.getAttribute("current.user.ln")%>,
				<a href="/blog/servleti/logout">odjava</a>
			
		</c:when>
		<c:otherwise>
				<a href="/blog/servleti/main">login</a>
		</c:otherwise>
	</c:choose>
	</p>
	<h1>Register</h1>
	<form action="/blog/servleti/register" method="post">
		<table>
			<tr>
			 	<td>First Name</td> 
			 	<td><input type="text" name="firstName" value='<c:out value="${form.firstName}"></c:out>' size="50"><br>
				  	<c:if test="${form.hasError('firstName')}">
				  		<div class="error"><c:out value="${form.fetchError('firstName')}"></c:out> </div>
				  	</c:if>
			  	</td>
			</tr>
			<tr>
			 	<td>Last Name</td> 
			 	<td><input type="text" name="lastName" value='<c:out value="${form.lastName}"></c:out>' size="50"><br>
				  	<c:if test="${form.hasError('lastName')}">
				  		<div class="error"><c:out value="${form.fetchError('lastName')}"></c:out> </div>
				  	</c:if>
			  	</td>
			</tr>
			<tr>
			 	<td>E-Mail</td> 
			 	<td><input type="text" name="email" value='<c:out value="${form.email}"></c:out>' size="50"><br>
				  	<c:if test="${form.hasError('email')}">
				  		<div class="error"><c:out value="${form.fetchError('email')}"></c:out> </div>
				  	</c:if>
			  	</td>
			</tr>
			<tr>
			 	<td>Nick</td> 
			 	<td><input type="text" name="nick" value='<c:out value="${form.nick}"></c:out>' size="50"><br>
				  	<c:if test="${form.hasError('nick')}">
				  		<div class="error"><c:out value="${form.fetchError('nick')}"></c:out> </div>
				  	</c:if>
			  	</td>
			</tr>
			<tr>
			 	<td>Password</td> 
			 	<td><input type="password" name="password" value='<c:out value="${form.password}"></c:out>' size="50"><br>
				  	<c:if test="${form.hasError('password')}">
				  		<div class="error"><c:out value="${form.fetchError('password')}"></c:out> </div>
				  	</c:if>
			  	</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="submit" name="metoda" value="Register">
					<input type="submit" name="metoda" value="Cancel">
				</td>
			</tr>
		</table>
	</form>
	
   </body>
</html>