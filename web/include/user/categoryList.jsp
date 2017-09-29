<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<div class="categoryMenu">
	<c:forEach items="${cs}" var="c" varStatus="st">
		<br>
		<div cid="${c.id}" class="eachCategory">
			<span class="glyphicon glyphicon-link"></span>
			<c:if test = "${st.count<=10}">
				<a href="forecategory?cid=${c.id}">${c.name}</a>
			</c:if>
		</div>
	</c:forEach>
	<br>
	<br>
</div>  