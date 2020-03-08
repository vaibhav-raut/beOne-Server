package com.beone.shg.net.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class AccessUtil {
	
	private static final List<String> ALL_PAGES = new ArrayList<>();
	public enum ACCESS_LEVEL {
		NO_ACCESS, READ, UPDATE, APPROVE
	}

	static {
		ALL_PAGES.add("manage");
		ALL_PAGES.add("dashboard");
		ALL_PAGES.add("transaction");
		ALL_PAGES.add("account");
		ALL_PAGES.add("reports");
		ALL_PAGES.add("application");
		ALL_PAGES.add("bank");
		ALL_PAGES.add("purchase");
		ALL_PAGES.add("profile");
		ALL_PAGES.add("contact");
	}
	
	public static List<String> getAllPages() {
		return ALL_PAGES;
	}

	public static void getUserPageAccess(JSONObject userLoginInfo, JSONArray uiAccessCodes, 
			List<String> userPages, Map<String, Integer> accessMap) {
		
		String groupType = userLoginInfo.getString("groupType");
		JSONObject member = userLoginInfo.getJSONObject("member");
		String memberRoleCat = member.getString("roleCategory");
		
		Set<String> pages = new HashSet<>();
		// profile and contact page will be for all user
		pages.add("profile");
//		pages.add("contact");
		
		// if user group Type = SHG-One Hub Purchase, show Purchase invoice page
//		if("SHG-One Hub Purchase".equals(groupType)) {
			pages.add("purchase");
//		}
		
		for (int i = 0; i < uiAccessCodes.length(); i++) {
			
			JSONObject accessCode = uiAccessCodes.getJSONObject(i);
			String code = accessCode.getString("accessCode");
			String levelStr = accessCode.getString("accessLevel");
			int level = 1;
			if(levelStr.equals(ACCESS_LEVEL.UPDATE.toString()))
				level = 2;
			else if(levelStr.equals(ACCESS_LEVEL.APPROVE.toString()))
				level = 3;
			
			if(!levelStr.equals(ACCESS_LEVEL.NO_ACCESS.toString())) {
				
				if(code.equals("MANAGE_SHG") || code.equals("ACCESS_RIGHTS")) {
					pages.add("manage");
					accessMap.put(accessCode.getString("accessCode"), level);
				}
				if(code.equals("MANAGE_SHG")) {
					pages.add("application");
					accessMap.put(accessCode.getString("accessCode"), level);
				}
				if(code.equals("SHG_GROUP_AC")) {
					pages.add("profile");
					accessMap.put(accessCode.getString("accessCode"), level);
				}
				if(code.equals("MEMBER_AC")) {
					pages.add("application");
					accessMap.put(accessCode.getString("accessCode"), level);
				}
				
				if("SHG".equals(groupType)) {
					
					if(code.equals("SHG_GROUP_RULES")) {
						pages.add("manage");
						accessMap.put(accessCode.getString("accessCode"), level);
					}
					else if(code.equals("GROUP_DASHBOARD") || code.equals("MEMBER_WISE_DASHBOARD")) {
						pages.add("dashboard");
						accessMap.put(accessCode.getString("accessCode"), level);
					}
					else if(code.equals("MEMBER_DASHBOARD") && "SHG Member".equals(memberRoleCat)) {
						pages.add("dashboard");
						accessMap.put(accessCode.getString("accessCode"), level);
					}
					else if(code.equals("ADD_TRANSACTION")) {
						pages.add("transaction");
						accessMap.put(accessCode.getString("accessCode"), level);
					}
					else if(code.equals("MEMBER_ACCOUNT_BOOK") || code.equals("GROUP_ACCOUNT_BOOK") || code.equals("GROUP_ACCOUNT_TRACKING")) {
						pages.add("account");
						accessMap.put(accessCode.getString("accessCode"), level);
					}
					else if(code.equals("REPORTS")) {
						pages.add("reports");
						accessMap.put(accessCode.getString("accessCode"), level);
					}
					else if(code.equals("MEMBER_LOAN_AC")) {
						pages.add("application");
						accessMap.put(accessCode.getString("accessCode"), level);
					}
					else if(code.equals("BANK_INTERACTION")) {
						pages.add("bank");
						accessMap.put(accessCode.getString("accessCode"), level);
					}
					
					if(code.equals("MEMBER_AC")) {
						pages.add("profile");
						accessMap.put(accessCode.getString("accessCode"), level);
					}
					if(code.equals("GROUP_DASHBOARD")) {
						pages.add("contact");
						accessMap.put(accessCode.getString("accessCode"), level);
					}
				}
			}
		}
		// Correcting page sequence
		for (String page : ALL_PAGES) {
			if(pages.contains(page))
				userPages.add(page);
		}
	}
}
