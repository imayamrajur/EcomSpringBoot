<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="/home/index">
				<s:message code="lyt.menu.home"/>
			</a>
		</div>
		<ul class="nav navbar-nav">
			<li><a href="/home/about">About Us</a></li>
			<li><a href="/home/contact">Contact Us</a></li>
			<li><a href="/home/feedback">Feedback</a></li>
			<li><a href="/home/faq">FAQs</a></li>
			<li class="dropdown">
	        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Account <span class="caret"></span></a>
	       	<c:choose>	
	       		 <c:when test="${empty sessionScope.user }">
				       	<ul class="dropdown-menu">
						          <li><a href="/account/login">Login</a></li>
						          <li><a href="/account/register">Register</a></li>
						          <li><a href="/account/forgot">Forgot Password</a></li>
				         </ul>				       		 
	       		 </c:when>
	       	<c:otherwise>
	       		<ul class="dropdown-menu">
			          <li><a href="/account/change">Change Password</a></li>
			          <li><a href="/account/edit">Account Update</a></li>
			           <li><a href="/order/list">Order</a></li>
			           <li><a href="/order/items">Items</a></li>
			           <li><a href="/account/logoff">LogOut</a></li>
	    	    </ul>
	       	</c:otherwise>
	   	</c:choose>
	       
      </li>
	</ul>
		<ul class="nav navbar-nav navbar-right">
			<li><a href="#" data-lang="ta">Tamil</a></li>
			<li><a href="#" data-lang="en">English</a></li>
		</ul>
	</div>
</nav>
<script>
	$(function(){
		$('a[data-lang]').click(function(){
			var lang = $(this).attr("data-lang");
			$.ajax({
				url:'/home/language?lang=' +lang,
						success: function(){
							location.reload();
						}						
			});
			return false;
		});
		
	});
</script>