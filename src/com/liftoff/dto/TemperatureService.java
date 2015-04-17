package com.liftoff.dto;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import com.liftoff.parser.JsonParserApp;
/*
 *This is a servoce class which fetches the data from helper class 
 * and passes it to action servlet class to render the data in ui
 */
/**
 * @author SADAB
 *
 */
public class TemperatureService {
	JsonParserApp parserObj = new JsonParserApp();
	
	/*
	 * Gets the list of the dates for which temperature and timing will be displayed
	 * 
	 */
	public Set<String> getDistinctDates(){
		Set<String> dates = parserObj.getDates();
		return dates;
	}
	

	/**
	 * @param req = gets the date as param to fetch the min temp and max temp with time respectively
	 * @return Map <K ,V>  eg <"Min Temp", minTemp>
	 */
	public Map <String , String> getMinMaxTemp(String req) {
		
		return parserObj.getMinMaxTemp(req);
	}
	

	/**
	 * @param req = gets the date as param to fetch Map of <temp, time> in creasing order of temp
	 * @return
	 */
	public SortedMap<String , String> getDataForGraph (String req){
		
		return parserObj.getDataForGraph(req);
	}
	
}


