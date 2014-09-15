package com.jtang.controller.warahouse;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.jtang.controller.common.ControllerParent;
import com.jtang.model.VirtualProduction;
import com.jtang.service.IUserService;
import com.jtang.service.IVirtualProductionService;

public class WarahouseInOutController extends ControllerParent implements Controller{
	
	private IVirtualProductionService vpService;
	public IVirtualProductionService getVpService() {
		return vpService;
	}

	public void setVpService(IVirtualProductionService vpService) {
		this.vpService = vpService;
	}

	private IUserService userService;

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView mv = null;
		
		//验证登陆
		if(this.checkLogin(request) == 1){
			String action = request.getParameter("action");
			String path=request.getServletPath();
			
			//处理返回中文乱码
			response.setCharacterEncoding("utf-8");         
			response.setContentType("text/html; charset=utf-8"); 
			
			if(action == null)
			{
				mv = new ModelAndView("dashboard");
				String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
				mv.addObject("locations", locations);
				mv.addObject("location_info", "仓库管理平台");
				mv.addObject("allPros", vpService.getAllVirtualProductionName(getEnterpriseId(request)));
				mv.addObject("allUsers",userService.getAllUser(this.getEnterpriseId(request)));
					
				mv.addObject("content_page", "/WEB-INF/view/inventoryIn.vm");
				
				mv = setStorageList(request, mv);
				mv = setWorkStorage(request, mv);
				mv = setUser(request, mv);
				
				setUserPosition(request, mv);
			}
			else if(action.equals("get_rfid_pros"))
			{
				String cardNum = request.getParameter("cardNum");
				List<VirtualProduction>  res = vpService.getVirtualProductionByRFID(getEnterpriseId(request),cardNum);
				
				PrintWriter  out = response.getWriter();
				String json = JSON.toJSONString(res,true);
				out.write(json);
				out.flush();
				out.close();
			}
		}
		else
		{
			//重定向到登录页面
			response.sendRedirect(request.getContextPath());
		}

		return mv;
	}
	 
}
