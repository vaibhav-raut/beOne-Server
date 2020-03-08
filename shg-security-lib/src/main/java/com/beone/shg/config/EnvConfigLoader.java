package com.beone.shg.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvConfigLoader<T extends Enum<T>> {
	
	private static Logger log = LoggerFactory.getLogger(EnvConfigLoader.class);
	private static final String GLOBAL_CONFIG_FILE_NAME = ".properties";
	private static final String APP_CONFIG_FILE_NAME = "-app.properties";
	private static final String TEST_CONFIG_FILE_NAME = "-test.properties"; 
	private static String FILE_SEPARATOR = System.getProperty("file.separator");
	
	private final EnvConfig<T> config;

	private final Class<T> propertyClass;
	private final String fileNamePrefix;
	private final EnvConfigPropertyValueProcessor valueProcessor;

	public EnvConfigLoader(Class<T> propertyClass, String fileNamePrefix) {
		this(propertyClass, fileNamePrefix, null);
	}
	
	public EnvConfigLoader(Class<T> propertyClass, String fileNamePrefix, EnvConfigPropertyValueProcessor valueProcessor) {

		if (!EnvConfigProperty.class.isAssignableFrom(propertyClass)) {
			throw new RuntimeException("Property Enum " + propertyClass.getName() + " must implement interface "
					+ EnvConfigProperty.class.getName());
		}
		this.valueProcessor = valueProcessor;
		this.fileNamePrefix = StringUtils.trimToNull(fileNamePrefix);
		if (this.fileNamePrefix == null) {
			throw new RuntimeException("File name prefix must not be empty. Current value '" + fileNamePrefix + "'.");
		}
		this.propertyClass = propertyClass;
		this.config = new EnvConfig<T>(propertyClass);
		reload();
		
	}

	public EnvConfig<T> getServerConfig() {
		return config;
	}

	public void reload() {
		Properties properties = loadAllProperties(EnvConfigLocation.INSTANCE.getPath(), this.fileNamePrefix);
		config.setProperties(properties);
	}
	
	private Properties loadAllProperties(String path, String prefix)  
	{
		String globalConfigFileName = prefix+GLOBAL_CONFIG_FILE_NAME;
		String appConfigFileName = prefix+APP_CONFIG_FILE_NAME; 
		String testConfigFileName = prefix+TEST_CONFIG_FILE_NAME;

		Properties globalProps = loadFileWithProperties(path, globalConfigFileName, true);

		Properties appProps = loadFileWithProperties(path, appConfigFileName, false);
		globalProps.putAll(appProps);

		Properties testProps = loadFileWithProperties(path, testConfigFileName, false);
		globalProps.putAll(testProps);
	
		return normalize(globalProps);
		
	}


	private Properties normalize(Properties globalProps) {
		
		Properties result = new Properties();
		HashSet<String> propsNameInFile = new HashSet<String>();
		
		T[] propertyEnums = propertyClass.getEnumConstants();
		for (T propertyEnum : propertyEnums) {
			propsNameInFile.add(((EnvConfigProperty) propertyEnum).getNameInFile());
		}
		
		Set<Entry<Object, Object>> entrySet = globalProps.entrySet();
		for (Entry<Object, Object> entry : entrySet) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			
			if (!propsNameInFile.contains(key)) {
				log.error("Property with the name \""+key+"\" is not registered as AhaProperty. It will be ignored.");
				continue;
			}

			value=StringUtils.trimToEmpty(value);
			if (valueProcessor!=null) {
				value = valueProcessor.processValue(key, value);
			}
			if (log.isDebugEnabled()) {
				log.debug(" adding property "+key+"="+value);
			}
			result.put(key, value);
		}
		return result;
	}

	private Properties loadFileWithProperties(String path, String fileName, boolean mustExist) {
		Properties props = new Properties();

		InputStream istream = null;
		try 
		{
			log.warn("Looking for path:"+path+" file:"+fileName);
			if (log.isDebugEnabled()) {
				log.debug("YOU CAN IGNORE THIS EXCEPTION: the purpose of this exception is to trace invocation points of AhaConfigFactory", new RuntimeException("AhaConfigFactory invocation stack trace."));
			}
			String propertiesPath = null;
			
			if ("classpath".equalsIgnoreCase(path)) 
			{
				propertiesPath = fileName;
				istream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
				if (istream==null) {
					if (mustExist) {
						log.error("Cannot find file with configuration properties in the classpath:"+fileName);
						throw new FileNotFoundException(fileName);
					}
					return props;
				}
			}
			else 
			{
				File file = new File(path+FILE_SEPARATOR+fileName);
				propertiesPath = file.toString();
				if (!file.exists()) {
					if (mustExist) {
						log.error("Cannot find file with configuration properties:"+propertiesPath);
						throw new FileNotFoundException(file.toString());
					}
					return props;
				}
				istream = new FileInputStream(file);
			}
			log.warn("FILE FOUND loading: "+propertiesPath);
			props.load(istream);
			return props;
		} 
		catch(Exception e) 
		{
			throw new RuntimeException(e);
		} 
		finally 
		{
			if (istream != null) 
			{
				try 
				{
					istream.close();
				} 
				catch (Exception ignore) 
				{
				}
			}
		}
	}
}
