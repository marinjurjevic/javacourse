<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %> 

<%
	Boolean user = Boolean.valueOf(request.getParameter("loged"));
%>

<html>
	<head>
		<title>Blogs</title>
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
		
	
		<h1>${autor}'s blog entries:</h1>
		<c:if test="${entries.isEmpty()}">
			<p>No blog entries for this user</p>
		</c:if>
		<c:forEach var="e" items="${entries}">
			<p><a href="/blog/servleti/author/${autor}/${e.id}">${e.title}</a></p>
		</c:forEach>
	</body>
	<c:if test="${loged}">
		<p><a href="/blog/servleti/author/${autor}/new">Add new blog entry</a></p>
	</c:if>
	
	<p>
			Return to <a href="/blog/servleti/main">homepage</a>
	</p>
</html>