/**
 * 
 */
var titles, bankAccTypes, searchedMembers, isMemberReg = true, memberAttachments = new Array();

$(document).ready(function() {
	$("#item_tag_print").click(printTable);
	$("#item_tag_pdf").click(printTable);
});