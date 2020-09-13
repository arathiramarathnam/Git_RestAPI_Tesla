package com.qa.restassured.Tesla.rentalcarstests;

import java.lang.Float;

public class PerDayRentPrice implements Comparable<PerDayRentPrice>{
	
	public String sVin;
	public float fPrice;
	public float fPriceAfterDiscount;
	
	public PerDayRentPrice(String vin, float price) {
	this.sVin=vin;
	this.fPrice=price;
	}
	
	@Override
	public int compareTo(PerDayRentPrice obj) { 
		return (int) ((float) this.fPrice-obj.fPrice);	
	}	

}
