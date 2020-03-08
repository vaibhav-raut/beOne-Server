package com.beone.shg.common.web.security;

import org.springframework.security.authentication.BadCredentialsException;

/**
 * Utility class that helps to represent expired token exception. Exception
 * constructor has additional parameter to convey user name which uses expired
 * token.
 * 
 * @author ipautov
 * 
 */
public class ShgTokenExpiredException extends BadCredentialsException {

    private static final long serialVersionUID = 339216858843770736L;
    private long memberAcNo = 0l;

    public long getMemberAcNo() {
        return memberAcNo;
    }

    public ShgTokenExpiredException(String msg, long memberAcNo) {
        super(msg);
        this.memberAcNo = memberAcNo;
    }

}
