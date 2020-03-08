/**
 * 
 */
var mRoles;

$(document).ready(function() {
	loadCommonProperties();
	$("#lang").val(curLang);
	loadMemberRoles();
});
function loadMemberRoles() {
	ajaxCall("/enum/v1/m_role", "GET", true, "", function(data, status) {
		mRoles = data.enumValues;
	});
}
function getMemberRoleCategory(mrole) {
	var roleCategory = "";
	for (var i = 0; i < mRoles.length; i++) {
		if (mRoles[i].role == mrole) {
			roleCategory = mRoles[i].roleCategory;
			break;
		}
	}
	return roleCategory;
}