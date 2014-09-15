package com.jtang.controller.warahouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jtang.controller.common.ControllerParent;

public class WarahouseReaderController extends ControllerParent implements Controller{
	
	//private IRFIDService rfidService;

	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView mv=null;
		//处理返回中文乱码
		response.setCharacterEncoding("utf-8");         
		response.setContentType("text/html; charset=utf-8"); 
		
		//验证登陆
		if(this.checkLogin(request) == 1){
			//单纯获取页面
			mv = new ModelAndView("dashboard");
			mv.addObject("content_page", "/WEB-INF/view/rfidReader.vm");
			String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
			mv.addObject("locations", locations);
			mv.addObject("location_info", "仓库管理平台");
			mv = setStorageList(request, mv);
			mv = setWorkStorage(request, mv);
			mv = setUser(request, mv);
			
			setUserPosition(request, mv);
		}
		else
		{
			//重定向到登录页面
			response.sendRedirect(request.getContextPath());
		}

		return mv;	
	}

}
