<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<script>
</script>

<title>list pictures</title>

<div class="workingArea">
	<ol class="breadcrumb">
		  <li><a href="admin_category_list">category</a></li>
		  <li><a href="admin_product_list?cid=${product.category.id}">${product.category.name}</a></li>
		  <li class="active">${product.name}</li>
		  <li class="active">picture</li>
		</ol>
	
	<div class = "listDataTableDiv">
		<table class="table table-striped table-bordered table-hover  table-condensed">
			<thead>
				<tr class = "success">
					<th>ID</th>
					<th>product</th>
					<th>picture_simple</th>
					<th>picture_detail</th>
				</tr>
			</thead>
			
			<tbody>			
				<tr>
					<td>${product.id}</td>
					<td>${product.name}</td>
					<td>
						<c:forEach items = "${pics_simple}" var = "pic_simple">
							<img height = "100px" src = "pic/pic_simple/${pic_simple.id}.jpg">
						</c:forEach>
					</td>
					<td>
						<c:forEach items = "${pics_detail}" var = "pic_detail">
							<img height = "100px" src = "pic/pic_detail/${pic_detail.id}.jpg">
						</c:forEach>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>

<%@include file="../include/admin/adminFooter.jsp"%>