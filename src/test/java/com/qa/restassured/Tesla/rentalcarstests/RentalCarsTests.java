package com.qa.restassured.Tesla.rentalcarstests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.isIn;

import org.apache.log4j.Logger;
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIn;
import org.hamcrest.core.Is;
import static org.hamcrest.Matchers.comparesEqualTo;
import org.testng.annotations.Test;
import com.qa.restassured.Tesla.stubmappings.StubMappingsForRentalCars;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import io.restassured.specification.RequestSenderOptions;

public class RentalCarsTests extends StubMappingsForRentalCars{
	public Logger log = Logger.getLogger(getClass().getSimpleName());
	
	@Test(priority=1)
	public void getRequest_Returns_ListOf_AllCars() throws Exception{
	
		String sHostName="http://localhost:8088";
		String URI="/getcars";
		String URL = sHostName+URI;
		RestAssured.baseURI=URL;
		Response response=RestAssured.given().contentType("application/json").get();
		System.out.println(response.asString());
		log.info("Get the response body after parsing as string:"+response.asString());
		System.out.println(response.statusCode());
		log.info("Get the status code of response:"+response.statusCode());
		String responsebody = response.getBody().asString();
		System.out.println(responsebody);
		log.info("Get the response body: "+responsebody);
		assertThat(responsebody, containsString("Tesla"));
					
		/*String pathToSchemaInClasspath = System.getProperty("user.dir")+"\\src\\test\\resources\\__files\\RentalCarsSchema.json";
		given().get("http://localhost:8088/getcars").then().assertThat()
	      .body(matchesJsonSchemaInClasspath(pathToSchemaInClasspath));*/
	}
	
	
	@Test(priority=2)
	public void getRequest_Returns_TeslaCarsWith_BlueColor_And_Notes() throws Exception{
		
		String sHostName="http://localhost:8088";
		String URI="/getcars";
		String URL = sHostName+URI;
		RestAssured.baseURI=URL;
		String make="Tesla";
		String color ="Blue";
		Response response=RestAssured.given().contentType("application/json").get();
		System.out.println(response.statusCode());
		log.info("Get the status code of response:"+response.statusCode());
		String responsebody = response.getBody().asString();
		List<String> limake= response.jsonPath().getList("Car.make");
		System.out.println(limake);
		log.info("Returns the list of all cars:"+limake);
		int setIndex = 0;
		for(int i=0;i<limake.size();i++) {
			if(limake.get(i).equalsIgnoreCase(make)) {
//				System.out.println("printing the car make that equals: "+limake.get(i));
				setIndex=i;
//				System.out.println(setIndex);
				String carcolor = response.jsonPath().getString("Car["+setIndex+"].metadata.Color");
				if(carcolor.equalsIgnoreCase(color)){
					System.out.println("printing the car make that equals: "+limake.get(i));
					log.info("printing the car make that equals: "+limake.get(i));
					
					System.out.println("prinitng only " +make+ " of color: "+carcolor);
					log.info("prinitng only " +make+ " of color: "+carcolor);
					
					assertThat(carcolor, containsString("Blue"));
					
					String carnotes = response.jsonPath().getString("Car["+setIndex+"].metadata.Notes");
					System.out.println("printing only the notes of "+ carcolor + make+" : "+carnotes);	
					log.info("printing only the notes of "+ carcolor + make+" : "+carnotes);
				}
			}
		}
		assertThat(responsebody, containsString("Tesla"));
		assertThat(limake, hasItem("Tesla"));
		
	}

@Test(priority=3, enabled=false)
public void getRequest_Returns_CarsWith_Lowest_PerDayRent() throws Exception{
	
	String sHostName="http://localhost:8088";
	String URI="/getcars";
	String URL = sHostName+URI;
	RestAssured.baseURI=URL;
	Response response=RestAssured.given().contentType("application/json").get();
	System.out.println(response.statusCode());
	log.info("Get the status code of response:"+response.statusCode());
	String responsebody = response.getBody().asString();
	List<Float> liperdayrent= response.jsonPath().getList("Car.perdayrent");
	System.out.println("print perdayrental cost of all cars: "+liperdayrent);
	log.info("print perdayrental cost of all cars: "+liperdayrent);
	ArrayList<Float> alperdayrent =new ArrayList<>();
	ArrayList<Float> alperdayrentplusdiscount =new ArrayList<>();
	int setIndex = 0;
	for(int i=0;i<liperdayrent.size();i++) {
		
		Float perDayRentPrice = response.jsonPath().getFloat("Car["+i+"].perdayrent.Price");
		Float perDayRentDiscount = response.jsonPath().getFloat("Car["+i+"].perdayrent.Discount");
		Float perDayRentandDiscount = (perDayRentPrice - (perDayRentPrice * perDayRentDiscount/100));
		alperdayrent.add(perDayRentPrice);
		alperdayrentplusdiscount.add(perDayRentandDiscount);
		
	}
	Collections.sort(alperdayrent);
	System.out.println("Returns all cars starting from lowest perdayrent with price only: "+alperdayrent);
	log.info("Returns all cars starting from lowest perdayrent with price only: "+alperdayrent);
	Collections.sort(alperdayrentplusdiscount);
	System.out.println("Returns all cars starting from lowest perdayrent with price after discount: "+alperdayrentplusdiscount);
	log.info("Returns all cars starting from lowest perdayrent with price after discount: "+alperdayrentplusdiscount);
	assertThat(Float.valueOf((float) 120.15), isIn(alperdayrent));
	assertThat(Float.valueOf((float) 102.1275), isIn(alperdayrentplusdiscount));
	
	}

@Test(priority=4, enabled=false)
public void getRequest_Returns_CarsWith_HighestRevenue() throws Exception{
	
	String sHostName="http://localhost:8088";
	String URI="/getcars";
	String URL = sHostName+URI;
	RestAssured.baseURI=URL;
	Response response=RestAssured.given().contentType("application/json").get();
	System.out.println(response.statusCode());
	log.info("Get the status code of response:"+response.statusCode());
	String responsebody = response.getBody().asString();
	List<Float> limake= response.jsonPath().getList("Car.make");
	ArrayList<Float> highestRevenue =new ArrayList<>();
	int setIndex = 0;
	List<Float> licarmetrics=response.jsonPath().getList("Car.metrics");
	System.out.println(licarmetrics);
	log.info("Returns each car with yeartoyear maintenance cost plus depreciation: "+licarmetrics);
	for(int i=0;i<licarmetrics.size();i++) {
		
		Float yoymaintenancecost = response.jsonPath().getFloat("Car["+i+"].metrics.yoymaintenancecost");
		Float depreciation = response.jsonPath().getFloat("Car["+i+"].metrics.depreciation");
		int yeartodate = response.jsonPath().getInt("Car["+i+"].metrics.rentalcount.yeartodate");
		Float perDayRentPrice = response.jsonPath().getFloat("Car["+i+"].perdayrent.Price");
		Float perDayRentDiscount = response.jsonPath().getFloat("Car["+i+"].perdayrent.Discount");
		Float perDayRentandDiscount = (perDayRentPrice - (perDayRentPrice * perDayRentDiscount/100));
		
		Float carRevenue =((yeartodate*perDayRentandDiscount)-(yoymaintenancecost+depreciation));
		highestRevenue.add(carRevenue);
		
	}
	Collections.sort(highestRevenue,Collections.reverseOrder());
	System.out.println("Highest Revenue: "+highestRevenue);
	log.info("Highest Revenue: "+highestRevenue);
	assertThat(Float.valueOf((float) 63632.684), isIn(highestRevenue));
	}


@Test(priority=5)
public void getRequest_Returns_CarsOf_LowestPerDayRent_WithPriceAnd_WithPriceAfterDiscount() throws Exception{
	
	String sHostName="http://localhost:8088";
	String URI="/getcars";
	String URL = sHostName+URI;
	RestAssured.baseURI=URL;
	Response response=RestAssured.given().contentType("application/json").get();
	System.out.println(response.statusCode());
	log.info("Get the status code of response:"+response.statusCode());
	String responsebody = response.getBody().asString();
	List<Float> liperdayrent= response.jsonPath().getList("Car.perdayrent");
	System.out.println("print perdayrental cost of all cars: "+liperdayrent);
	log.info("print perdayrental cost of all cars: "+liperdayrent);
	ArrayList<PerDayRentPrice> alperdayrent =new ArrayList<>();
	ArrayList<PerDayRentandDiscount> alperdayrentplusdiscount =new ArrayList<>();
	int setIndex = 0;
	for(int i=0;i<liperdayrent.size();i++) {
		
		String vinumber=response.jsonPath().getString("Car["+i+"].vin");
		Float perDayRentPrice = response.jsonPath().getFloat("Car["+i+"].perdayrent.Price");
		Float perDayRentDiscount = response.jsonPath().getFloat("Car["+i+"].perdayrent.Discount");
		Float perDayRentandDiscount = (perDayRentPrice - (perDayRentPrice * perDayRentDiscount/100));
		alperdayrent.add(new PerDayRentPrice(vinumber, perDayRentPrice));
		alperdayrentplusdiscount.add(new PerDayRentandDiscount(vinumber, perDayRentandDiscount));
		
}
	Collections.sort(alperdayrent);
	Collections.sort(alperdayrentplusdiscount);
	System.out.println("Returns all cars starting from lowest perdayrent with price only: ");
	log.info("Returns all cars starting from lowest perdayrent with price only: ");
	Iterator<PerDayRentPrice> it =alperdayrent.iterator();
	while (it.hasNext()) {
		System.out.println("------------------");
		PerDayRentPrice object = (PerDayRentPrice) it.next();
		System.out.println("Vin: "+object.sVin+"-----------> Price: "+object.fPrice);
		log.info("Vin: "+object.sVin+"-----------> Price: "+object.fPrice);
	}
	
	System.out.println("Returns all cars starting from lowest perdayrent with price after discount: ");
	log.info("Returns all cars starting from lowest perdayrent with price after discount: ");
	Iterator<PerDayRentandDiscount> it1 =alperdayrentplusdiscount.iterator();
	while (it1.hasNext()) {
		System.out.println("********************");
		PerDayRentandDiscount object = (PerDayRentandDiscount) it1.next();
		System.out.println("Vin: "+object.sVin+"-----------> PriceAfterDiscount: "+object.fPriceAfterDiscount);
		log.info("Vin: "+object.sVin+"-----------> PriceAfterDiscount: "+object.fPriceAfterDiscount);
	}
	assertThat(Float.valueOf((float) 120.15), comparesEqualTo(alperdayrent.get(0).fPrice));
	assertThat(Float.valueOf((float) 102.1275), comparesEqualTo(alperdayrentplusdiscount.get(0).fPriceAfterDiscount));
	
	}

@Test(priority=6)
public void getRequest_Returns_CarsWith_HighestRevenue_ForFullYear() throws Exception{
	
	String sHostName="http://localhost:8088";
	String URI="/getcars";
	String URL = sHostName+URI;
	RestAssured.baseURI=URL;
	Response response=RestAssured.given().contentType("application/json").get();
	System.out.println(response.statusCode());
	log.info("Get the status code of response:"+response.statusCode());
	String responsebody = response.getBody().asString();
	List<Float> limake= response.jsonPath().getList("Car.make");
	ArrayList<carRevenue> highestRevenue =new ArrayList<>();
	int setIndex = 0;
	List<Float> licarmetrics=response.jsonPath().getList("Car.metrics");
	System.out.println(licarmetrics);
	log.info("Returns each car with yeartoyear maintenance cost plus depreciation: "+licarmetrics);
	for(int i=0;i<licarmetrics.size();i++) {
		String vinumber=response.jsonPath().getString("Car["+i+"].vin");
		Float yoymaintenancecost = response.jsonPath().getFloat("Car["+i+"].metrics.yoymaintenancecost");
		Float depreciation = response.jsonPath().getFloat("Car["+i+"].metrics.depreciation");
		int yeartodate = response.jsonPath().getInt("Car["+i+"].metrics.rentalcount.yeartodate");
		Float perDayRentPrice = response.jsonPath().getFloat("Car["+i+"].perdayrent.Price");
		Float perDayRentDiscount = response.jsonPath().getFloat("Car["+i+"].perdayrent.Discount");
		Float perDayRentandDiscount = (perDayRentPrice - (perDayRentPrice * perDayRentDiscount/100));
		
		Float carRevenue =((yeartodate*perDayRentandDiscount)-(yoymaintenancecost+depreciation));
		highestRevenue.add(new carRevenue(vinumber,carRevenue));
		
	}
	Collections.sort(highestRevenue,Collections.reverseOrder());
	System.out.println("Highest Revenue: ");
	log.info("Highest Revenue: ");
	Iterator<carRevenue> it =highestRevenue.iterator();
	while (it.hasNext()) {
		System.out.println("********************");
		carRevenue object = (carRevenue) it.next();
		System.out.println("Vin: "+object.sVin+"-----------> CarRevenue: "+object.fCarRevenue);
		log.info("Vin: "+object.sVin+"-----------> CarRevenue: "+object.fCarRevenue);
	}
	assertThat(Float.valueOf((float) 63632.684), comparesEqualTo(highestRevenue.get(0).fCarRevenue));
	}
}

