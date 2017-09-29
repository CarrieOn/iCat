<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<script>
$(function(){
     
    <c:if test="${!empty msg}">
    $("span.errorMessage").html("${msg}");
    $("div.registerErrorMessageDiv").css("visibility","visible");       
    </c:if>
     
    $(".registerForm").submit(function(){
        if(0==$("#name").val().length){
            $("span.errorMessage").html("please input user name");
            $("div.registerErrorMessageDiv").css("visibility","visible");           
            return false;
        }       
        if(0==$("#password").val().length){
            $("span.errorMessage").html("please input password");
            $("div.registerErrorMessageDiv").css("visibility","visible");           
            return false;
        }       
        if(0==$("#repeatpassword").val().length){
            $("span.errorMessage").html("please re-enter password");
            $("div.registerErrorMessageDiv").css("visibility","visible");           
            return false;
        }       
        if($("#password").val() !=$("#repeatpassword").val()){
            $("span.errorMessage").html("passwords are different");
            $("div.registerErrorMessageDiv").css("visibility","visible");           
            return false;
        }       
        return true;
    });
})
</script>
 
<form method="post" action="foreregister" class="registerForm">
	<div class="registerDiv">
	    <div class="registerErrorMessageDiv">
	        <div class="alert alert-danger" role="alert">
	          <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
	            <span class="errorMessage"></span>
	        </div>        
	    </div>
	 
	    <table class="registerTable" align="center">
	        <tr>
	            <td  class="registerTip registerTableLeftTD"><img src="pic/pic_simple/logo.png" height = "45px">Create your account</td>
	            <td></td>
	        </tr>
	        <tr>
	            <td class="registerTableLeftTD">user name</td>
	            <td  class="registerTableRightTD"><input id="name" name="name" placeholder="once set, can't modify" ></td>
	        </tr>
	        <tr>
	            <td  class="registerTip registerTableLeftTD"><img src="pic/pic_simple/logo.png" height = "45px">Enter your password</td>
	            <td></td>
	        </tr>     
	        <tr>
	            <td class="registerTableLeftTD">password</td>
	            <td class="registerTableRightTD"><input id="password" name="password" type="password"  placeholder="the longer, the safer" > </td>
	        </tr>
	        <tr>
	            <td class="registerTableLeftTD">confirm password</td>
	            <td class="registerTableRightTD"><input id="repeatpassword" type="password"   placeholder="re-enter password" > </td>
	        </tr> 
	        <tr>
	        	<td></td>
	        </tr>            
	        <tr>
	            <td colspan="2" class="registerButtonTD">
	                <a href="registerSuccess.jsp"><button style="background-color:green;">Submit</button></a>
	            </td>
	        </tr>             
	    </table>
	</div>
</form>

<br>
<br>
<br>

