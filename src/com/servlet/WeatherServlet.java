package com.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.liftoff.dto.TemperatureService;

public class WeatherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		TemperatureService tempService = new TemperatureService();
		String req = request.getParameter("date");
		
		if ("/getDistinctDates".equals(request.getPathInfo())) {
			Set<String> dates = tempService.getDistinctDates();
			Gson gson = new Gson();
			String dateData = gson.toJson(dates);
			response.getWriter().write(dateData);
			response.setContentType("text/json;charset=utf-8");
			response.setStatus(HttpServletResponse.SC_OK);
		} else if ("/getMinMaxTemp".equals(request.getPathInfo())) {
			Map<String, String> minmaxTemp = tempService.getMinMaxTemp(req);
			Gson gson = new Gson();
			String dateData = gson.toJson(minmaxTemp);
			response.getWriter().write(dateData);
			response.setContentType("text/json;charset=utf-8");
			response.setStatus(HttpServletResponse.SC_OK);
		} else if("/getGraphData".equals(request.getPathInfo())){
			
			System.out.println("Inside graph : " + req);
			SortedMap<String, String> graphdata = tempService.getDataForGraph(req);
			Gson gson = new Gson();
			String dateData = gson.toJson(graphdata);
			response.getWriter().write(dateData);
			response.setContentType("text/json;charset=utf-8");
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}

}
