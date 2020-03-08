package com.beone.shg.net.springweb;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
 
public class ShgWsWebApp extends AbstractAnnotationConfigDispatcherServletInitializer {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShgWsWebApp.class);

    public ShgWsWebApp() {
        LOGGER.info("WebApp ======================> Initilized");
    }

	@Override
    protected Class<?>[] getRootConfigClasses() {
        LOGGER.info("WebApp ======================> getRootConfigClasses()");
        return new Class[] { ShgWsRootConfig.class };
    }
 
    @Override
    protected Class<?>[] getServletConfigClasses() {
        LOGGER.info("WebApp ======================> getServletConfigClasses()");
        return new Class[] { ShgWsWebAppConfig.class };
    }
 
    @Override
    protected String[] getServletMappings() {
        LOGGER.info("WebApp ======================> getServletMappings()");
        return new String[] { "/" };
    }
    
    @Override  
    protected Filter[] getServletFilters() {  
        LOGGER.info("WebApp ======================> getServletFilters()");
            return new Filter[] { new DelegatingFilterProxy("springSecurityFilterChain") };  
    }  
}