<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt"  prefix="f" %>

<div class="row">
	<div class="col-sm-5 text-center">
		<img class="detail-img"  src="/static/imagies/products/${prod.image }">
	</div>
	<div class="col-sm-7">
		<ul class="detail-info">
			<li>Name : ${prod.name }</li>
			<li>Price : <f:formatNumber value="${prod.unitPrice }" pattern="##,##,##,###.00" />Rs </li>
			<li>Product Date : <f:formatDate value="${prod.productDate }" pattern="dd-MM-yyyy" /> </li>
			<li>Category : ${prod.category.nameVN }</li>
			<li>Quantity : ${prod.quantity }</li>
			<li>Discount : <f:formatNumber value="${prod.discount }" type="percent" /> </li>
			<li>View Count : ${prod.viewCount }</li>
			<li>Available : ${prod.available?'Yes':'No' }</li>
			<li>Special : ${prod.special?'Yes':'No' }</li>
		</ul>
	</div>
</div>
<div class="text-justify">${prod.description }</div>

<ul class="nav nav-tabs">
  <li class="active"><a data-toggle="tab" href="#tab1">Product List</a></li>
  <li><a data-toggle="tab" href="#tab2">Favorite</a></li>
  <li><a data-toggle="tab" href="#tab3">Viewed</a></li>
</ul>

<div class="tab-content">
  <div id="tab1" class="tab-pane fade in active">
    <div>
    		<c:forEach var="p"  items="${list}">
				<a href="/product/detail/${p.id }">
					<img class="thumb-img"  src="/static/imagies/products/${p.image }">
				</a>
			</c:forEach>
    </div>
  </div>
  <div id="tab2" class="tab-pane fade">
   	<div>
   		<c:forEach var="p"  items="${favo}">
				<a href="/product/detail/${p.id }">
					<img class="thumb-img"  src="/static/imagies/products/${p.image }">
				</a>
		</c:forEach>
   	</div>
  </div>
  <div id="tab3" class="tab-pane fade">
    	<div>
    		<c:forEach var="p"  items="${viewed}">
					<a href="/product/detail/${p.id }">
						<img class="thumb-img"  src="/static/imagies/products/${p.image }">
					</a>
			</c:forEach>
    	</div>
  </div>
</div>
