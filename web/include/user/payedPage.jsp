<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<br>
<br>
<br>
<br>
<br>
<div class="payedDiv">
    <div class="payedTextDiv">
        <img src="pic/pic_simple/paySuccess.png">
        <span>Payed successfully!</span>         
    </div>
    <div class="payedAddressInfo">
        <ul>
            <li>Address   ：${o_admin.address}</li>
            <li>Receiver   ：${o_admin.buyer} </li>
            <li>Cellphone ：${o_admin.mobile }</li>
            <br>
            <li>Price  ：<span class="payedInfoPrice">$<fmt:formatNumber type="number" value="${param.total}" minFractionDigits="2"/></li>
            <li>Arrive  :  in <b style="font-size:15px; color:FireBrick ">3</b> days</li>
        </ul>            
    </div>     
    <div class="payedSeperateLine"></div>     
    <div class="warningDiv">
        <img src="pic/pic_simple/logo.png" height = "30px">
        <b>See you next time !</b>
    </div> 
</div>
<br>
<br>
<br>
<br>
<br>
<br>