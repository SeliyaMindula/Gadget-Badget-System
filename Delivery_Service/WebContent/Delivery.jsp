<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student details</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/Delivery.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Delivery Service</h1>
				<form id="formDelivery" name="formDelivery">
						Item ID: <input id="ItemID" name="ItemID" type="text" class="form-control form-control-sm"> 
						<br>
						 Receiver Name:<input id="ReceiverName" name="ReceiverName" type="text" class="form-control form-control-sm"> 
						<br> 
						Receiver Phone No:<input id="ReceiverPhoneNo" name="ReceiverPhoneNo" type="text" class="form-control form-control-sm">
						 <br> 
						Receiver Mail:<input id="ReceiverMail" name="ReceiverMail" type="email" class="form-control form-control-sm"> 
						<br> 
						<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
						<input type="hidden" id="hidDeliveryIDSave" name="hidDeliveryIDSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divDeliveryGrid">
					<%
						Delivery deliveryObj = new Delivery();
					out.print(deliveryObj.readDelivery());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
