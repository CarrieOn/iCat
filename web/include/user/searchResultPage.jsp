<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<div id="searchResult">	
	<div class="searchResultDiv">
		<div class="searchProducts">
			
			<c:if test = "${not empty ps_search}">
				<div>
					<c:forEach items="${ps_search}" var="p_search">
						<div class="productUnit" price="${p_search.promotePrice}">
							<a href="foreproduct?pid=${p_search.id}"><img class="productImage" src="pic/pic_simple/${p_search.firstPic.id}.jpg"></a>
							<span class="productPrice">$<fmt:formatNumber type="number" value="${p_search.promotePrice}" minFractionDigits="2"/></span>
							<a class="productLink" href="foreproduct?pid=${p_search.id}">${p_search.name}</a>
						</div>
					</c:forEach>
				</div>
			</c:if>
					
			<c:if test = "${empty ps_search}">
				<div class="noMatch">Sorry, no results.<div>
			</c:if>
			
			<div style="clear:both"></div>
		</div>
	</div>
</div>
