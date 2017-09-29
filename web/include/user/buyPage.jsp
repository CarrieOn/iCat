<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<div class="buyPageDiv">
  <form action="forecreateOrder" method="post">
	  <div class="buyFlow">
			<img class="pull-left" src="pic/pic_simple/logo.png" height = "50px">
			<div style="clear:both"></div>
		</div> 
		
		<div class="address">
			<div class="addressTip">Input address</div>
			<div><table class="addressTable">
				<tr>
					<td class="firstColumn">Address<span class="redStar">*</span></td>					
					<td><textarea name="address" placeholder="detailed address"></textarea></td>
				</tr>
				<tr>
					<td>Zipcode</td>
					<td><input  name="post" placeholder="xxxxx" type="text"></td>
				</tr>
				<tr>
					<td>Name<span class="redStar">*</span></td>
					<td><input  name="receiver"  placeholder="receiver name" type="text"></td>
				</tr>
				<tr>
					<td>Phone<span class="redStar">*</span></td>
					<td><input name="mobile"  placeholder="phone number" type="text"></td>
				</tr>
			</table></div>
		</div>
		
		<div class="productList">
			<div class="productListTip">Confirm Your Order Info.</div>		
			<table class="productListTable">
				<thead>
				<tr>
					<th>picture</th>
					<th>name</th>
					<th>price</th>
					<th>number</th>
					<th>sum</th>
					<th>shipping</th>
				</tr>
				<tr class="rowborder">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				</thead>
				
				<tbody class="productListTableTbody">
					<c:forEach items="${os_user}" var="o_user" varStatus="st" >
					<tr class="orderItemTR">
						<td><span class="orderItemFirstTD"><img class="orderItemImg" src="pic/pic_simple/${o_user.product.firstPic.id}.jpg"></span></td>
						<td><span class="orderItemProductInfo"><a  href="foreproduct?pid=${o_user.product.id}" class="orderItemProductLink">${o_user.product.name}</a></span></td>
						<td><span class="orderItemProductPrice">$<fmt:formatNumber type="number" value="${o_user.product.promotePrice}" minFractionDigits="2"/></span></td>
						<td><span class="orderItemProductNumber">${o_user.number}</span></td>
						<td><span class="orderItemUnitSum">$<fmt:formatNumber type="number" value="${o_user.number*o_user.product.promotePrice}" minFractionDigits="2"/></span></td>
						<c:if test="${st.count==1}">
						<td rowspan="5"  class="orderItemLastTD">
							<label class="orderItemDeliveryLabel"><input type="radio" value="" checked="checked"> free shipping</label><br>
							<select class="orderItemDeliverySelect" class="form-control"><option>more choices</option></select>
						</td>
						</c:if>
					</tr>
					</c:forEach>	
				</tbody>
			</table>
			
			<div class="orderItemSumDiv">
				<div class="pull-left">
					<span class="leaveMessageText">leave a message : </span>
					<span class="leaveMessageTextareaSpan">
						<textarea name="userMessage" class="leaveMessageTextarea" maxlength="50" placeholder="50 characters at most"></textarea>
					</span>
				</div>
			</div>
		
			<div class="orderItemTotalSumDiv">
				<div class="pull-right"> 
					<span>Sum：</span>
					<span class="orderItemTotalSumSpan">$<fmt:formatNumber type="number" value="${totalPrice/100}" minFractionDigits="2"/></span>
				</div>
			</div>
	
			<div class="submitOrderDiv">
				<button type="submit" class="submitOrderButton">Submit</button>
			</div>
	
	</form>
</div>