<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<div class="aliPayPageDiv">
	<div><img class="aliPayImg" src="pic/pic_simple/payLogo.png" height = "200px"></div>
    <div>
        <img class="aliPayImg" src="pic/pic_simple/QRpay.jpg" height = "200px">
        <span class="confirmMoney">$<fmt:formatNumber type="number" value="${param.total}" minFractionDigits="2"/></span> 
    </div>
    <div><a href="forepayed?oid_admin=${param.oid_admin}&total=${param.total}"><button class="buyButton" style="color:white;border:white;background-color:orange">Scan QR code to pay</button></a></div>
</div>