package com.qa.restassured.Tesla.utilities;

import java.io.FileInputStream;
import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;

public class CommonUtilities {
	
	public void loadLog4jPropertyFile(String sPathLog4jPropertyFile) throws Exception{
 		TestBase.log.info("Log4j Properties File path: " +sPathLog4jPropertyFile);
 		Properties props = new Properties();
 		FileInputStream fileinput = new FileInputStream(sPathLog4jPropertyFile);
 		props.load(fileinput);
 		PropertyConfigurator.configure(props);
 		}
}
