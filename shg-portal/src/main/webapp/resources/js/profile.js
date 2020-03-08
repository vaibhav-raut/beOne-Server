/**
 * 
 */
var roles, titles, bankAccTypes, groupTypes;

$(document).ready(function() {
	loadCommonProperties();
	loadMemberRoles();
});
function loadMemberRoles() {
	ajaxCall("/enum/v1/m_role", "GET", true, "", function(data, status) {
		roles = data.enumValues;
		// Don't show my profile if parent group is != to selected group.
		if(gdCode === mdCode && gdId === mdId)
			loadMyProfileTab();
		else
			loadMemberProfileTab();
		loadNameTitles();
	});
}
function loadNameTitles() {
	ajaxCall("/enum/v1/name_title", "GET", true, "", function(data, status) {
		titles = data.enumValues;
		loadBankAccTypes();
	});
}
function loadBankAccTypes() {
	ajaxCall("/enum/v1//enum_values/BankAccountType", "GET", true, "",
			function(data, status) {
				bankAccTypes = data.enumValues;
				loadGroupTypes();
			});
}
function loadGroupTypes() {
	ajaxCall("/enum/v1/group_type", "GET", true, "", function(data, status) {
		groupTypes = data.enumValues;
	});
}
function getMemberRoleCategory(mrole) {
	var roleCategory = "";
	for (var i = 0; i < roles.length; i++) {
		if (roles[i].role == mrole) {
			roleCategory = roles[i].roleCategory;
			break;
		}
	}
	return roleCategory;
}