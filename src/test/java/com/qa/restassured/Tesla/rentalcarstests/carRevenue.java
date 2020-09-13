package com.qa.restassured.Tesla.rentalcarstests;

public class carRevenue implements Comparable<carRevenue>{


	public String sVin;
	public float fCarRevenue;
	
	
	public carRevenue(String vin, float carRevenue) {
	this.sVin=vin;
	this.fCarRevenue=carRevenue;
	}
	
	@Override
	public int compareTo(carRevenue obj) { 
		return (int) ((float) this.fCarRevenue-obj.fCarRevenue);	
		//return obj.sCarRevenue - this.sCarRevenue;  --> For reverse order
	}	
}
