package com.beone.shg.common.web.security.role;

import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;

public class MySecurityMetadataSource extends MapBasedMethodSecurityMetadataSource {

	public MySecurityMetadataSource(PropertyHolder propertyHolder) {
		super(propertyHolder.getMethodMap());
	}
}