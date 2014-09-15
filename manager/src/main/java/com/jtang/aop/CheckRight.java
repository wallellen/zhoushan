package com.jtang.aop;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import com.jtang.model.User;
import com.jtang.service.IPrivilegeUserService;
import static jt.sensordata.bean.GlobalVariable.controllerToPrivilege;

public class CheckRight extends HandlerInterceptorAdapter {

	
	public IPrivilegeUserService pus;

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	
	/**
	 * @return the pus
	 */
	public IPrivilegeUserService getPus() {
		return pus;
	}


	/**
	 * @param pus the pus to set
	 */
	public void setPus(IPrivilegeUserService pus) {
		this.pus = pus;
	}


	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		/*
		ArrayList<String>  exception = new ArrayList<String>();
		exception.add("error");
		exception.add("dashboard");
		exception.add("login");
		exception.add("logout");
		
	    exception.add("traceSource");
			
		// 用户信息数绘制
		exception.add("userinfo");
		exception.add("privilege");
		// 用户信息数绘制
		
		// 平台管理
		exception.add("enterpriseList");
		exception.add("enterpriseMaintain");
		*/
		ArrayList<String>  needCheck = new ArrayList<String>();
		
		// 仓库管理 
		needCheck.add("inventoryInOut");
		needCheck.add("repository");
		needCheck.add("inventory");
		needCheck.add("rfidReader");
		
		//温控管理
		needCheck.add("realTimeData");
		needCheck.add("historyData");
		needCheck.add("exceptionData");
		needCheck.add("sensorManager");
		needCheck.add("storageManager");
		needCheck.add("tmDownload");
		
		//溯源管理
		needCheck.add("tmDownload");
		needCheck.add("tmDownload");
		needCheck.add("tmDownload");
		needCheck.add("tmDownload");
		needCheck.add("tmDownload");
		needCheck.add("tmDownload");
		needCheck.add("tmDownload");
		
		String requestUrl = request.getRequestURI();

		String controller = requestUrl.substring(requestUrl.lastIndexOf("/")+1);
		if(!needCheck.contains(controller)){
			return super.preHandle(request, response, handler);
		}//无需访问的例外请求会从这里返回
		
		if(checkLogin(request) == 0){
			response.sendRedirect(request.getContextPath()+"/");
			return false;
		}
		
		HttpSession hs = request.getSession();
		User loginUser = (User) hs.getAttribute("user");
		if(!pus.hasPrivilege(controller, loginUser.getId())){
			response.sendRedirect(request.getContextPath()+"/error?source=" + request.getServletPath().replaceAll(".html", ""));
			return false;
		}
		// TODO Auto-generated method stub
		return super.preHandle(request, response, handler);
	}
	
	
	/**
	 * 登陆验证
	 * @param request
	 * @return
	 * 	    0  未登录
	 *      1  已登录
	 */
	protected int checkLogin(HttpServletRequest request)
	{
		User user = (User) request.getSession().getAttribute("user");
		if(user == null)
			return 0;
		else 
			return 1;
	}

}
