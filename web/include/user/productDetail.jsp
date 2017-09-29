<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
 
<script>
	
</script>

<div class="productDetailDiv" >
	<div class="productDetailTopPart">
		<a href="#details" class="productDetailTopPartSelectedLink selected">Details</a>
		<a  href="#reviews" class="selected">Reviews</a>
	</div>
	<div class="productDetailImagesPart" id = "details">
		<c:forEach items="${product.detailPics}" var="pdetail">
			<img src = "pic/pic_detail/${pdetail.id}.jpg">
		</c:forEach>
	</div>
</div>

<br>
<br>

<div class="reviewStasticsDiv" id="reviews">
		<div class="reviewStasticsLeft">
				<div class="reviewStasticsLeftTop"></div>
				<div class="reviewStasticsLeftContent">Customer reviews</div>
				<div class="reviewStasticsLeftFoot"></div>
		</div>
		<div class="reviewStasticsRight">
			<div class="reviewStasticsRightEmpty"></div>
			<div class="reviewStasticsFoot"></div>
		</div>
	</div>		
	
	<div class="reviewDivlistReviews">
		<c:forEach items="${reviews}" var="r">
			<div class="reviewDivlistReviewsEach">
				<div class="reviewDate"><fmt:formatDate value="${r.createDate}" pattern="yyyy-MM-dd"/></div>
				<div class="reviewContent">${r.content}</div>
				<div class="reviewUserInfo pull-right">${r.user.name}<</div>
			</div>
		</c:forEach>
	</div>
	
<br>	
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>