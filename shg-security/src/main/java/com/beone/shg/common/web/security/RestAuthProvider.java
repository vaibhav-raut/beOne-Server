package com.beone.shg.common.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.client.RestTemplate;

//import com.beone.shg.common.web.security.config.SecurityProperty;
//import com.beone.shg.config.EnvConfig;

public class RestAuthProvider implements AuthenticationProvider {

//    @Resource(name = "securityConfig")
//    private EnvConfig<SecurityProperty> config;
    protected Encryptor encryptor = null;
	private int keyShift;
    protected RestTemplate restTemplate;
    private static Logger LOGGER = LoggerFactory.getLogger(RestAuthProvider.class);

    public RestAuthProvider() {
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws ShgTokenExpiredException, AuthenticationException {
        LOGGER.debug("AuthenticationProvider.authenticate():start");
        if (encryptor == null) {
            init();
        }
        if (keyShift != AuthKeyManager.getInstance().getKeyShift()) {
        	initEncryptor();
        }
        ShgAuthToken token = (ShgAuthToken) authentication;
        if (token.isProcessed()) {

            return token;
        }
        if (token == null || token.getPrincipal() == null) {
            throw new BadCredentialsException("Passed token is null!");
        }
        
        // Decrypt Pass-Token from Auth-Token
        ShgPassToken passToken = encryptor.decrypt(token.getPrincipal().toString());
        
        if (passToken == null) {
            LOGGER.error("Decrypted token: Pass token is INVALID!");
            throw new BadCredentialsException("Pass token is invalid!");
        }
        if (!AuthKeyManager.getInstance().getKey().equals(passToken.getKey())) {
        	LOGGER.error("Decrypted token: key is not equal to " + passToken.getKey());
            throw new BadCredentialsException("Pass token is invalid!");
        }
        long curTime = System.currentTimeMillis();
        if (passToken.getTimestamp() < curTime) {
            LOGGER.error("Decrypted token for Member Ac No :" + Long.toString(passToken.getMemberAcNo())
                    + ", Token has been expired. current time=" + curTime + " Expiration time=" + passToken.getTimestamp());
            throw new ShgTokenExpiredException("Token Expired", passToken.getMemberAcNo());
        }
        if (token.getMemberAcNo() != passToken.getMemberAcNo()) {
        	LOGGER.error("Decrypted token: Pass token is not for the given Member Ac No: " + 
        			Long.toString(passToken.getMemberAcNo()));
        	throw new BadCredentialsException("Pass token is invalid!");
        }
        if (token.getSelectedGroupAcNo() <= 0l) {
        	LOGGER.error("Decrypted token: Pass token is not for the given Selected Group Ac No: " + 
        			Long.toString(passToken.getSelectedGroupAcNo()));
            throw new SelectGroupInvalidException("Selected Group Invalid", passToken.getMemberAcNo());
        }
        if (token.getSelectedGroupAcNo() != passToken.getSelectedGroupAcNo()) {
        	LOGGER.error("Decrypted token: Pass token is not for the given Selected Group Ac No: " + 
        			Long.toString(passToken.getSelectedGroupAcNo()));
            throw new SelectGroupInvalidException("Selected Group Invalid", passToken.getMemberAcNo());
        }
        
        ShgAuthToken ret = new ShgAuthToken(token.getPrincipal().toString());
        ret.setProcessed(true);
        ret.setMemberAcNo(passToken.getMemberAcNo());
        ret.setSelectedGroupAcNo(passToken.getSelectedGroupAcNo());
        ret.setWsAccess(passToken.getWsAccess());
        ret.setUiAccess(passToken.getUiAccess());
        
        LOGGER.info("AuthenticationProvider.authenticate() : Authentication Success :- ID = "+Long.toString(passToken.getMemberAcNo())+
        		"\t Token expiration time:"+passToken.getTimestamp()+"\tRoles:"+Long.toString(passToken.getWsAccess())); 
        return ret;
    }

    @Override
    public boolean supports(Class<? extends Object> tokenClass) {

        return ShgAuthToken.class.equals(tokenClass);
    }

    private void init() {
        restTemplate = new RestTemplate();
        initEncryptor();
        return;
    }

    private void initEncryptor() {
        try {
            encryptor = new Encryptor(AuthKeyManager.getInstance().getKey());
        	keyShift = AuthKeyManager.getInstance().getKeyShift();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }
}
