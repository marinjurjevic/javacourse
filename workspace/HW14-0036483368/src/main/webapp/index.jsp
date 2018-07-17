<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Welcome</title>
	</head>
	
   <body>
     <p> Izaberite anketu u kojoj zelite glasati</p>
     
     <ol>
     <c:forEach var="p" items="${polls}">
		
			<li> <a href="glasanje?pollID=${p.id}"> ${p.title} </a>  </li>
		
	 </c:forEach>
	 </ol>
	 
   </body>
</html>