package com.qa.restassured.Tesla.utilities;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

//A port number is a way to identify a specific process to which an Internet or other network message is to be forwarded when it arrives at a server
//server's operating system uses ports to make sure data is received by the right process.

public class TestBase {
	
	public static int iPort = 8088;
	public  WireMockServer wireMockSever;
	public static String slog4jPath=System.getProperty("user.dir")+"\\src\\main\\resources\\properties\\log4j.properties";
	public static Logger log = Logger.getLogger(TestBase.class);
	public static CommonUtilities commonUtil = new CommonUtilities();
	
	@BeforeSuite
	public void setUpWireMockServer() throws Exception {
		
		wireMockSever = new WireMockServer(iPort);
		WireMock wireMock1 = new WireMock("localhost", 9999);
		wireMockSever.start();
		commonUtil.loadLog4jPropertyFile(slog4jPath);
	}
	
	
	@AfterSuite
	public void teardown() {
		wireMockSever.stop();
	}
}
