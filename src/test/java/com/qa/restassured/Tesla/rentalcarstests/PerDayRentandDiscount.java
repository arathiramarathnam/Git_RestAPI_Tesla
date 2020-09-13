package com.qa.restassured.Tesla.rentalcarstests;

public class PerDayRentandDiscount implements Comparable<PerDayRentandDiscount>{
	public String sVin;
	public float fPrice;
	public float fPriceAfterDiscount;
	
	public PerDayRentandDiscount(String vin, float PriceAfterDiscount) {
	this.sVin=vin;
	this.fPriceAfterDiscount=PriceAfterDiscount;
	}
	
	@Override
	public int compareTo(PerDayRentandDiscount obj) { 
		return (int) ((float) this.fPriceAfterDiscount-obj.fPriceAfterDiscount);	
		}	

}
