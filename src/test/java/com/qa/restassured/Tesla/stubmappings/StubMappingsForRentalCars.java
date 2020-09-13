package com.qa.restassured.Tesla.stubmappings;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.testng.annotations.BeforeTest;

import com.qa.restassured.Tesla.utilities.TestBase;

public class StubMappingsForRentalCars extends TestBase{
	
	@BeforeTest
	public void getstubmappingsRequestAndResponseForRentalCars() {
		
		wireMockSever.stubFor(
	            get(urlEqualTo("/getcars"))
	                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
	                .willReturn(
	                    aResponse()
	                        .withStatus(200)
			                .withHeader("Content-Type", "application/json; charset=UTF-8")
	                        .withBodyFile("RentalCarsSchema.json")
	                ));
	}
}


