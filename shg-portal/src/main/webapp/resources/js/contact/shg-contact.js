/**
 * 
 */
$(document).ready(function() {
	loadSHGContactTab();
});

function loadSHGContactTab() {
	if (groupAcProfile != null && groupAcProfile.contacts != null) {
		loadContactDetails(groupAcProfile.contacts[0]);
	} else {
		ajaxCall("/group/v1/group_ac/" + $("#lang option:selected").text()
				+ "/" + groupAcNo, "GET", true, "", function(data, status) {
			groupAcProfile = data;
			if (groupAcProfile.contacts != null)
				loadContactDetails(groupAcProfile.contacts[0]);
		});
	}
}
function loadContactDetails(shgContacts) {
	$("#shg_cont_name").html(shgContacts.name);
	$("#shg_cont_address").html(shgContacts.address);
	$("#shg_cont_address1").html(shgContacts.district + ", " + shgContacts.state);
	$("#shg_cont_phone").html(shgContacts.phone);
	$("#shg_cont_modile").html(shgContacts.priMobile);
	$("#shg_cont_email").html("<a href='mailto:" + shgContacts.email + "'>" + shgContacts.email + "</a>");
}