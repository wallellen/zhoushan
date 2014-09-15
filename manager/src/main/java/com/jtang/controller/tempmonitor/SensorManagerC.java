package com.jtang.controller.tempmonitor;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.jtang.controller.common.ControllerParent;
import com.jtang.model.PageInfo;
import com.jtang.model.Sensor;
import com.jtang.model.Storage;
import com.jtang.service.ISensorService;
import com.jtang.service.IStorageService;



public class SensorManagerC extends ControllerParent implements Controller {

	public ISensorService sensorService;
	

	public ISensorService getSensorService() {
		return sensorService;
	}


	public void setSensorService(ISensorService sensorService) {
		this.sensorService = sensorService;
	}

	public IStorageService storageService;
	
	

	/**
	 * @return the storageService
	 */
	public IStorageService getStorageService() {
		return storageService;
	}


	/**
	 * @param storageService the storageService to set
	 */
	public void setStorageService(IStorageService storageService) {
		this.storageService = storageService;
	}


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView mv = null;
		
		//检测登陆
		if(this.checkLogin(request) == 1)
		{
			mv = new ModelAndView("dashboard");
			mv.addObject("content_page", "/WEB-INF/view/sensorManager.vm");
			String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
			mv.addObject("locations", locations);
			mv.addObject("location_info", "仓库管理平台");
			
			PageInfo<Sensor> sensorPageInfo = new PageInfo<>();
			setPageInfo(sensorPageInfo,request);
			
			String storageId = getWorkStorageId(request);
			if(!storageId.equals("")){
				sensorPageInfo = sensorService.getSensorListByPage(sensorPageInfo, null, Integer.parseInt(storageId));
				mv.addObject("allSensors",sensorPageInfo.getDataList());
				mv.addObject("sensorPageInfo", JSON.toJSONString(sensorPageInfo));
			}
			
			HttpSession hs = request.getSession();
			setUserPosition(request,mv);
			List<Storage> storageList = (List<Storage>) hs.getAttribute("storageList");
			
			mv.addObject("storageList",storageList);
			
			mv = setStorageList(request, mv);
			mv = setWorkStorage(request, mv);
			mv = setUser(request, mv);
		}
		else{
			//重定向到登录页面
			response.sendRedirect(request.getContextPath());
		}
		return mv;

	}

	

}
