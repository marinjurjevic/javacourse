<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html >
	<head>
		<style type="text/css">
		table.rez td {text-align: center;}
		table.rez {
    			margin-left:auto; 
    			margin-right:auto;
  		}
		</style>
	
		<title>Rezultati</title>
	</head>
	
	<body align="center">
		<h1>Rezultati glasanja</h1>
		
	 	<p>
	 		Ovo su rezultati glasanja.
	 	</p>
	 	
	 	<table border="1" cellspacing="0" class="rez">
			<tr><td>Natjecatelj</td><td>Broj glasova</td></tr>
			
			<c:forEach var="r" items="${results}">
				<tr><td>${r.optionTitle}</td><td>${r.votesCount}</td></tr>
			</c:forEach>
		
		</table>
		
		<h2>Grafički prikaz rezultata</h2>
		
		<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />
		
		<h2>Rezultati u XLS formatu</h2>
		<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>
		
		<h2>Razno</h2>
		<p>Poveznice na pobjednike:</p>
		
		<p>
		<ul>
	 		<c:forEach var="w" items="${winners}">
				<li> <a href="${w.optionLink}" target="_blank"> ${w.optionTitle} </a> </li>
			</c:forEach>
	 		
	 	</ul>
		
		<a href="index.jsp"> Povratak na pocetnu stranicu </a> 
		
	</body>

</html>		