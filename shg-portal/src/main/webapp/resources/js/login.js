/**
 * 
 */

$(document).ready(function() {
	$("#submitBtn").click(login);
	$("#district").change(districtChangeHandler);
	$("#groupid").change(groupidChangeHandler);
	$("#memberid").change(memberidChangeHandler);
	var errMsg = $("#errmsg").html();
	if(errMsg !== "")
		alert(errMsg);
});

function districtChangeHandler() {
	var distChars = $("#district").val().split("");
	if (distChars.length != 4 || !isNaN(distChars[0]) || !isNaN(distChars[1])
			|| isNaN(distChars[2]) || isNaN(distChars[3])) {
		$("#errmsg").html("Please enter proper district code with first 2 character and last 2 numbers. eg. MH01");
		$("#district").val("");
	}
}
function groupidChangeHandler() {
	var prefix = "";
	var groupid = $("#groupid").val();
	if (groupid != "") {
		if (isNaN(groupid)) {
			$("#errmsg").html("Please enter numbers only");
			groupid = "";
			$("#groupid").focus();
		} else {
			if (groupid.length > 5) {
				groupid = groupid.substring(0, 5);
			} else {
				for (var i = groupid.length; i < 5; i++)
					prefix += "0";
				groupid = prefix + groupid;
			}
		}
		$("#groupid").val(groupid);
	}
}
function memberidChangeHandler() {
	var prefix = "";
	var memberid = $("#memberid").val();
	if (memberid != "") {
		if (isNaN(memberid)) {
			$("#errmsg").html("Please enter numbers only");
			memberid = "";
			$("#memberid").focus();
		} else {
			if (memberid.length > 4) {
				memberid = memberid.substring(0, 4);
			} else {
				for (var i = memberid.length; i < 4; i++)
					prefix += "0";
				memberid = prefix + memberid;
			}
		}
		$("#memberid").val(memberid);
	}
}
function login() {
	var district = document.myform.district.value;
	var groupid = document.myform.groupid.value;
	var memberid = document.myform.memberid.value;
	var password = document.myform.password.value;
	if (district == "" || groupid == "" || memberid == "" || password == "") {
		$("#errmsg").html("Please enter all details to login");
	} else {
		$("#submitBtn").attr("disabled", "disabled");
		document.myform.action = "login";
		document.myform.submit();
	}
}
