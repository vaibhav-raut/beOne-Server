package com.beone.shg.common.web.security;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class AuthRequestWrapper extends HttpServletRequestWrapper {

	protected HashMap<String,String> headers;
	public AuthRequestWrapper(HttpServletRequest request) {
		super(request);
		headers = new HashMap<String,String>();
	}

	public String getHeader(String name) {
		 HttpServletRequest request = (HttpServletRequest)getRequest(); 
		String ret = null;
		ret = headers.get(name);
		if(ret == null) {
			ret = request.getHeader(name);
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public Enumeration getHeaderNames() {
		@SuppressWarnings("rawtypes")
		
		List list = new ArrayList();  
		//loop over request headers from wrapped request object
		HttpServletRequest request = (HttpServletRequest)getRequest();
		Enumeration e = request.getHeaderNames();
		while(e.hasMoreElements()) {
			//add the names of the request headers into the list
			String n = (String)e.nextElement();
			list.add(n);
		}
		Iterator<String>it = headers.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			if(!list.contains(key)) {
				list.add(key);
			}
		}
		return Collections.enumeration(list);
	}
	
	public void setHeader(String name, String value) {
		headers.put(name, value);
	}
}
