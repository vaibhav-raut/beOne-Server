package com.beone.shg.common.web.security.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;

public class ValidateSecuritySetup {

	private static List<String> securedControllerList;
	private static Map<String,Set<String>> classesAndMethods = new HashMap<String, Set<String>>();

	private static String[] controllerBasePackages;
	private static Logger logger = LoggerFactory.getLogger(ValidateSecuritySetup.class); 


	public static String[] getControllerBasePackages() {
		return controllerBasePackages;
	}

	public static void setControllerBasePackages(String[] controllerBasePackages) {
		ValidateSecuritySetup.controllerBasePackages = controllerBasePackages;
	}

	public static List<String> getSecuredControllerList() {
		return securedControllerList;
	}

	public static void setSecuredControllerList(List<String> securedControllerList) {
		ValidateSecuritySetup.securedControllerList = securedControllerList;
	}

	public static Map<String, Set<String>> getClassesAndMethods() {
		return ValidateSecuritySetup.classesAndMethods;
	}

	public static void setClassesAndMethods(Map<String, Set<String>> classesAndMethods) {
		ValidateSecuritySetup.classesAndMethods = classesAndMethods;
	}

	public static List<Class> scanForControllers() {
		List<Class> controllers = new ArrayList<Class>();
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Controller.class));

		/* This loop should be un-commented and used instead of the one below, if securedController.properties is used again */
		/*for (String controllerBasePackage : controllerBasePackages) {  
		for (String controllerBasePackage : controllerBasePackages) {
			for (BeanDefinition bd : scanner.findCandidateComponents(controllerBasePackage)) {
				try {
					controllers.add(Class.forName(bd.getBeanClassName()));
				} catch (ClassNotFoundException ex) {
				}
			}
		}
		 */
		// Looking for controllers in the whole classpath
		for (BeanDefinition bd : scanner.findCandidateComponents("")) {
			try {
				controllers.add(Class.forName(bd.getBeanClassName()));
			} catch (ClassNotFoundException ex) {
			}
		}

		return controllers;
	}

	public static void validate() throws ClassNotFoundException{
		Set<String> classNames = classesAndMethods.keySet();

		logger.debug("securedControllerList:"+securedControllerList);

		List<Class> controllers = scanForControllers();

		logger.debug("All controllers:"+controllers);

		for(Class controller:controllers){
			if(!classNames.contains(controller.getName())){
				Annotation annotation = controller.getAnnotation(UnsecuredController.class);

				if(annotation==null){
					logger.error("Entry for "+controller+" is not present in securedController.properties! Methods in this class will not be secured! Please use @UnsecuredController annotation of this is purposeful.");
					throw new RuntimeException("Entry for "+controller+" is not present in securedController.properties! Methods in this class will not be secured! Please use @UnsecuredController annotation of this is purposeful.");
				}else{
					UnsecuredController unsecuredController = (UnsecuredController)annotation;
					logger.warn("WARNING:"+controller+" marked as @UnsecuredController! Methods in this class will not be secured! \nJustification:"+unsecuredController.justification());
				}
			}
		}

		for(String className:classNames){

			String classNameKey = className.substring(className.lastIndexOf('.')+1,className.length()); 
//			classNameKey = classNameKey.substring(0,1).toLowerCase() + classNameKey.substring(1,classNameKey.length());

			if(securedControllerList.indexOf(classNameKey)==-1){
				logger.error("Entry for "+className+" is not present in securedController.properties but its methods are listed in methods.properties! Methods in this class will not be secured! Please make relevant controller name entry in controller.properties ");
				throw new RuntimeException("Entry for "+className+" is not present in securedController.properties but its methods are listed in methods.properties! Methods in this class will not be secured! Please make relevant controller name entry in controller.properties ");
			}

			Set<String> methodsInProperties = classesAndMethods.get(className);

			logger.debug(className+"'s "+"Methods:"+methodsInProperties);

			Class controllerClass = Class.forName(className);
			Method[] allMethods = controllerClass.getDeclaredMethods();
			for(Method method:allMethods){
				String fullMethodName = className+"."+method.getName();

				if(!methodsInProperties.contains(fullMethodName)){
					Annotation annotation = method.getAnnotation(UnsecuredMethod.class);
					if(annotation==null){
						logger.error("Entry for "+fullMethodName+" is not present in method.properties! This method will not be secured! Please use @UnsecuredMethod annotation of this is purposeful.");
						throw new RuntimeException("Entry for "+fullMethodName+" is not present in method.properties! This method will not be secured! Please use @UnsecuredMethod annotation of this is purposeful.");
					}else{
						UnsecuredMethod unsecuredMethod = (UnsecuredMethod)annotation;
						logger.warn("WARNING:"+fullMethodName+" marked as @UnsecuredMethod! Calls to this method will not be secured! Justification: "+unsecuredMethod.justification());
					}
				}
			}

		}

	}
}
