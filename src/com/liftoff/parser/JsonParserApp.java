package com.liftoff.parser;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.liftoff.dto.MaxTempComparator;
import com.liftoff.dto.MinTempComparator;
import com.liftoff.dto.Temperature;

public class JsonParserApp {

	private Map<String, ArrayList<Temperature>> map;

	BigDecimal minTemp;
	BigDecimal maxTemp;
	String maxTime;
	String minTime;

	public JsonParserApp() {
		init();
	}

	public Map<String, String> getMinMaxTemp(String req) {

		Map<String, String> minMaxMap = new HashMap<String, String>();
		if (!map.isEmpty()) {
			ArrayList<Temperature> tempList = map.get(req);

			System.out.println(tempList);
			Collections.sort(tempList, new MaxTempComparator());
			Temperature temp1 = tempList.get(0);

			maxTemp = temp1.getTemp_max();
			maxTime = temp1.getTemp_date();
			minMaxMap.put("Max_Temp", maxTemp.toString());
			minMaxMap.put("Max_Time", maxTime);

			Collections.sort(tempList, new MinTempComparator());
			Temperature temp2 = tempList.get(0);
			minTemp = temp2.getTemp_min();
			minTime = temp2.getTemp_date();

			minMaxMap.put("Min_Temp", minTemp.toString());
			minMaxMap.put("Min_Time", minTime);

		}
		return minMaxMap;
	}

	/**
	 * @throws MalformedURLException
	 * @throws ParseException
	 * @throws IOException
	 */
	private void init() {
		URL url = null;
		BufferedReader in=null ;
		try {
			url = new URL(
					"http://api.openweathermap.org/data/2.5/forecast/city?id=1277333&APPID=1111111111");
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection connection = (HttpURLConnection) urlConnection;

			 in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String urlString = "";
			String current;
			while ((current = in.readLine()) != null) {
				urlString += current;
			}
			JsonElement elem = new JsonParser().parse(urlString);
			if (elem.isJsonObject()) {
				JsonObject obj = elem.getAsJsonObject();
				map = createMap(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param obj
	 * @return
	 * @throws ParseException
	 */
	private Map<String, ArrayList<Temperature>> createMap(JsonObject obj)
			throws ParseException {
		Temperature temp = null;
		Format formatter = new SimpleDateFormat("dd/MM/yy");
		DateFormat timeInstance = SimpleDateFormat.getTimeInstance();
		String dateforrow = null;
		Date tempDate = null;
		String dateKey;
		ArrayList<Temperature> list;
		Map<String, ArrayList<Temperature>> map = new HashMap<String, ArrayList<Temperature>>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		JsonArray results = obj.getAsJsonArray("list");

		for (JsonElement result : results) {
			JsonObject resultObj = result.getAsJsonObject();
			temp = new Temperature();
			String dateStr = resultObj.getAsJsonPrimitive("dt_txt")
					.getAsString();
			tempDate = sdf.parse(dateStr);
			dateforrow = timeInstance.format(tempDate.getTime());
			dateKey = formatter.format(tempDate);
			JsonObject mainObj = resultObj.get("main").getAsJsonObject();
			temp.setTemp(mainObj.getAsJsonPrimitive("temp").getAsBigDecimal());
			temp.setTemp_min(mainObj.getAsJsonPrimitive("temp_min")
					.getAsBigDecimal());
			temp.setTemp_max(mainObj.getAsJsonPrimitive("temp_max")
					.getAsBigDecimal());

			temp.setTemp_date(dateforrow);
			if (!map.containsKey(dateKey)) {
				list = new ArrayList<Temperature>();
				list.add(temp);
				map.put(dateKey, list);
			} else {
				map.get(dateKey).add(temp);
			}
		}
		System.out.println("final map" + map);
		return map;
	}

	public Set<String> getDates() {
		if (!map.keySet().isEmpty()) {
			return map.keySet();
		}
		return null;
	}

	public SortedMap<String , String> getDataForGraph (String req){
		SortedMap<String , String>  sortMap = new TreeMap<>();
		
	   ArrayList<Temperature> tempForDay =  map.get(req);
	   			
		for (Temperature tmp : tempForDay){
			sortMap.put(tmp.getTemp().toString(), tmp.getTemp_date());
		}
		
		return sortMap;
		
	}
}

