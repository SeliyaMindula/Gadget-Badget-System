$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateDeliveryForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	//If valid------------------------
	var type = ($("#hidDeliveryIDSave").val() == "") ? "POST" : "PUT";
	$.ajax({
		url : "DeliveryAPI",
		type : type,
		data : $("#formDelivery").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onDeliverySaveComplete(response.responseText, status);
		}
	});
});
// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidDeliveryIDSave").val($(this).data("deliveryid"));
	$("#ItemID").val($(this).closest("tr").find('td:eq(0)').text());
	$("#ReceiverName").val($(this).closest("tr").find('td:eq(1)').text());
	$("#ReceiverPhoneNo").val($(this).closest("tr").find('td:eq(2)').text());
	$("#ReceiverMail").val($(this).closest("tr").find('td:eq(3)').text());
});

//Delete

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "DeliveryAPI",
		type : "DELETE",
		data : "DeliveryID=" + $(this).data("deliveryid"),
		dataType : "text",
		complete : function(response, status) {
			onDeliveryDeleteComplete(response.responseText, status);
		}
	});
});
// CLIENT-MODEL================================================================
function validateDeliveryForm() {
	// ID
	if ($("#ItemID").val().trim() == "") {
		return "Insert Item ID.";
	}
	// NAME
	if ($("#ReceiverName").val().trim() == "") {
		return "Insert Receiver Name.";
	}

	//PHONE-------------------------------
	if ($("#ReceiverPhoneNo").val().trim() == "") {
		return "Insert Receiver Phone No.";
	}
	if (!validatePhone($("#ReceiverPhoneNo").val().trim())) {
			return "Insert Valid Phone Number.";
		}
	

	
	//Mail------------------------
	if ($("#ReceiverMail").val().trim() == "") {
		return "Insert Receiver Mail.";
	}
	var email = $("#ReceiverMail").val().trim();
		if (!validateEmail(email)) {
		return "Insert Valid Email.";
		}
	return true;
	
}


function validatePhone(phone) 
{
    var re = /^(\91-|\91|0)?\d{9}$/;
    return re.test(phone);
}

function validateEmail(email) 
{
    var re = /\S+@\S+\.\S+/;
    return re.test(email);
}




function onDeliverySaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divDeliveryGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidDeliveryIDSave").val("");
	$("#formDelivery")[0].reset().Status();
}

function onDeliveryDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divDeliveryGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}