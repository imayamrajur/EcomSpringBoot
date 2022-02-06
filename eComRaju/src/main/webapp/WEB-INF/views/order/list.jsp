<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<h2>ORDER LIST</h2>
<table class="table">
<thead>
	<tr>
		<th>Id</th>
		<th>Order Date</th>
		<th>Address</th>
		<th>Amount</th>
		<th></th>
	</tr>
</thead>
<tbody>
<c:forEach var="o" items="${orders}">
	<tr>
		<td>${o.id }</td>
		<td>${o.orderDate }</td>
		<td>${o.address }</td>
		<td>${o.amount }</td>
		<td>
			<a href="/order/detail/${o.id }" class="btn btn-sm btn-warning">Detail
				
			</a>
		</td>
	</tr>
	</c:forEach>
</tbody>
</table>
	