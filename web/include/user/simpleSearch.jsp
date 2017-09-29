<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<div>
	<a href = "forehome">
		<img id="logo" src="pic/home/Garfield.gif" height = "80px" class="logo">
	</a>
	
	<form action="foresearch" method="post" >	
			<div class="simpleSearchDiv pull-right">
				<input name="keyword" type="text" value="food" placeholder="cat dog">
				<button  type="submit" class="searchButton">search</button>
			</div>
	</form>
	<div style="clear:both"></div>
</div>