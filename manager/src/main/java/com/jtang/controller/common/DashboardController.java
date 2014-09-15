package com.jtang.controller.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jtang.model.Storage;
import com.jtang.model.User;
import com.jtang.service.IStorageService;
import com.jtang.service.IUserService;

public class DashboardController extends ControllerParent implements Controller{

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
	
	public IUserService userService;
	
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		HttpSession hs = request.getSession();
		User loginUser = (User) hs.getAttribute("user");
		
		
		
		ModelAndView mv = new ModelAndView("dashboard");
		
		mv.addObject("content_page", "/WEB-INF/view/hello.vm");
		String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
		mv.addObject("locations", locations);
		mv.addObject("location_info", "our home page");
		
		if(loginUser !=null){
			String [] storageIdList = userService.getStorageListByUser(loginUser.getId());
			mv.addObject("userName",loginUser.getName());
			setUserPosition(request,mv);
			if(storageIdList !=null){
				List<Storage> storageList = storageService.getStorageListByIds(storageIdList,loginUser.getEnterpriseId());
				mv.addObject("storageList",storageList);
				hs.setAttribute("storageList", storageList);
			}
			if(hs.getAttribute("workStorage")==null){
				hs.setAttribute("workStorage",  loginUser.getDefaultStorageId());
				mv.addObject("workStorage", loginUser.getDefaultStorageId());
			}else{
				mv.addObject("workStorage",hs.getAttribute("workStorage"));
			}
		}
		return mv;
	}

}
