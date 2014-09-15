/**
 * 
 */
package com.jtang.controller.tempmonitor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jtang.controller.common.ControllerParent;

/**
 * @author Administartor
 *
 */
public class RealTimeDataC extends ControllerParent implements Controller {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView mv = null;
		if ( this.checkLogin(request) == 1){
			mv = new ModelAndView("dashboard");
			mv.addObject("content_page", "/WEB-INF/view/realTimeData.vm");
			String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
			setUserPosition(request,mv);
			mv.addObject("locations", locations);
			mv.addObject("location_info", "仓库管理平台");
			mv = setStorageList(request, mv);
			mv = setWorkStorage(request, mv);
			mv = setUser(request, mv);
		}
		else{
			
		}
		return mv;
		
	}

}
