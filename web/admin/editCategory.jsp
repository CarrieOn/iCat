<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<title>edit category</title>

<script>
$(function(){
	$("#editForm").submit(function(){
		if(!checkEmpty("name", "name"))
			return false;
		return true;
	});
});
</script>

<div class = "workingArea">
	<ol class = "breadcrumb">
		<li><a href = "admin_category_list"> all categories</a></li>
		<li>edit category</li>
	</ol>
	
	<div class = "panel panel-warning editDiv">
		<div class = "panel-heading">edit category</div>
		<div class = "panel-body">
			<form method = "post" id = "editForm" action = "admin_category_update" enctype = "multipart/form-data">
				<table class = "editTable">
					<tr>
						<td>name</td>
						<td><input id = "name" name = "name" value = "${c.name}" type = "text" class = "form-control"></td>
					</tr>
					<tr>
						<td>picture</td>
						<td><input id = "picture" accept = "pic/*" type = "file" name = "filepath"></td>
					</tr>
					<tr class = "submitTR">
						<td colspan = "2" align = "center">
							<input type = "hidden" name = "id" value = "${c.id}">
							<button type = "submit" class = "btn btn-success">submit</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>

</div>

<%@include file="../include/admin/adminFooter.jsp"%>