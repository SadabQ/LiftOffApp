package com.liftoff.dto;

import java.util.Comparator;

public class MaxTempComparator implements Comparator<Temperature>{

	@Override
	public int compare(Temperature temp1, Temperature temp2) {
		
		return - temp1.temp_max.compareTo(temp2.temp_max);
	}
}