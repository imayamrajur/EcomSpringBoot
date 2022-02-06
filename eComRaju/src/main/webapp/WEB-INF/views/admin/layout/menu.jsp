<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="/home/index">Home</a>
		</div>
		<ul class="nav navbar-nav">
			<li class="dropdown">
	        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Manage <span class="caret"></span></a>	     
				       	<ul class="dropdown-menu">
						          <li><a href="/admin/category/index">Category</a></li>
						          <li><a href="/admin/product/index">Product</a></li>
						          <li><a href="/admin/order/index">Order</a></li>
						          <li><a href="/admin/customer/index">Customer</a></li>
				         </ul>
     		 </li>
     		 
     		 <li class="dropdown">
	        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Statistical <span class="caret"></span></a>	     
				       	<ul class="dropdown-menu">
						          <li><a href="/admin/inventory/index">Inventory By Type</a></li>
						          <li><a href="/admin/revenue/category">Sales By Type</a></li>
						          <li><a href="/admin/revenue/customer">Sales By Customer</a></li>
						          <li><a href="/admin/revenue/month">Sales By Month</a></li>
						           <li><a href="/admin/revenue/quarter">Sales By Quarter</a></li>
						          <li><a href="/admin/revenue/year">Sales By Year</a></li>	
				         </ul>
     		 </li>
     		 	<li><a href="/home/feedback">Account</a></li>
		</ul>		
	</div>
</nav>