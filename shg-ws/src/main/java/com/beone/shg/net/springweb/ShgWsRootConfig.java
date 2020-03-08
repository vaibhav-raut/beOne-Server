package com.beone.shg.net.springweb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.beone.shg.common.web.security.CustomRestAuthFilter;
import com.beone.shg.common.web.security.RestAuthProvider;
import com.beone.shg.common.web.security.role.ControllerListProvider;
import com.beone.shg.common.web.security.role.MySecurityMetadataSource;
import com.beone.shg.common.web.security.role.PropertyHolder;
import com.beone.shg.common.web.security.role.SecuredControllerList;
import com.beone.shg.config.EnableGlobalAuthentication;
import com.beone.shg.net.auth.ShgWSAccessDecisionManager;
import com.beone.shg.net.auth.ShgWsAccessVoter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( securedEnabled = true )
public class ShgWsRootConfig extends WebSecurityConfigurerAdapter implements EnableGlobalAuthentication {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getRestAuthProvider());
    }
        
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
    	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	http.sessionManagement().and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
    	http.sessionManagement().and().authenticationProvider(getRestAuthProvider());
    	http.sessionManagement().and().addFilterBefore(getCustomRestAuthFilter(), BasicAuthenticationFilter.class);
    	
//    	http.authorizeRequests().accessDecisionManager(shgWSAccessDecisionManager());
//    	http.authorizeRequests().anyRequest().authenticated();
    }
    
	@Bean(name="authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return new AuthenticationManagerBuilder(objectPostProcessor).build();	
		return super.authenticationManagerBean();
	}
	
	@Bean(name="authenticationEntryPoint")
	public Http403ForbiddenEntryPoint authenticationEntryPoint() {
		return new Http403ForbiddenEntryPoint();
	}
	
	@Bean(name="customRestAuthFilter")
	public CustomRestAuthFilter getCustomRestAuthFilter() throws Exception {
		return new CustomRestAuthFilter(authenticationManagerBean(), authenticationEntryPoint());
	}
	
	@Bean(name="restAuthProvider")
	public RestAuthProvider getRestAuthProvider() throws Exception {
		return new RestAuthProvider();
	}
	
	@Bean(name="mySecurityMetadataSource")
	public MySecurityMetadataSource getMySecurityMetadataSource() throws Exception {
		return new MySecurityMetadataSource(getPropertyHolder());
	}
	
	@Bean(name="propertyHolder")
	public PropertyHolder getPropertyHolder() throws Exception {
		return new PropertyHolder();
	}
	
	@Bean(name="securedControllerList")
	public SecuredControllerList getSecuredControllerList() throws Exception {
		return new SecuredControllerList(getControllerListProvider());
	}
	
	@Bean(name="controllerListProvider")
	public ControllerListProvider getControllerListProvider() throws Exception {
		return new ControllerListProvider();
	}
	
	@Bean(name="shgWSAccessDecisionManager")
	public ShgWSAccessDecisionManager shgWSAccessDecisionManager() {
		List<AccessDecisionVoter> decisionVoters = new ArrayList<AccessDecisionVoter>();
		decisionVoters.add(new ShgWsAccessVoter());
		return new ShgWSAccessDecisionManager(decisionVoters);
	}
	
	@Bean(name="methodSecurityInterceptor")
	public MethodSecurityInterceptor methodSecurityInterceptor() throws Exception {
		MethodSecurityInterceptor interceptor = new MethodSecurityInterceptor();
		interceptor.setSecurityMetadataSource(getMySecurityMetadataSource());
		interceptor.setAuthenticationManager(authenticationManagerBean());
		interceptor.setAccessDecisionManager(shgWSAccessDecisionManager());
		return interceptor;
	}
	
	@Bean(name="autoProxyCreator")
	public BeanNameAutoProxyCreator autoProxyCreator() throws Exception {
		BeanNameAutoProxyCreator autoProxyCreator = new BeanNameAutoProxyCreator();
		
		String[] interceptorNames = new String[1];
		interceptorNames[0] = "methodSecurityInterceptor";
		autoProxyCreator.setInterceptorNames(interceptorNames);
		SecuredControllerList securedControllerList = getSecuredControllerList();
		
		String[] beanNames = new String[securedControllerList.size()];
		for(int i = 0; i < securedControllerList.size(); i++) {
			beanNames[i] = securedControllerList.get(i);
		}
		autoProxyCreator.setBeanNames(beanNames);
		
		return autoProxyCreator;
	}
}