<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<script>
	$(function(){
		$("div.productsAsideCategorys div.row a").each(function(){
			var v = Math.round(Math.random() *6);
			if(v == 1)
				$(this).css("color","#87CEFA");
		});
	});
</script>

<c:forEach items = "${cs}" var = "c">
	<div cid = "${c.id}" class = "productsAsideCategorys">	 
		<c:forEach items="${c.productRows}" var="ps">
			<div class = "row show1">
				<c:forEach items = "${ps}" var = "p">
						<a href="foreproduct?pid=${p.id}">${p.name}</a>
				</c:forEach>
				<div class = "seperator"></div>
			</div>		
		</c:forEach>
	</div>			
</c:forEach>