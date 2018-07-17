<%@page import="hr.fer.zemris.java.blog.model.BlogEntry"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %> 

<html>
	<head>
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
		
		<p>
			Return to <a href="/blog/servleti/author/${autor}">blogs</a>
		</p>
		
		<h1>${form.title}</h1>
		
		<table>
			<tr>
				<td>ID:</td> 
			 	<td>
			 		${form.id}<br>
			  	</td>
			</tr>
			<tr>
				<td></td>
				<td>
					${form.text}
				</td>
			</tr>
		</table>
		<p>
			<c:if test="${loged}">
				<%
					BlogEntry e = (BlogEntry) request.getAttribute("form");
					session.setAttribute("blogID", e.getId());
				%>
				<p><a href="/blog/servleti/author/${autor}/edit">Edit</a></p>
			</c:if>
		</p>
		
		<h2>Comments:</h2>
		<ul>
		<c:forEach var="c" items="${form.comments}">
			 <li><div style="font-weight: bold">[User=${c.usersEMail}] ${c.postedOn} </div>
			 <div style="padding-left: 10px;">${c.message}</div></li>
		</c:forEach>
		</ul>
		
		<form action="/blog/servleti/author/${autor}/${form.id}" method="post">
			<input type="hidden" name="blogID" value='<c:out value="${form.id}"></c:out>'>
			<table align="center">
			<tr>
				<td>
					Comment:
				</td>
				<td>
					<textarea name="comment" rows="16" cols="70"></textarea>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td>
					<input type="submit" name="metoda-komentar" value="Send comment">
				</td>
			</tr>
			</table>
		</form>
	</body>
</html>