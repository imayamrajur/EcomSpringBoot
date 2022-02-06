<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<table class="table table-hover">
<thead>
	<tr>
		<th>Id</th>
		<th>Name</th>
		<th>Unit Price</th>
		<th>Discount</th>
		<th>Quantity</th>
		<th>Date</th>
		<th></th>
	</tr>
</thead>
<tbody>
<c:forEach var="e" items="${list }">
	<tr>
		<td>${e.id}</td>
		<td>${e.name}</td>
		<td>${e.unitPrice}</td>
		<td>${e.discount}</td>		
		<td>${e.quantity}</td>
		<td>${e.productDate}</td>
		
		<td>
			<a class="btn btn-sm btn-info" href="${base }/edit/${e.id}">Edit</a>
			<a class="btn btn-sm btn-danger" href="${base }/delete/${e.id}">Delete</a>
		</td>
	</tr>
	</c:forEach>
</tbody>
 
</table>

