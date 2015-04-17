package com.liftoff.dto;

import java.math.BigDecimal;


public class Temperature implements Comparable<Temperature> {
	@Override
	public int compareTo(Temperature temp2) {
		
		return this.temp.compareTo(temp2.temp);
	}

	BigDecimal temp ;

	BigDecimal temp_min ;

	BigDecimal temp_max ;

	String temp_time;

	public String getTemp_date() {
		return temp_time;
	}

	public void setTemp_date(String temp_date) {
		this.temp_time = temp_date;
	}

	public BigDecimal getTemp() {
		return temp;
	}

	public void setTemp(BigDecimal temp) {
		this.temp = temp;
	}

	public BigDecimal getTemp_min() {
		return temp_min;
	}

	public void setTemp_min(BigDecimal temp_min) {
		this.temp_min = temp_min;
	}

	public BigDecimal getTemp_max() {
		return temp_max;
	}

	public void setTemp_max(BigDecimal temp_max) {
		this.temp_max = temp_max;
	}

	@Override
	public String toString() {
		return "Temperature [temp=" + temp + ", temp_min=" + temp_min
				+ ", temp_max=" + temp_max + ", temp_time=" + temp_time +  "]" + "\n" ;
	}

}



