<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<c:set var="cart" value="${sessionScope['scopedTarget.cartService'] }"/>
<div class="panel panel-default">
	<div class="panel-heading">
		<s:message code="lyt.cart.title"/>
	</div>
	<div class="panel-body">
		<img id="cart-img" src="/static/imagies/shoppingcart.png" class="col-sm-5">
		<ul class="col-sm-7">
			<li><b id="cart-cnt">${cart.count }</b>Items </li>
			<li><b id="cart-amt">
				<f:formatNumber value="${cart.amount }" pattern="#,##,###.00" />
			</b> Rs</li>
			<li>
			 <a href="/cart/view">Cart View</a>
			</li>
		
		</ul>
	</div>
</div>

<div class="panel panel-default">
	<div class="panel-heading">SEARCH</div>
	<div class="panel-body">
		<form action="/product/list-by-keywords" method="post">
			<input value="${param.keywords }"  name="keywords" class="form-control" placeholder="Keywords">
	   </form>
	</div>
</div>

<div class="panel panel-default">
	<div class="panel-heading">
		<s:message code="lyt.cate.title"/>
	</div>
	<div class="list-group">
	<c:set value="${pageContext.response.locale.language }" var="lang" />
			<c:forEach var="c" items="${cates}">
			<a href="/product/list-by-category/${c.id }"  class="list-group-item"> ${(lang=='ta')?c.nameVN:c.name}</a> 
		</c:forEach>
	</div>
	</div>

<div class="panel panel-default">
	<div class="panel-heading">SPECIAL</div>
	<div class="list-group">
		<a href="/product/list-by-special/0"  class="list-group-item">New Products</a> 
		<a href="/product/list-by-special/1"  class="list-group-item">Friend</a> 
		<a href="/product/list-by-special/2"  class="list-group-item">More Views</a>
		<a href="/product/list-by-special/3"  class="list-group-item">Discount</a>
		<a href="/product/list-by-special/4"  class="list-group-item">Special</a>
	</div>
</div>

<style id="cart-css"></style>
