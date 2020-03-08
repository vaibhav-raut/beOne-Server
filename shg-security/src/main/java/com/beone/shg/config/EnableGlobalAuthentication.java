package com.beone.shg.config;

import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Import({AuthenticationConfiguration.class, ShgSecurityWebConfig.class})
public interface EnableGlobalAuthentication {
}
