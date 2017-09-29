<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
 
<script>
$(function(){
    var stock = ${product.stock};
    $(".productNumberSetting").keyup(function(){
        var num= $(".productNumberSetting").val();
        num = parseInt(num);
        if(isNaN(num))
            num= 1;
        if(num<=0)
            num = 1;
        if(num>stock)
            num = stock;
        $(".productNumberSetting").val(num);
    });
     
    $(".increaseNumber").click(function(){
        var num= $(".productNumberSetting").val();
        num++;
        if(num>stock)
            num = stock;
        $(".productNumberSetting").val(num);
    });
    $(".decreaseNumber").click(function(){
        var num= $(".productNumberSetting").val();
        --num;
        if(num<=0)
            num=1;
        $(".productNumberSetting").val(num);
    });
     
    $(".addCartButton").removeAttr("disabled");
    $(".addCartLink").click(function(){
        var page = "forecheckLogin";
        $.get(
                page,
                function(result){
                    if("success"==result){
                        var pid = ${product.id};
                        var num= $(".productNumberSetting").val();
                        var addCartpage = "foreaddCart";
                        $.get(
                                addCartpage,
                                {"pid":pid,"num":num},
                                function(result){
                                    if("success"==result){
                                        $(".addCartButton").html("added succesfully");
                                        $(".addCartButton").attr("disabled","disabled");
                                        $(".addCartButton").css("background-color","lightgray")
                                        $(".addCartButton").css("border-color","lightgray")
                                        $(".addCartButton").css("color","black")
                                         
                                    }
                                    else{
                                         
                                    }
                                }
                        );                          
                    }
                    else{
                        $("#loginModal").modal('show');                     
                    }
                }
        );      
        return false;
    });
    $(".buyLink").click(function(){
        var page = "forecheckLogin";
        $.get(
                page,
                function(result){
                    if("success"==result){
                        var num = $(".productNumberSetting").val();
                        location.href= $(".buyLink").attr("href")+"&num="+num;
                    }
                    else{
                        $("#loginModal").modal('show');                     
                    }
                }
        );      
        return false;
    });
     
    $("button.loginSubmitButton").click(function(){
        var name = $("#name").val();
        var password = $("#password").val();
         
        if(0==name.length||0==password.length){
            $("span.errorMessage").html("please input account/password");
            $("div.loginErrorMessageDiv").show();           
            return false;
        }
         
        var page = "foreloginAjax";
        $.get(
                page,
                {"name":name,"password":password},
                function(result){
                    if("success"==result){
                        location.reload();
                    }
                    else{
                        $("span.errorMessage").html("invalid account/password");
                        $("div.loginErrorMessageDiv").show();                       
                    }
                }
        );                   
        return true;
    });
     
    $("img.smallImage").mouseenter(function(){
        var bigImageURL = $(this).attr("bigImageURL");
        $("img.bigImg").attr("src",bigImageURL);
    });
     
    $("img.bigImg").load(
        function(){
            $("img.smallImage").each(function(){
                var bigImageURL = $(this).attr("bigImageURL");
                img = new Image();
                img.src = bigImageURL;
                 
                img.onload = function(){
                    $("div.img4load").append($(img));
                };
            });     
        }
    );
});
</script>

<div class="imgAndInfo"> 

    <div class="imgInimgAndInfo">
        <img src="pic/pic_simple/${product.firstPic.id}.jpg" class="bigImg">
        <div class="smallImageDiv">
            <c:forEach items="${product.detailPics}" var="pdetail">
            	<img src="pic/pic_detail/${pdetail.id}.jpg" bigImageURL="pic/pic_detail/${pdetail.id}.jpg" class="smallImage">
            </c:forEach>
            </div>
        <div class="img4load hidden" ></div>
    </div>
    
    <div class="infoInimgAndInfo">              
        <div class="productPrice">
        	<div class="juhuasuan" style="background-color:FireBrick;">
                <span class="juhuasuanBig" >${product.name}</span>
            </div>
            <div class="productTitle" style="font-size:15px; color:FireBrick;">${product.subTitle}</div>
            <br>
            <div class="productPriceDiv">
                <div class="originalDiv">
                    <span class="originalPriceDesc">Price</span>
                    <span class="originalPriceYuan">$</span>
                    <span class="originalPrice"><fmt:formatNumber type="number" value="${product.originalPrice}" minFractionDigits="2"/></span>
                </div>
                <div class="promotionDiv">
                    <span class="promotionPriceDesc">Sale</span>
                    <span class="promotionPriceYuan">$</span>
                    <span class="promotionPrice"><fmt:formatNumber type="number" value="${product.promotePrice}" minFractionDigits="2"/></span>               
                </div>
            </div>
        </div>
        <div class="productNumber">
            <span>Qty:</span>
            <span>
                <span class="productNumberSettingSpan"><input class="productNumberSetting" type="text" value="1"></span>
                <span class="arrow">
                    <a href="#nowhere" class="increaseNumber">
                    	<span class="updown"><img src="pic/pic_simple/increase.png"></span>
                    </a> 
                	<span class="updownMiddle"> </span>
                    <a href="#nowhere"  class="decreaseNumber">
                    	<span class="updown"><img src="pic/pic_simple/decrease.png"></span>
                    </a>                  
                </span>
            </span>
            <span>${product.stock} in stock</span>
        </div>
        <br>
        <div class="serviceCommitment">
            <span class="serviceCommitmentDesc">-- Free shipping<br>-- Free return in 90 days</span>
        </div>   
    	<div class="buyDiv">
        	<a class="buyLink" href="forebuyone?pid=${product.id}"><button class="buyButton">Buy now</button></a>
        	<a href="#nowhere" class="addCartLink"><button class="addCartButton"><span class="glyphicon glyphicon-shopping-cart"></span>Add to cart</button></a>
    	</div>
  	</div>     
  	<div style="clear:both"></div>
</div>