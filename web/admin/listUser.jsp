<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<script>
</script>

<title>list users</title>

<div class="workingArea">
	<h1 class = "label label-info"> -- User -- </h1>
	<br>
	<br>
	
	<div class = "listDataTableDiv">
		<table class="table table-striped table-bordered table-hover  table-condensed">
			<thead>
				<tr class = "success">
					<th>ID</th>
					<th>name</th>
					<th>password</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items = "${users}" var = "user">
					<tr>
						<td>${user.id}</td>
						<td>${user.name}</td>
						<td>${user.password}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<%@include file="../include/admin/adminFooter.jsp"%>