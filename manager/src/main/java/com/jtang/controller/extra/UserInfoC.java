package com.jtang.controller.extra;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jtang.controller.common.ControllerParent;

public class UserInfoC extends ControllerParent implements Controller {

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView mv = null;
		
		if(this.checkLogin(request) == 1)
		{	
			//刷新页面
			mv = new ModelAndView("dashboard");
			String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
			mv.addObject("locations", locations);
			mv.addObject("location_info", "仓库管理平台");
			mv.addObject("content_page", "/WEB-INF/view/privilegeTree.vm");			
			
			setStorageList(request, mv);
			setWorkStorage(request, mv);
			setUser(request, mv);
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
