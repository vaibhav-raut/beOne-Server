package com.beone.shg.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackages = "com.beone.shg", excludeFilters = { @ComponentScan.Filter( Configuration.class ) })
public class SecurityEncriptorConfig {

}
