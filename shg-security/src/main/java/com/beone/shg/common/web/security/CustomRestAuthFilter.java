package com.beone.shg.common.web.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;

//import com.beone.shg.common.web.security.config.SecurityProperty;
//import com.beone.shg.config.EnvConfig;

public class CustomRestAuthFilter extends GenericFilterBean {

	//	@Resource(name = "securityConfig")
	//	private EnvConfig<SecurityProperty> config;

	private AuthenticationManager authenticationManager;
	private AuthenticationEntryPoint authenticationEntryPoint;

	private String authToken = "AUTH-Token";
	private String mAcNoHeader = "MEM-Ac-No";
	private String selGAcNoHeader = "SEL-GRP-Ac-No";
	private RestTemplate restTemplate;
	private static Logger LOGGER = LoggerFactory.getLogger(RestAuthProvider.class);

	public CustomRestAuthFilter(AuthenticationManager authenticationManager) {
		this(authenticationManager, new Http403ForbiddenEntryPoint());

	}

	public CustomRestAuthFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
		this.authenticationManager = authenticationManager;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	protected void initFilterBean() throws ServletException {
		//		authToken = config.getStringValue(SecurityProperty.AUTH_TOKEN);
		//		mAcNoHeader = config.getStringValue(SecurityProperty.MEMBER_AC_NO);
		//		selGAcNoHeader = config.getStringValue(SecurityProperty.SELECTED_GROUP_AC_NO);

		restTemplate = new RestTemplate();

		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>(restTemplate.getMessageConverters());
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		converters.add(jsonConverter);
		restTemplate.setMessageConverters(converters);

		LOGGER.debug("authToken=" + authToken);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

		LOGGER.debug("CustomRestAuthFilter.doFilter():start");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String token = request.getHeader(authToken);
		String memberAcNoStr = request.getHeader(mAcNoHeader);
		String selectedGroupAcNoStr = request.getHeader(selGAcNoHeader);

		LOGGER.debug("Request Header value [ " + authToken + ":" + token +
				mAcNoHeader + ":" + memberAcNoStr +
				selGAcNoHeader + ":" + selectedGroupAcNoStr + " ]");

		// If the Authorization token is null, let the
		// ExceptionTranslationFilter provided by
		// the <http> namespace kick of the Http403ForbiddenEntryPoint to send
		// "Forbidden" response
		BufferedRequestWrapper bufferedRequest = new BufferedRequestWrapper(request);
		if (token == null || memberAcNoStr == null) {

			String requestUrl = bufferedRequest.getRequestURI();
			if(requestUrl.contains("/login") ||
					requestUrl.contains("/add_default_groups") ||
					requestUrl.contains("/sms_pass/") ||
					requestUrl.contains("Temp_file_folder")) {
				LOGGER.info("No authentication needed for login.");
				chain.doFilter(bufferedRequest, response);
				return;
			}

			if(token == null){
				LOGGER.error("No authentication header. Token is null. Authentication Failure");
//				handleException(request, response, 
//						new ShgAuthenticationException("Token is null. Authentication Failure"));
				throw new ShgAuthenticationException("Token is null. Authentication Failure");
			}            
			if(memberAcNoStr == null){
				LOGGER.error("No authentication header. memberAcNo is null. Authentication Failure");
//				handleException(request, response, 
//						new ShgAuthenticationException("memberAcNo is null. Authentication Failure"));
				throw new ShgAuthenticationException("memberAcNo is null. Authentication Failure");
			}
			
		} else {
			request = bufferedRequest;
			Authentication authentication = null;
			
			if(selectedGroupAcNoStr == null) {
				authentication = new ShgAuthToken(token, 
						Long.parseLong(memberAcNoStr),
						-1l);
			} else {
				authentication = new ShgAuthToken(token, 
						Long.parseLong(memberAcNoStr),
						Long.parseLong(selectedGroupAcNoStr));
			}
			
			try {
				// Request the authentication manager to authenticate the token
				Authentication successfulAuthentication = authenticationManager.authenticate(authentication);
				// Pass the successful token to the SecurityHolder where it can be
				// retrieved by this thread at any stage.
				SecurityContextHolder.getContext().setAuthentication(successfulAuthentication);

				// Continue with the Filters
				if (successfulAuthentication instanceof ShgAuthToken) {
					request = generateAuthRequest(request, ((ShgAuthToken) successfulAuthentication));
				}
				LOGGER.debug("Sending request to the chain");

				chain.doFilter(request, response);

			} catch (SelectGroupInvalidException selectGroupInvalidException) {
				// set user name in case of expired token

				if(bufferedRequest.getRequestURI().contains("/select_group")){
					LOGGER.info("No authentication needed for select_group.");
					chain.doFilter(bufferedRequest, response);
					return;
				}
				AuthRequestWrapper requestWrapper = generateAuthRequest(request, Long.parseLong(memberAcNoStr));
//				handleException(requestWrapper, response, selectGroupInvalidException);
				throw selectGroupInvalidException;
			} 
			catch (ShgTokenExpiredException tokenExpiredException) {
				// set user name in case of expired token
				AuthRequestWrapper requestWrapper = generateAuthRequest(request, Long.parseLong(memberAcNoStr));
//				handleException(requestWrapper, response, tokenExpiredException);
				throw tokenExpiredException;
			} catch (AuthenticationException authenticationException) {
//				handleException(request, response, authenticationException);
				throw authenticationException;
			}
		}
		LOGGER.debug("CustomRestAuthFilter.doFilter():end");
	}

	/**
	 * Helper function to centralize exception handling
	 * 
	 * @param request
	 * @param response
	 * @param exception
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleException(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		// If it fails clear this threads context and kick off the
		// authentication entry point process.
		SecurityContextHolder.clearContext();
		response.setHeader("reasonForFailure", exception.getMessage());
		LOGGER.error("Authentication Failure: reasonForFailure:" + exception.getMessage());
		authenticationEntryPoint.commence(request, response, exception);
	}

	private AuthRequestWrapper generateAuthRequest(HttpServletRequest request, ShgAuthToken token) {
		AuthRequestWrapper requestWrapper = new AuthRequestWrapper(request);
		requestWrapper.setHeader(authToken, token.getToken());
		requestWrapper.setAttribute(authToken, token.getToken());

		requestWrapper.setHeader(mAcNoHeader, Long.toString(token.getMemberAcNo()));
		requestWrapper.setAttribute(mAcNoHeader, Long.toString(token.getMemberAcNo()));

		requestWrapper.setHeader(selGAcNoHeader, Long.toString(token.getSelectedGroupAcNo()));
		requestWrapper.setAttribute(selGAcNoHeader, Long.toString(token.getSelectedGroupAcNo()));

		return requestWrapper;
	}

	private AuthRequestWrapper generateAuthRequest(HttpServletRequest request, long memberAcNo) {
		AuthRequestWrapper requestWrapper = new AuthRequestWrapper(request);

		requestWrapper.setHeader(mAcNoHeader, Long.toString(memberAcNo));
		requestWrapper.setAttribute(mAcNoHeader, Long.toString(memberAcNo));

		return requestWrapper;
	}
}
