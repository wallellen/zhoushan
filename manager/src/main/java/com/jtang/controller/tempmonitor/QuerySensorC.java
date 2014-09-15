package com.jtang.controller.tempmonitor;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.jtang.model.Sensor;
import com.jtang.service.ISensorService;

public class QuerySensorC implements Controller {
	
	public ISensorService getSensorService() {
		return sensorService;
	}

	public void setSensorService(ISensorService sensorService) {
		this.sensorService = sensorService;
	}

	
	private ISensorService sensorService;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		int storageId = (Integer) request.getSession().getAttribute("workStorage");
	
		List<Sensor>  allSensors = sensorService.getSensorListByStorageId(storageId);
		Map<String,Object> map =  new HashMap<String,Object>();
		map.put("sensorList", allSensors);
		
		//处理返回中文乱码
		response.setCharacterEncoding("utf-8");         
		response.setContentType("text/html; charset=utf-8"); 

		PrintWriter  out = response.getWriter();
		String json = JSON.toJSONString(map,true);
		out.write(json);
		out.flush();
		out.close();
		
		return null;
	}

}
