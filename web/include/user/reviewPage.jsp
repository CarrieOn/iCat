<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<br>
<br>

<div class="reviewDiv">
	<div class="reviewProductInfoDiv">
		<div class="reviewProductInfoImg"><a href = "foreproduct?pid=${p.id}"><img height="200px" src="pic/pic_simple/${p.firstPic.id}.jpg"></div>
		<div class="reviewProductInfoRightDiv"></a>
			<table class="reviewProductInfoTable">
				<tr>
					<td width="100px">name :</td>
					<td>${p.name}</td>
				</tr>
				<tr>
					<td width="100px">price :</td>
					<td><span>$<fmt:formatNumber type="number" value="${p.originalPrice}" minFractionDigits="2"/></span></td>
				</tr>
				<tr>
					<td width="100px">your price :</td>
					<td><span class="reviewProductInfoTablePrice">$<fmt:formatNumber type="number" value="${p.promotePrice}" minFractionDigits="2"/></span></td>
				</tr>
				<tr>
				    <td width="100px">bought on :</td>
					<td><span><fmt:formatDate value="${o_admin.createDate}" pattern="yyyy-MM-dd"/></span></td>			
				</tr>
			</table>
		</div>
		<div style="clear:both"></div>
		<span style="font-size:13px; color:DimGrey;"> -- -- -- -- -- -- -- -- -- -- -- -- -- Click pic to review details -- -- -- -- -- -- -- -- -- -- -- -- -- </span>
	</div>
	<br>
	<br>
	<br>
	
	<div class="reviewStasticsDiv">
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
	
	<div class="makeReviewDiv">
		<form method="post" action="foresubmitReview">
			<div class="makeReviewText">Thank you for writing comments.</div>
			<table class="makeReviewTable">
				<tr>
					<td class="makeReviewTableFirstTD">Review</td>
					<td><textarea name="content"></textarea></td>
				</tr>
			</table>
			<div class="makeReviewButtonDiv">
				<input type="hidden" name="oid_admin" value="${o_admin.id}">
				<input type="hidden" name="pid" value="${p.id}">
				<button type="submit">Submit</button>
			</div>
		</form>
	</div>			
</div>

<br>
<br>