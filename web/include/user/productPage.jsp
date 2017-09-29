<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<title>eShop</title>

<div class="categoryPictureInProductPageDiv">
    <img class="categoryPictureInProductPage" src="pic/category/${product.category.id}.jpg">
</div>

<div class="productPageDiv">
    <%@include file="productSimple.jsp" %>     
    <%@include file="productReview.jsp" %>
    <%@include file="productDetail.jsp" %>
</div>