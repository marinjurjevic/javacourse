<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %> 

<%
	Object o = session.getAttribute("error");
	Object id = session.getAttribute("current.user.id");
%>

<html>
	<head>
		<title>Login</title>
		<link rel="stylesheet" type="text/css" href="/blog/css/mystyle.css">
	</head>
   <body> 
		<c:choose>
			<c:when test="<%= session.getAttribute(\"current.user.id\") instanceof Long %>">
				<p class="loginout">
					<%= session.getAttribute("current.user.fn")%> <%= session.getAttribute("current.user.ln")%>,
					<a href="/blog/servleti/logout">logout</a>
				</p>
			</c:when>
		</c:choose>
		
   		<c:if test="<%= !(id instanceof Long) %>">
	   		<h2>LOGIN</h2>
			<p class="error">
				<c:if test="<%= o instanceof Boolean %>">
					Username or password is incorrect!
				</c:if>
			</p>
			<form action="/blog/servleti/main" method="post">
				<table>
				<tr>
					<td>
						Username: 
					</td>
					<td>
						<input type="text" name="username"
						<c:choose>
							<c:when test="<%= o instanceof Boolean %>">
			  					value="<%= session.getAttribute("current.user.nick") %>"
							</c:when>
							<c:otherwise>
								value=""
							 </c:otherwise>
						</c:choose>
						size="10">
					</td>
				</tr>
				<tr>
					<td>
						Password: 
					</td>
					<td>
						<input type="password" name="password" value="" size="10">
					</td>
				</tr>
				<tr>
					<td>
					</td>
					<td>
						<input type="submit" name="metoda" value="Login">
					</td>
				</tr>
				</table>
				</form>
		
		<br/>
   		<p>If you want to write your own blog, please <a href="/blog/servleti/register">register</a> <br/>
   		For now, you can see blogs of other users
   		</p>
   		</c:if>
   		
   		
   		<p>List of bloggers:</p>
   		<ul>
   		<c:forEach var="u" items="${users}">
   			<a href="/blog/servleti/author/${u.nick}">${u.nick}</a><br/>
		</c:forEach>
		</ul>
   </body>
</html>