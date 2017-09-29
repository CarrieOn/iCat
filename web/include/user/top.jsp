<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %> 

<nav class = "top" id = "top">

	<a href="forehome">
		<span style="color:#C40000; margin:0px" class=" glyphicon glyphicon-home redColor"></span>Home
	</a>	
	
	<span>Welcome</span>
	
	<c:if test="${!empty user}">
		<a href="login.jsp">Hello, ${user.name}</a>
		<a href="forelogout">Logout</a>		
	</c:if>
	
	<c:if test="${empty user}">
		<a href="login.jsp">Login</a>
		<a href="register.jsp">Register</a>		
	</c:if>


	<span class="pull-right">
		<a href="forebought"><span style="color:#C40000; margin:0px" class=" glyphicon glyphicon-file"></span>My order</a>
		<a href="forecart"><span style="color:#C40000; margin:0px" class=" glyphicon glyphicon-shopping-cart redColor"></span>My cart</a>	
	</span>
		
</nav>