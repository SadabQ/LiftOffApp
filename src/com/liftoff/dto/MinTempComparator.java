package com.liftoff.dto;

import java.util.Comparator;

public class MinTempComparator implements Comparator<Temperature>{

	@Override
	public int compare(Temperature temp1, Temperature temp2) {
		
		return temp1.temp_min.compareTo(temp2.temp_min);
	}

}
