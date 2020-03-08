package com.beone.shg.net.springweb;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration  
@EnableWebMvc  
@ComponentScan(basePackages = {"com.beone.shg.net"})  
public class WebAppConfig extends WebMvcConfigurerAdapter {  

	private final static Logger LOGGER = LoggerFactory.getLogger(WebAppConfig.class);

	public WebAppConfig() {
		LOGGER.info("WebAppConfig ======================> Initilized");
	}

//	@Resource  
//	private Environment env;  

	@Override  
	public void addResourceHandlers(ResourceHandlerRegistry registry) {  
		LOGGER.info("WebAppConfig ======================> addResourceHandlers()");
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");  
	}  

	@Override  
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {  
		LOGGER.info("WebAppConfig ======================> configureContentNegotiation()");
		configurer.favorPathExtension(true)  
		.useJaf(false)  
		.ignoreAcceptHeader(true)  
		.mediaType("html", MediaType.TEXT_HTML)  
		.mediaType("json", MediaType.APPLICATION_JSON)
		.defaultContentType(MediaType.TEXT_HTML);  
	}  

	@Bean  
	public ViewResolver contentNegotiatingViewResolver(  
			ContentNegotiationManager manager) {  
		LOGGER.info("WebAppConfig ======================> contentNegotiatingViewResolver()");

		List< ViewResolver > resolvers = new ArrayList< ViewResolver >();  

		InternalResourceViewResolver r1 = new InternalResourceViewResolver();  
		r1.setPrefix("/WEB-INF/jsp/");  
		r1.setSuffix(".jsp");  
		r1.setViewClass(JstlView.class);  
		resolvers.add(r1);  

//		JsonViewResolver r2 = new JsonViewResolver();  
//		resolvers.add(r2);  

		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();  
		resolver.setViewResolvers(resolvers);  
		resolver.setContentNegotiationManager(manager);  
		return resolver;  

	}  
//
//	/**  
//	 * View resolver for returning JSON in a view-based system. Always returns a  
//	 * {@link MappingJacksonJsonView}.  
//	 */  
//	public class JsonViewResolver implements ViewResolver {  
//
//		public View resolveViewName(String viewName, Locale locale)  
//				throws Exception {  
//			LOGGER.info("WebAppConfig ======================> JsonViewResolver.resolveViewName()");
//			MappingJackson2JsonView view = new MappingJackson2JsonView();  
//			view.setPrettyPrint(true);  
//			return view;  
//		}  
//	}  

} 