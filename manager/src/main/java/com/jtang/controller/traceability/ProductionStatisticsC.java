package com.jtang.controller.traceability;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jtang.controller.common.ControllerParent;

public class ProductionStatisticsC extends ControllerParent implements Controller {

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView mv = null;
		if ( this.checkLogin(request) == 1){
			mv = new ModelAndView("dashboard");
			mv.addObject("content_page", "/WEB-INF/view/productionStatistics.vm");
			String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
			setUserPosition(request,mv);
			mv.addObject("locations", locations);
			mv.addObject("location_info", "仓库管理平台");
			mv = setStorageList(request, mv);
			mv = setWorkStorage(request, mv);
			mv = setUser(request, mv);
		}
		else{
			response.sendRedirect(request.getContextPath()+"/");
		}
		return mv;
	}

}
