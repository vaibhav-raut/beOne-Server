package com.beone.shg.common.web.security.role;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.util.StringUtils;

import com.beone.shg.common.web.security.annotation.ValidateSecuritySetup;

public class PropertyHolder {
	
	private Logger logger = LoggerFactory.getLogger(PropertyHolder.class);
	private Properties methodProperties = new Properties();	

	public void setMethodProperties(Properties methodProperties) {
		this.methodProperties = methodProperties;
	}

	public Properties getMethodProperties() {
		return methodProperties;
	}

	public void loadMethodProperties() {		
		try {
			InputStream inStream = getClass().getResourceAsStream("/methods.properties");
			methodProperties.load(inStream);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public Map<String, List<ConfigAttribute>> getMethodMap() {
		// Load Properties from file
		loadMethodProperties();
		
		String className = "";
		String methodEntry = "";
		Map<String, List<ConfigAttribute>> methodMap = new HashMap<String, List<ConfigAttribute>>();
		Map<String,Set<String>> classesAndMethods = new HashMap<String, Set<String>>();
		Set<String> methods = new HashSet<String>();
		
		for (Iterator iter = methodProperties.keySet().iterator(); iter
				.hasNext();) {
			String name = (String) iter.next();
			
			// The following exception handling code is to avoid loading of Method Security related target classes being loaded multiple time out of context
			try {
				methodEntry = name;
				className = name.subSequence(0, name.lastIndexOf('.')).toString();
				logger.info("PropertyHolder:Trying to load class with Class Name:"+className);
				Class.forName(className);
				
			} catch (ClassNotFoundException e) {
				logger.info("PropertyHolder:-Class Not Found, this is OK: Skipping adding to security context");
				continue;
			}
			
			String value = methodProperties.getProperty(name);

			String[] tokens = StringUtils
					.commaDelimitedListToStringArray(value);
			List<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>(
					tokens.length);

			for (String token : tokens) {
				attributes.add(new SecurityConfig(token));
			}

			methodMap.put(name, attributes);
			
			
			if(classesAndMethods.containsKey(className)){
				methods = classesAndMethods.get(className);
				methods.add(methodEntry);
				classesAndMethods.put(className, methods);
			}else{
				methods = new HashSet<String>();
				methods.add(methodEntry);
				classesAndMethods.put(className, methods);
			}
			
			
		}
		ValidateSecuritySetup.setClassesAndMethods(classesAndMethods);
		
		try {
			ValidateSecuritySetup.validate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return methodMap;
	}
}