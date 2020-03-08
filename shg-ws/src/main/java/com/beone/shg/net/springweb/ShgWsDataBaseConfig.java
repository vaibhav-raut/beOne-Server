package com.beone.shg.net.springweb;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration  
@EnableTransactionManagement
//@EnableCaching
//@EnableJpaRepositories("com.beone.shg.net.persistence")  
public class ShgWsDataBaseConfig {  

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";  
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";  
	private static final String PROPERTY_NAME_DATABASE_URL = "db.url";  
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";  

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";  
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";  
	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";  

	private final static Logger LOGGER = LoggerFactory.getLogger(ShgWsDataBaseConfig.class);

	public ShgWsDataBaseConfig() {
		LOGGER.info("DataBaseConfig ======================> Initilized");
	}

//	@Resource  
//	private Environment env;  

	@Bean  
	public DataSource dataSource() {  
		LOGGER.info("DataBaseConfig ======================> dataSource()");

		DriverManagerDataSource dataSource = new DriverManagerDataSource();  

//		dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));  
//		dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));  
//		dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));  
//		dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));  

		dataSource.setDriverClassName("com.mysql.jdbc.Driver");  

		dataSource.setUrl("jdbc:mysql://localhost:3306/shg");  

		dataSource.setUsername("root");  
		dataSource.setPassword("shgdbadmin");  

//		dataSource.setUsername("root");  
//		dataSource.setPassword("root");  

		return dataSource;  
	}  

	@Bean  
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {  
		LOGGER.info("DataBaseConfig ======================> entityManagerFactory()");

		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();  
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		
		entityManagerFactoryBean.setDataSource(dataSource());  
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);  
//		entityManagerFactoryBean.setPackagesToScan(new String[] { "com.beone.shg.net.persistence",
//				"com.beone.udaan.mr.persistence"}); 
		entityManagerFactoryBean.setPackagesToScan(new String[] { "com.beone"}); 
		entityManagerFactoryBean.setPersistenceUnitName("shg.net");
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);		
		entityManagerFactoryBean.setJpaProperties(hibProperties());  

		return entityManagerFactoryBean;  
	}  

	private Properties hibProperties() {  
		LOGGER.info("DataBaseConfig ======================> hibProperties()");

		Properties properties = new Properties();  
//		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));  
//		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));  

		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, "org.hibernate.dialect.MySQLDialect");  
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, "false");  
//		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, "true");  

		return properties;          
	}  

	@Bean
	public PlatformTransactionManager transactionManager() {  
		LOGGER.info("DataBaseConfig ======================> transactionManager()");

		JpaTransactionManager transactionManager = new JpaTransactionManager();  
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		transactionManager.setNestedTransactionAllowed(true);
		transactionManager.setPersistenceUnitName("shg.net");
		return transactionManager;  
	}  

	@Bean
	public PersistenceAnnotationBeanPostProcessor persistenceAnnotation() {  
		LOGGER.info("DataBaseConfig ======================> persistenceAnnotation()");

		PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor = new PersistenceAnnotationBeanPostProcessor();  
		return persistenceAnnotationBeanPostProcessor;  
	}  

//	@Bean
//    public TransactionAttributeSource transactionAttributeSource() {
//		LOGGER.info("DataBaseConfig ======================> transactionAttributeSource()");
//
//        return new AnnotationTransactionAttributeSource();
//    }
//
//	@Bean
//	public TransactionInterceptor transactionInterceptor() {
//		LOGGER.info("DataBaseConfig ======================> transactionInterceptor()");
//
//		TransactionInterceptor interceptor = new TransactionInterceptor();
//		LOGGER.info("DataBaseConfig ======================> transactionInterceptor() - 02");
//		interceptor.setTransactionAttributeSource(transactionAttributeSource());
//		LOGGER.info("DataBaseConfig ======================> transactionInterceptor() - 03");
//		interceptor.setTransactionManager(transactionManager());
//		LOGGER.info("DataBaseConfig ======================> transactionInterceptor() - 04");
//		return interceptor;
//    } 

//	@Bean
//	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
//		return new PersistenceExceptionTranslationPostProcessor();
//	}
}  