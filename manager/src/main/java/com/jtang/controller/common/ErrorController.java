package com.jtang.controller.common;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ErrorController extends ControllerParent implements Controller {

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView("dashboard");
		mv.addObject("content_page", "/WEB-INF/view/error.vm");
		String[] locations= request.getParameter("source").replaceFirst("/", "").split("/");
		System.out.println(Arrays.toString(locations));

		mv.addObject("locations", locations);
		setUserPosition(request,mv);
		mv.addObject("location_info", "仓库管理平台");
		mv = setStorageList(request, mv);
		mv = setWorkStorage(request, mv);
		mv = setUser(request, mv);
		return mv;
	}

}
