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
public class TMDownloadC extends ControllerParent implements Controller {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		if(checkLogin(request) == 0){
			//重定向到登录页面
			response.sendRedirect(request.getContextPath());
		}else{
			ModelAndView mv = new ModelAndView("dashboard");
			mv.addObject("content_page", "/WEB-INF/view/tmDownload.vm");
			String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
			mv.addObject("locations", locations);
			mv.addObject("location_info", "仓库管理平台");
			setUserPosition(request,mv);
			mv.addObject("downloadUrl","/public/app/TempMonitor.apk");
			return mv;
		}
		return null;
	}

}
