/**
 * 
 */
package com.jtang.controller.tempmonitor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.jtang.controller.common.ControllerParent;
import com.jtang.model.Sensor;
import com.jtang.model.Temperature;
import com.jtang.service.ISensorService;
import com.jtang.service.ITempService;

/**
 * @author Administartor
 *
 */
	


public class ExceptionDataC extends ControllerParent  implements Controller {

	public ISensorService sensorService;
	

	public ISensorService getSensorService() {
		return sensorService;
	}


	public void setSensorService(ISensorService sensorService) {
		this.sensorService = sensorService;
	}
	
	public ITempService tempService;
	
	
	/**
	 * @return the tempService
	 */
	public ITempService getTempService() {
		return tempService;
	}


	/**
	 * @param tempService the tempService to set
	 */
	public void setTempService(ITempService tempService) {
		this.tempService = tempService;
	}
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if(action == null){
			ModelAndView mv = new ModelAndView("dashboard");
			mv.addObject("content_page", "/WEB-INF/view/exceptionData.vm");
			String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
			mv.addObject("locations", locations);
			mv.addObject("location_info", "仓库管理平台");
			mv = setUser(request, mv);
			setUserPosition(request,mv);
			
			String storageId = getWorkStorageId(request);
			if(!storageId.equals("")){
				List<Sensor >  allSensors = sensorService.getSensorListByStorageId(Integer.parseInt(storageId));
				mv.addObject("allSensors",allSensors);
			}
			HashMap<String,Float> maxMin = getMaxMinTemp(request);
			mv.addObject("maxTemp",maxMin.get("max"));
			mv.addObject("minTemp",maxMin.get("min"));
			mv = setStorageList(request, mv);
			mv = setWorkStorage(request, mv);
			return mv;
			
		}else if(action.equals("getData")){
			String sensorExtAddr = request.getParameter("extAddr").trim();
			String startTime = request.getParameter("startTime").trim();
			String endTime = request.getParameter("endTime").trim();
			
			HashMap<String,Float> maxMin = getMaxMinTemp(request);
			
			/**取得这段时间的所有温度记录*/
			List<Temperature> temperatureList = tempService.getExceptionDataByTime(sensorExtAddr, startTime, endTime, maxMin.get("max"), maxMin.get("min"));
			
			HashMap<String,ArrayList> jsonObj = new HashMap<String,ArrayList>();
			
			ArrayList<String> recordTime = new ArrayList<String>();
			ArrayList<Float> tempData = new ArrayList<Float>();
			
			for(int i=0;i<temperatureList.size();i++){
				recordTime.add(temperatureList.get(i).getRecordTime());
				tempData.add(temperatureList.get(i).getTemperature());
			}
			jsonObj.put("time", recordTime);
			jsonObj.put("tempdata", tempData);
			
			/**
			 * 取得这段时间的温度最高的五个时间点
			 */
			List<Temperature> topFive = tempService.getExceptionDataUseLimit(sensorExtAddr, startTime, endTime, 5, 0, maxMin.get("max"), maxMin.get("min"));
			ArrayList<String> topFiveTime = new ArrayList<String>();
			ArrayList<Float> topFiveData = new ArrayList<Float>();
			for(int i=0;i<topFive.size();i++){
				topFiveTime.add(topFive.get(i).getRecordTime());
				topFiveData.add(topFive.get(i).getTemperature());
			}
			jsonObj.put("topFiveTime", topFiveTime);
			jsonObj.put("topFiveData", topFiveData);
			
			/**
			 * 取得这段时间温度最低的五个时间点
			 */
			
			List<Temperature> lowFive = tempService.getExceptionDataUseLimit(sensorExtAddr, startTime, endTime, 5, 1, maxMin.get("max"), maxMin.get("min"));
			ArrayList<String> lowFiveTime = new ArrayList<String>();
			ArrayList<Float> lowFiveData = new ArrayList<Float>();
			for(int i=0;i<topFive.size();i++){
				lowFiveTime.add(lowFive.get(i).getRecordTime());
				lowFiveData.add(lowFive.get(i).getTemperature());
			}
			jsonObj.put("lowFiveTime", lowFiveTime);
			jsonObj.put("lowFiveData", lowFiveData);
			
			/**
			 * 取得正常温度点个数，温度偏高的温度点个数以及温度偏低的个数
			 */
			int normalNumber = tempService.getNormalNumber(sensorExtAddr, startTime, endTime,maxMin.get("max"), maxMin.get("min"));
			int exceedNumer = tempService.getExceedNumber(sensorExtAddr, startTime, endTime,maxMin.get("max"));
			int lowerNumber = tempService.getLowerNumber(sensorExtAddr, startTime, endTime,  maxMin.get("min"));
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.add(normalNumber);
			temp.add(exceedNumer);
			temp.add(lowerNumber);
			jsonObj.put("threeNumber", temp);
			
			
			/**
			 * push到浏览器
			 */
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.print(JSON.toJSONString(jsonObj));
			out.flush();
			out.close();
		}
			
		return null;
	}

}
