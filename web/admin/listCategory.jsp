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

<title>list categories</title>

<div class="workingArea">
	<h1 class = "label label-info"> -- Category -- </h1>
	<br>
	<br>
	
	<div class = "listDataTableDiv">
		<table class="table table-striped table-bordered table-hover  table-condensed">
			<thead>
				<tr class = "success">
					<th>ID</th>
					<th>picture</th>
					<th>name</th>
					<th>product</th>
					<th>edit</th>
					<th>delete</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items = "${cs}" var = "c">
					<tr>
						<td>${c.id}</td>
						<td><img height = "40px" src = "pic/category/${c.id}.jpg"></td>
						<td>${c.name}</td>
						<td><a href = "admin_product_list?cid=${c.id}"><span class="glyphicon glyphicon-shopping-cart"></span></a></td>
						<td><a href="admin_category_edit?cid=${c.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
						<td><a href="admin_category_delete?id=${c.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
	
<div class="panel panel-warning addDiv">
	<div class="panel-heading">add a new category</div>
	<div class="panel-body">
		<form method="post" id="addForm" action="admin_category_add" enctype="multipart/form-data">
	    	<table class="addTable">
	    		<tr>
					<td>name</td>
					<td><input id = "name" name = "name" type = "text" class = "form-control"></td>
				</tr>
				<tr>
					<td>picture</td>
					<td><input id = "picture" accept = "pic/*" type = "file" name = "filepath"></td>
				</tr>
				<tr class = "submitTR">
					<td colspan = "2" align = "center">
						<button type = "submit" class = "btn btn-success">submit</button>
					</td>
				</tr>
	    	</table>
	    </form>
	</div>
</div>

<%@include file="../include/admin/adminFooter.jsp"%>