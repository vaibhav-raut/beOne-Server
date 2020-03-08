package com.beone.shg.common.web.security.role;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.beone.shg.common.web.security.annotation.ValidateSecuritySetup;

public class ControllerListProvider {
	
	private Logger logger = LoggerFactory.getLogger(PropertyHolder.class);	
	private Properties controllerProperties = new Properties();
	
	public Properties getControllerProperties() {
		return controllerProperties;
	}

	public void setControllerProperties(Properties controllerProperties) {
		this.controllerProperties = controllerProperties;
	}

	public void loadControllerProperties() {		
		try {
			InputStream inStream = getClass().getResourceAsStream("/securedController.properties");
			controllerProperties.load(inStream);
						
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public List<String> getControllerList() {
		// Load Properties from file
		loadControllerProperties();
		
		logger.info("ControllerListProvider:start");
		List<String> securedControllerList = new ArrayList<String>();
		
		String value = controllerProperties.getProperty("controllerList");
		String[] tokens = StringUtils.commaDelimitedListToStringArray(value);
		for (String token : tokens) {
			logger.info("ControllerListProvider:token = "+token);
			securedControllerList.add(token);
		}
		
		/* These lines should be un-commented if securedController.properties is used again */
		//String[] controllerBasePackages = controllerProperties.getProperty("controllerBasePackages").split(",");
		//ValidateSecuritySetup.setControllerBasePackages(controllerBasePackages);
		
		logger.info("ControllerListProvider:end");
		ValidateSecuritySetup.setSecuredControllerList(securedControllerList);
		return securedControllerList;
	}
}
