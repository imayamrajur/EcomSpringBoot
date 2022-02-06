<%@ page pageEncoding="utf-8"%>

<h2>FORGOT PASSWORD</h2>
<h4>${message}</h4>
<form action="/account/forgot" method="post">
<div class="form-group">
	<label>Username</label>
	<input name="id" class="form-control" > 
</div>
<div class="form-group">
	<label>Email Address</label>
	<input name="email" class="form-control" >
</div>

<div class="form-group">
	<button class="btn btn-default">Retrieve Password</button>
</div>
</form>