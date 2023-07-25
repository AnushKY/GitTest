package utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;

public class LoadProperties {

	private static final Logger LOG  = LoggerFactory.getLogger(LoadProperties.class);
	
	@Getter
	private static Properties runProps;
	
	public static void loadRunConfigProps(String configPropertyFileLocation){
		runProps = new Properties();
		try(InputStream inputStream = LoadProperties.class.getResourceAsStream(configPropertyFileLocation)){
			runProps.load(inputStream);
			setUpEnvironmentURLFor("webUrl");
			setUpEnvironmentURLFor("pilsApiBaseUrl");
			setUpEnvironmentURLFor("platform");
			setUpEnvironmentURLFor("browser");
			setUpEnvironmentURLFor("device");
			setUpEnvironmentURLFor("deviceName");
			setUpEnvironmentURLFor("driver.root.dir");
			setUpEnvironmentURLFor("environment");
			setUpEnvironmentURLFor("planID");
			setUpEnvironmentURLFor("suiteID");
			setUpEnvironmentURLFor("team");
			setUpEnvironmentURLFor("sprint");
		}catch (IOException e){
			LOG.info(e.getMessage());
		}
	}
	
	
	public static void setUpEnvironmentURLFor(String key){
		String value = getRunProps().getProperty(key);
		LOG.info("Properties : key "+ key + " | value: "+value);
		if(null == value){
			LOG.warn(String.format("property %s has null value injected", value));
		}else if(value==""){
			LOG.warn(String.format("property %s has null value injected", value));
		}
		if(StringUtils.startsWith(value, "http://")){
			return;
		}
		String urlFromVMOptions = System.getProperty(key);
		if(null != urlFromVMOptions){
			LoadProperties.getRunProps().put(key, urlFromVMOptions);
		}
		
			
	}
}
