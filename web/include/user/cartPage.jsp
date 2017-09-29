<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<br>
<br>
<script>
var deleteOrderUser = false;
var deleteOrderUserId = 0;
$(function(){
	
	$("img.cartProductItemIfSelected").click(function(){
		var selectit = $(this).attr("selectit");
		if("selectit"==selectit){
			$(this).attr("src","pic/pic_simple/cartNotSelected.png");
			$(this).attr("selectit","false")
			$(this).parents("tr.cartProductItemTR").css("background-color","#fff");
		}
		else{
			$(this).attr("src","pic/pic_simple/cartSelected.png");
			$(this).attr("selectit","selectit")
			$(this).parents("tr.cartProductItemTR").css("background-color","#FFF8E1");
		}
		syncSelect();
		syncCreateOrderButton();
		calcCartSumPriceAndNumber();
	});
	
	$("img.selectAllItem").click(function(){
		var selectit = $(this).attr("selectit")
		if("selectit"==selectit){
			$("img.selectAllItem").attr("src","pic/pic_simple/cartNotSelected.png");
			$("img.selectAllItem").attr("selectit","false")
			$(".cartProductItemIfSelected").each(function(){
				$(this).attr("src","pic/pic_simple/cartNotSelected.png");
				$(this).attr("selectit","false");
				$(this).parents("tr.cartProductItemTR").css("background-color","#fff");
			});			
		}
		else{
			$("img.selectAllItem").attr("src","pic/pic_simple/cartSelected.png");
			$("img.selectAllItem").attr("selectit","selectit")
			$(".cartProductItemIfSelected").each(function(){
				$(this).attr("src","pic/pic_simple/cartSelected.png");
				$(this).attr("selectit","selectit");
				$(this).parents("tr.cartProductItemTR").css("background-color","#FFF8E1");
			});				
		}
		syncCreateOrderButton();
		calcCartSumPriceAndNumber();
	});
	
	$(".orderItemNumberSetting").keyup(function(){
		var pid=$(this).attr("pid");
		var stock= $("span.orderItemStock[pid="+pid+"]").text();
		var price= $("span.orderItemPromotePrice[pid="+pid+"]").text();		
		var num= $(".orderItemNumberSetting[pid="+pid+"]").val();
		num = parseInt(num);
		if(isNaN(num))
			num= 1;
		if(num<=0)
			num = 1;
		if(num>stock)
			num = stock;		
		syncPrice(pid,num,price);
	});
	
	$(".numberPlus").click(function(){		
		var pid=$(this).attr("pid");
		var stock= $("span.orderItemStock[pid="+pid+"]").text();
		var price= $("span.orderItemPromotePrice[pid="+pid+"]").text();
		var num= $(".orderItemNumberSetting[pid="+pid+"]").val();
		num++;
		if(num>stock)
			num = stock;
		syncPrice(pid,num,price);
	});
	
	$(".numberMinus").click(function(){
		var pid=$(this).attr("pid");
		var stock= $("span.orderItemStock[pid="+pid+"]").text();
		var price= $("span.orderItemPromotePrice[pid="+pid+"]").text();		
		var num= $(".orderItemNumberSetting[pid="+pid+"]").val();
		--num;
		if(num<=0)
			num=1;
		syncPrice(pid,num,price);
	});	
	
	$("button.createOrderButton").click(function(){
		var params = "";
		$(".cartProductItemIfSelected").each(function(){
			if("selectit"==$(this).attr("selectit")){
				var o_user_cart_id = $(this).attr("o_user_cart_id");
				params += "&oid_user="+o_user_cart_id;
			}
		});
		params = params.substring(1);
		location.href="forebuy?"+params;
	});	
});

function syncCreateOrderButton(){
	var selectAny = false;
	$(".cartProductItemIfSelected").each(function(){
		if("selectit"==$(this).attr("selectit")){
			selectAny = true;
		}
	});	
	if(selectAny){
		$("button.createOrderButton").css("background-color","#C40000");
		$("button.createOrderButton").removeAttr("disabled");
	}
	else{
		$("button.createOrderButton").css("background-color","#AAAAAA");
		$("button.createOrderButton").attr("disabled","disabled");		
	}		
}

function syncSelect(){
	var selectAll = true;
	$(".cartProductItemIfSelected").each(function(){
		if("false"==$(this).attr("selectit")){
			selectAll = false;
		}
	});
	
	if(selectAll)
		$("img.selectAllItem").attr("src","pic/pic_simple/cartSelected.png");
	else
		$("img.selectAllItem").attr("src","pic/pic_simple/cartNotSelected.png");
}

