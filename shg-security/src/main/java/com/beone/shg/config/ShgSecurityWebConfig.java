package com.beone.shg.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.beone.shg.common.web.security.config.SecurityProperty;

@Configuration
public class ShgSecurityWebConfig {
	@Resource(name="securityConfigLoader")
	private EnvConfigLoader<SecurityProperty> securityConfigLoader;

	@Bean(name="securityConfigLoader")
	@Lazy
	@Scope(value = "prototype")
	public EnvConfigLoader<SecurityProperty> securityConfigLoader() {
		return new EnvConfigLoader<SecurityProperty>(SecurityProperty.class, "security");
	}

	@Bean(name="securityConfig")
	@Lazy
	@Scope(value = "prototype")
	public EnvConfig<SecurityProperty> securityConfig() {
		return securityConfigLoader.getServerConfig();
	}
}
