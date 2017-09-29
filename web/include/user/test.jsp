<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<div class="boughtDiv">
	<div class="orderType">
		<div class="selectedOrderType"><a href="#nowhere">All Orders</a></div>
		<div class="orderTypeLastOne"><a class="noRightborder">&nbsp;</a></div>
	</div>
	<div style="clear:both"></div>
	<div class="orderListTitle">
		<table class="orderListTitleTable">
			<tr>
				<td width="200px" align="center"><b>Picture</b></td>
				<td width="200px" align="center"><b>Name</b></td>
				<td width="200px" align="center"><b>Price</b></td>
				<td width="200px" align="center"><b>Number</b></td>
				<td width="200px" align="center"><b>Sum</b></td>
				<td width="200px" align="center"><b>Status</b></td>
			</tr>
		</table>
	</div>
	
	<div class="orderListItem">
		<c:forEach items="${os_admin_bought}" var="o_admin_bought">
			<table class="orderListItemTable" orderStatus="${o_admin_bought.status}" oid="${o_admin_bought.id}">
				<tr class="orderListItemFirstTR">
					<td colspan="6"><b><fmt:formatDate value="${o_admin_bought.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></b></td>
				</tr>
			
				<c:forEach items="${o_admin_bought.orderUser}" var="o_user_bought" varStatus="st">
					<tr class="orderItemProductInfoPartTR" >
						<td width="200px" align="left"><img width="80" height="80" src="pic/pic_simple/${o_user_bought.product.firstPic.id}.jpg"></td>
						<td width="200px" align="center">
							<div><a href="foreproduct?pid=${o_user_bought.product.id}">${o_user_bought.product.name}</a></div>
						</td>
						<td width="200px" align="center">						
							<div>$<fmt:formatNumber type="number" value="${o_user_bought.product.promotePrice}" minFractionDigits="2"/></div>
						</td>
						<td width="200px" align="center">
							<span class="orderListItemNumber">${o_user_bought.number}</span>
						</td>
						<td width="200px" align="center">
							<div class="orderListItemProductRealPrice">$<fmt:formatNumber  minFractionDigits="2"  maxFractionDigits="2" type="number" value="${o_user_bought.number * o_user_bought.product.promotePrice}"/></div>
						</td>
						<td width="200px" align="center">
							<div>${o_admin_bought.status}</div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:forEach>
	</div>
</div>