function calcCartSumPriceAndNumber(){
	var sum = 0;
	var totalNumber = 0;
	$("img.cartProductItemIfSelected[selectit='selectit']").each(function(){
		var o_user_cart_id = $(this).attr("o_user_cart_id");
		var price =$(".cartProductItemSmallSumPrice[o_user_cart_id="+o_user_cart_id+"]").text();
		price = price.replace(/,/g, "");
		price = price.replace(/\$/g, "");
		sum += new Number(price);	
		var num =$(".orderItemNumberSetting[o_user_cart_id="+o_user_cart_id+"]").val();
		totalNumber += new Number(num);		
	});	
	$("span.cartSumPrice").html("$"+formatMoney(sum));
	$("span.cartSumNumber").html(totalNumber);
}

function syncPrice(pid,num,price){
	$(".orderItemNumberSetting[pid="+pid+"]").val(num);
	var cartProductItemSmallSumPrice = formatMoney(num*price); 
	$(".cartProductItemSmallSumPrice[pid="+pid+"]").html("$"+cartProductItemSmallSumPrice);
	calcCartSumPriceAndNumber();	
	var page = "forechangeOrderUser";
	$.post(
		    page,
		    {"pid":pid,"num":num},
		    function(result){
				if("success"!=result){
					location.href="login.jsp";
				}
		    }
		);
}

</script>


<title>iCat-cart</title>

<div class="cartDiv">
	<div class="cartProductList">
		<table class="cartProductTable">
			<thead>
				<tr>
					<th class="selectAndImage"><img selectit="false" class="selectAllItem" src="pic/pic_simple/cartNotSelected.png"><b>Select all</b></th>
					<th><b>Product</b></th>
					<th><b>Price</b></th>
					<th><b>Number</b></th>
					<th><b>Total</b></th>
					<th class="operation"><b>Delete</b></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${os_user_cart}" var="o_user_cart">
					<tr o_user_cart_id="${o_user_cart.id}" class="cartProductItemTR">
						<td>
							<img selectit="false" o_user_cart_id="${o_user_cart.id}" class="cartProductItemIfSelected" src="pic/pic_simple/cartNotSelected.png">
							<a style="display:none" href="#nowhere"><img src="pic/pic_simple/cartSelected.png"></a>
							<img class="cartProductImg"  src="pic/pic_simple/${o_user_cart.product.firstPic.id}.jpg">
						</td>
						<td>
							<div class="cartProductLinkOutDiv">
								<a href="foreproduct?pid=${o_user_cart.product.id}" class="cartProductLink">${o_user_cart.product.name}</a>
							</div>							
						</td>
						<td>
							<span class="cartProductItemOringalPrice">$${o_user_cart.product.originalPrice}</span>
							<span  class="cartProductItemPromotionPrice">$${o_user_cart.product.promotePrice}</span>							
						</td>
						<td>					
							<div class="cartProductChangeNumberDiv">
								<span class="hidden orderItemStock " pid="${o_user_cart.product.id}">${o_user_cart.product.stock}</span>
								<span class="hidden orderItemPromotePrice " pid="${o_user_cart.product.id}">${o_user_cart.product.promotePrice}</span>
								<a  pid="${o_user_cart.product.id}" class="numberMinus" href="#nowhere">-</a>
								<input pid="${o_user_cart.product.id}" o_user_cart_id="${o_user_cart.id}" class="orderItemNumberSetting" autocomplete="off" value="${o_user_cart.number}">
								<a  stock="${o_user_cart.product.stock}" pid="${o_user_cart.product.id}" class="numberPlus" href="#nowhere">+</a>
							</div>										
						</td>
						<td >
							<span class="cartProductItemSmallSumPrice" o_user_cart_id="${o_user_cart.id}" pid="${o_user_cart.product.id}" >
								$<fmt:formatNumber type="number" value="${o_user_cart.product.promotePrice*o_user_cart.number}" minFractionDigits="2"/>
							</span>			
						</td>
						<td>
							<a class="deleteOrderItem" o_user_cart_id="${o_user_cart.id}"  href="foredeleteOrderUser?oid_user=${o_user_cart.id}">delete</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>	
	
	<div class="cartFoot">
		<img selectit="false" class="selectAllItem" src="pic/pic_simple/cartNotSelected.png">
		<span>select all</span>		
		<div class="pull-right">
			<span>selected <span class="cartSumNumber" >0</span> items , </span>			
			<span>total price : </span> 
			<span class="cartSumPrice" >$0.00</span>
			<button class="createOrderButton" disabled="disabled" >Submit</button>
		</div>		
	</div>
	
</div>

<br>
<br>
<br>
<br>