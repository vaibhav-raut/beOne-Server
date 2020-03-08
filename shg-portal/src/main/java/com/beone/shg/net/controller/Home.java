package com.beone.shg.net.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.beone.shg.net.utils.AccessUtil;

@Controller 
public class Home {
	private final static Logger LOGGER = LoggerFactory.getLogger(Home.class);
//	private final String BASE_URL = "http://www.shg-one.net/shg-ws";
	private final String BASE_URL = "http://localhost/shg-ws";

	@RequestMapping("/")
	public ModelAndView home(HttpSession session) {
		if(session.getAttribute("user") == null)
			return new ModelAndView("login");
		return new ModelAndView("welcome");
	}

	@RequestMapping("/{view}")
	public ModelAndView all(@PathVariable("view") String view, HttpSession session) {
		String errMsg = "";
		if(view.equals("selectgroup"))
			view = "manage";

		Object user = session.getAttribute("user");
		if(!AccessUtil.getAllPages().contains(view)) {
			// User trying to access page which does not exist.
			// Hence remove user info and invalidate the session.
			session.setAttribute("user", null);
			session.setAttribute("requestedView", null);
			session.invalidate();
			if(!view.equals("logout") && user != null)
				errMsg = "Your session is expired. Please login again.";
			return new ModelAndView("login", "errMsg", errMsg);
		} 
		else {
			if(user == null) {
				// save user request and display login page
				session.setAttribute("requestedView", view);
				view = "login";
			}
			else {
				Collection<?> userPages = (Collection<?>) session.getAttribute("userPages");
				if(!userPages.contains(view)){
					// User trying to access page on which has no access.
					// Hence remove user info and invalidate the session.
					session.setAttribute("user", null);
					session.setAttribute("requestedView", null);
					session.invalidate();
					if(!view.equals("logout"))
						errMsg = "Your session is expired. Please login again.";
					return new ModelAndView("login", "errMsg", errMsg);
				}
				else {
					session.setAttribute("requestedView", view);
				}
			}
		}
		return new ModelAndView(view);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam String district, @RequestParam String groupid, 
			@RequestParam String memberid, @RequestParam String password, HttpSession session) {
	
		// Log that request came to WS
		LOGGER.debug("Login (User Account Number:" + district + " - " + groupid + " - " + memberid + ")");

		JSONObject loginRequest = new JSONObject();
		loginRequest.put("memberDistrictCode", district);
		loginRequest.put("groupNo", groupid);
		loginRequest.put("memberNo", memberid);
		loginRequest.put("passcode", password);
		
		HttpPost post = new HttpPost(BASE_URL + "/auth/v1/login");
		post.setHeader("Content-Type", "application/json");
		HttpResponse response = null;
		String errMsg = null;
		try {
			post.setEntity(new StringEntity(loginRequest.toString()));
			response = HttpClientBuilder.create().build().execute(post);
			int responseCode = response.getStatusLine().getStatusCode();
			String entity = EntityUtils.toString(response.getEntity());
			
			if(200 == responseCode) {
				// storing user info into session
				JSONObject userLoginInfo = new JSONObject(entity);
				session.setAttribute("user", userLoginInfo);
				
				HttpGet get = new HttpGet(BASE_URL + "/auth/v1/portal_access/ui_access_codes/Direct_Access_for_Portal_Key");
				get.setHeader("AUTH-Token", userLoginInfo.getString("authToken"));
				get.setHeader("MEM-Ac-No", Long.toString(userLoginInfo.getLong("memberAcNo")));
				get.setHeader("SEL-GRP-Ac-No", Long.toString(userLoginInfo.getLong("selectedGroupAcNo")));
				response = HttpClientBuilder.create().build().execute(get);
				responseCode = response.getStatusLine().getStatusCode();
				entity = EntityUtils.toString(response.getEntity());
				
				if(200 == responseCode) {
					JSONArray uiAccessCodes = new JSONArray(entity);
					List<String> userPages = new ArrayList<>();
					Map<String, Integer> accessMap = new HashMap<>();

					AccessUtil.getUserPageAccess(userLoginInfo, uiAccessCodes, userPages, accessMap);

					if(!userPages.contains(session.getAttribute("requestedView")))
						session.setAttribute("requestedView", userPages.get(0));
					session.setAttribute("userPages", userPages);
					session.setAttribute("uiAccessCodes", accessMap);
					if(userLoginInfo.getJSONObject("member").getInt("passSet") == 0)
						return new ModelAndView("password");
					else
						return new ModelAndView("welcome");
				}
			}
			else {
				if(404 == responseCode) {
					errMsg = "Server is not available.";
				} else {
					try{
						JSONObject json = new JSONObject(entity);
						errMsg = json.getJSONObject("error").getString("message");
						errMsg = errMsg.substring(errMsg.indexOf(":") + 2, errMsg.length());
					} catch (Exception e){
						errMsg = "Server Error (code:" + responseCode + ") Occurred";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			errMsg = e.getMessage();
			LOGGER.error(errMsg);
		}
		Map<String, String> modelMap = new HashMap<>();
		modelMap.put("district", district);
		modelMap.put("groupid", groupid);
		modelMap.put("memberid", memberid);
		modelMap.put("errMsg", errMsg);
		return new ModelAndView("login", modelMap);
	}
	
	@RequestMapping(value = "/selectgroup", method = RequestMethod.POST)
	public ModelAndView selectGroup(@RequestParam String district, @RequestParam String groupid, 
			@RequestParam String password, HttpSession session) {
	
		// Log that request came to WS
		LOGGER.debug("Selecting Group (" + district + " - " + groupid + ")");

		String errMsg = null;
		JSONObject userLoginInfo = (JSONObject) session.getAttribute("user");
		if(userLoginInfo == null) {
			errMsg = "Your session is expired. Please login again.";
			return new ModelAndView("login", "errMsg", errMsg);
		}
		JSONObject loginRequest = new JSONObject();
		loginRequest.put("memberNo", userLoginInfo.getLong("memberAcNo"));
		loginRequest.put("groupDistrictCode", district);
		loginRequest.put("selectedGroupNo", groupid);
		loginRequest.put("passcode", password);
		
		HttpPost post = new HttpPost(BASE_URL + "/auth/v1/select_group");
		post.setHeader("Content-Type", "application/json");
		post.setHeader("AUTH-Token", userLoginInfo.getString("authToken"));
		post.setHeader("MEM-Ac-No", Long.toString(userLoginInfo.getLong("memberAcNo")));
		post.setHeader("SEL-GRP-Ac-No", Long.toString(userLoginInfo.getLong("selectedGroupAcNo")));
		HttpResponse response = null;
		try {
			post.setEntity(new StringEntity(loginRequest.toString()));
			response = HttpClientBuilder.create().build().execute(post);
			int responseCode = response.getStatusLine().getStatusCode();
			String entity = EntityUtils.toString(response.getEntity());
			
			if(200 == responseCode) {
				// storing user info into session
				userLoginInfo = new JSONObject(entity);
				session.setAttribute("user", userLoginInfo);
				
				HttpGet get = new HttpGet(BASE_URL + "/auth/v1/portal_access/ui_access_codes/Direct_Access_for_Portal_Key");
				get.setHeader("AUTH-Token", userLoginInfo.getString("authToken"));
				get.setHeader("MEM-Ac-No", Long.toString(userLoginInfo.getLong("memberAcNo")));
				get.setHeader("SEL-GRP-Ac-No", Long.toString(userLoginInfo.getLong("selectedGroupAcNo")));
				response = HttpClientBuilder.create().build().execute(get);
				responseCode = response.getStatusLine().getStatusCode();
				entity = EntityUtils.toString(response.getEntity());
				
				if(200 == responseCode) {
					JSONArray uiAccessCodes = new JSONArray(entity);
					List<String> userPages = new ArrayList<>();
					Map<String, Integer> accessMap = new HashMap<>();

					AccessUtil.getUserPageAccess(userLoginInfo, uiAccessCodes, userPages, accessMap);

					if(!userPages.contains(session.getAttribute("requestedView")))
						session.setAttribute("requestedView", userPages.get(0));
					session.setAttribute("userPages", userPages);
					session.setAttribute("uiAccessCodes", accessMap);
					return new ModelAndView("welcome");
				}
			}
			else {
				if(404 == responseCode) {
					errMsg = "Server is not available.";
				} else {
					try{
						JSONObject json = new JSONObject(entity);
						errMsg = json.getJSONObject("error").getString("message");
						errMsg = errMsg.substring(errMsg.indexOf(":") + 2, errMsg.length());
					} catch (Exception e){
						errMsg = "Server Error (code:" + responseCode + ") Occurred";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			errMsg = e.getMessage();
			LOGGER.error(errMsg);
		}
		Map<String, String> modelMap = new HashMap<>();
		modelMap.put("district", district);
		modelMap.put("groupid", groupid);
		modelMap.put("errMsg", errMsg);
		return new ModelAndView("manage", modelMap);
	}
}
