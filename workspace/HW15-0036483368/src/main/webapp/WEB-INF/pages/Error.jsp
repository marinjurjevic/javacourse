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
				<a href="/blog/servleti/logout">logout</a>
			
		</c:when>
		<c:otherwise>
				<a href="/blog/servleti/main">login</a>
		</c:otherwise>
	</c:choose>
	</p>
	<h1>Error</h1>
	<p><a href="/blog/servleti/main">Povratak na početnu</a></p>
   </body>
</html>