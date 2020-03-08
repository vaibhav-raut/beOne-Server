package com.beone.shg.net.springweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
 
public class WebApp extends AbstractAnnotationConfigDispatcherServletInitializer {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebApp.class);

    public WebApp() {
        LOGGER.info("WebApp ======================> Initilized");
    }

	@Override
    protected Class<?>[] getRootConfigClasses() {
        LOGGER.info("WebApp ======================> getRootConfigClasses()");
        return new Class<?>[0];
    }
 
    @Override
    protected Class<?>[] getServletConfigClasses() {
        LOGGER.info("WebApp ======================> getServletConfigClasses()");
        return new Class<?>[]{ WebAppConfig.class };
    }
 
    @Override
    protected String[] getServletMappings() {
        LOGGER.info("WebApp ======================> getServletMappings()");
        return new String[]{ "/" };
    }
    
//    @Override  
//    protected Filter[] getServletFilters() {  
//        LOGGER.info("WebApp ======================> getServletFilters()");
//            return new Filter[] { new HiddenHttpMethodFilter() };  
//    }  
    
//    @Configuration
//    @EnableWebMvc
//    @ComponentScan("springweb.controller")
//    public static class WebAppConfig {
//    }
}