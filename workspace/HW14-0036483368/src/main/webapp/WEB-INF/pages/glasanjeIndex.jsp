<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Glasanje</title>
	</head>
	
	<body>
		<h1>
			${sessionScope.currentPoll.title}
		</h1>
	 	<p>
	 		${sessionScope.currentPoll.message}
	 	</p>
	 	
	 	<ul>
	 		<c:forEach var="o" items="${options}">
				<li> <a href="glasanje-glasaj?id=${o.id}"> ${o.optionTitle} </a> </li>
			</c:forEach>
	 		
	 	</ul>
	 	
		<a href="index.jsp"> Povratak na pocetnu stranicu </a> 
	</body>

</html>		