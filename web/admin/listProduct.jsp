<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<script>
$(function(){
	$("#addForm").submit(function(){
		if (!checkEmpty("name", "name"))
			return false;
		if (!checkEmpty("subTitle", "subTitle"))
			return false;
		if (!checkEmpty("originalPrice", "originalPrice"))
			return false;
		if (!checkEmpty("promotePrice", "promotePrice"))
			return false;
		if (!checkEmpty("stock", "stock"))
			return false;
		return true;
	});
});
</script>

<title>list products</title>

<div class="workingArea">
	<ol class="breadcrumb">
		  <li><a href="admin_category_list">category</a></li>
		  <li class="active">product</li>
		</ol>
	
	<div class = "listDataTableDiv">
		<table class="table table-striped table-bordered table-hover  table-condensed">
			<thead>
				<tr class = "success">
					<th>ID</th>
					<th>picture</th>
					<th>name</th>
					<th>subTitle</th>
					<th>originalPrice</th>
					<th>promotePrice</th>
					<th>stock</th>
					<th>createDate</th>
					<th>delete</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items = "${products}" var = "product">
					<tr>
						<td>${product.id}</td>
						<td><a href="admin_picture_list?pid=${product.id}"><span class="glyphicon glyphicon-camera"></span></a></td>
						<td>${product.name}</td>
						<td>${product.subTitle}</td>
						<td>${product.originalPrice}</td>
						<td>${product.promotePrice}</td>
						<td>${product.stock}</td>
						<td><fmt:formatDate value="${product.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><a href="admin_product_delete?id=${product.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
	
<div class="panel panel-warning addDiv">
	<div class="panel-heading">add a new product</div>
	<div class="panel-body">
		<form method="post" id="addForm" action="admin_product_add">
	    	<table class="addTable">
	    		<tr>
					<td>name</td>
					<td><input id = "name" name = "name" type = "text" class = "form-control"></td>
				</tr>
				<tr>
					<td>subTitle</td>
					<td><input id = "subTitle" name = "subTitle" type = "text" class = "form-control"></td>
				</tr>
				<tr>
					<td>originalPrice</td>
					<td><input id = "originalPrice" name = "originalPrice" value = "280.0" type = "text" class = "form-control"></td>
				</tr>
				<tr>
					<td>promotePrice</td>
					<td><input id = "promotePrice" name = "promotePrice" value = "269.0" type = "text" class = "form-control"></td>
				</tr>
				<tr>
					<td>stock</td>
					<td><input id = "stock" name = "stock" value = "10" type = "text" class = "form-control"></td>
				</tr>
				<tr class = "submitTR">
					<td colspan = "2" align = "center">
						<input type="hidden" name="cid" value="${cid}">
						<button type = "submit" class = "btn btn-success">submit</button>
					</td>
				</tr>
	    	</table>
	    </form>
	</div>
</div>

<%@include file="../include/admin/adminFooter.jsp"%>