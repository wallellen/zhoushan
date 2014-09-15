package com.jtang.controller.usermanager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.jtang.controller.common.ControllerParent;
import com.jtang.model.PageInfo;
import com.jtang.model.User;
import com.jtang.service.IUserService;


public class UserOpeC extends ControllerParent implements Controller {
	
	private IUserService userS;

	public IUserService getUserS() {
		return userS;
	}

	public void setUserS(IUserService userS) {
		this.userS = userS;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String action = request.getParameter("action");
		if(action == null || action.trim().equals("")){
			return handleDefaultRequest(request);
		}else{
			action = action.trim().toLowerCase();
			switch (action) {
				default:
					return handleDefaultRequest(request);
			}
		}
	}
	
	private ModelAndView handleDefaultRequest (HttpServletRequest request){
		
		ModelAndView mv = null;
		mv = new ModelAndView("dashboard");
		mv.addObject("content_page", "/WEB-INF/view/addUser.vm");
		String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
		setUserPosition(request,mv);
		mv.addObject("locations", locations);
		mv.addObject("location_info", "平台管理员");
		mv = setStorageList(request, mv);
		mv = setWorkStorage(request, mv);
		mv = setUser(request, mv);
		setUserPosition(request,mv);
		
		return mv;
	}

}
