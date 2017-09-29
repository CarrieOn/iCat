<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<script>
$(function(){
	$("#addForm").submit(function(){
		if(!checkEmpty("name","name"))
			return false;
		if(!checkEmpty("picture", "picture"))
			return false;
		return true;
	});
});
</script>

<title>list orders</title>

<div class="workingArea">
	<h1 class = "label label-info"> -- Order -- </h1>
	<br>
	<br>
	
	<div class = "listDataTableDiv">
		<table class="table table-striped table-bordered table-hover  table-condensed">
			<thead>
				<tr class = "success">
					<th>ID</th>
					<th>buyer</th>
					<th>mobile</th>
					<th>address</th>
					<th>zipcode</th>
					<th>message</th>
					<th>status</th>
					<th>createDate</th>
					<th>payDate</th>
					<th>deliveryDate</th>
					<th>confirmDate</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items = "${orders}" var = "order">
					<tr>
						<td>${order.id}</td>
						<td>${order.buyer}</td>
						<td>${order.mobile}</td>
						<td>${order.address}</td>
						<td>${order.zipcode}</td>
						<td>${order.message}</td>
						<td>${order.status}</td>
						<td><fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${order.payDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${order.deliveryDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${order.confirmDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<%@include file="../include/admin/adminFooter.jsp"%>