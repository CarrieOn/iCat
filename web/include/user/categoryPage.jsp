<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<title>eShop-${c.name}</title> 

<div align="center" >
	<img src="pic/category/${c.id}.jpg">
</div>

<div id="category">
    <div class="categoryPageDiv">       
        <%@include file="sortBar.jsp"%>
        <%@include file="productsOfCategory.jsp"%>
    </div> 
</div>