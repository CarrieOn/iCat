<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<div class="categorySortBar"style="postion:relative;margin-left:-30px;"> 
    <table class="categorySortBarTable categorySortTable">
        <tr>
        	<td class="grayColumn"><b>- Sort -</b></td>          
            <td class="grayColumn" style="background:white;"><a href="?cid=${c.id}&sort=all">Default<span class="glyphicon glyphicon-arrow-down"></span></a></td>
            <td class="grayColumn" style="background:white;"><a href="?cid=${c.id}&sort=price">Price<span class="glyphicon glyphicon-arrow-down"></span></a></td>
          	<td class="grayColumn" style="background:white;"><a href="?cid=${c.id}&sort=date">Date<span class="glyphicon glyphicon-arrow-down"></span></a></td>
          </tr>
    </table>
</div>