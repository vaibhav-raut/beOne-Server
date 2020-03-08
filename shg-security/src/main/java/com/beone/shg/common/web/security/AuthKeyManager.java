package com.beone.shg.common.web.security;

import com.beone.shg.util.RandomString;

public final class AuthKeyManager {

	private int keyShift = 0;
	private String key = null;
    private RandomString randomString = new RandomString(12);
    private static AuthKeyManager instance;

    static {
    	instance = new AuthKeyManager();
    }
	public static AuthKeyManager getInstance() {
		return instance;
	}
    private AuthKeyManager() {
    	resetKey();
    }
	public void resetKey() {
		key = randomString.nextString();
    	keyShift++;
	}
	public int getKeyShift() {
		return keyShift;
	}
	public String getKey() {
		return key;
	}
}
