<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<div class = "homepageCategoryProducts">
	<c:forEach items = "${cs}" var = "c" varStatus="st">
	<c:if test="${st.count<6}">
		<div class="eachHomepageCategoryProducts">
			<div class="left-mark"></div>
			<span class="categoryTitle">${c.name}</span>
			<br>
			<c:forEach items = "${c.products}" var = "p">
				<div class = "productItem">
					<a href = "foreproduct?pid=${p.id}"><img width="100px" src="pic/pic_simple/${p.firstPic.id}.jpg"></a>
					<a class="productItemDescLink" href="foreproduct?pid=${p.id}"><span class="productItemDesc">[iCat] ${p.name}</span></a>
					<span class="productPrice">$<fmt:formatNumber type="number" value="${p.promotePrice}" minFractionDigits="2"/></span>
				</div>		
			</c:forEach>
			<div style="clear:both"></div>
		</div>
		</c:if>
	</c:forEach>
</div>

<br>
<br>