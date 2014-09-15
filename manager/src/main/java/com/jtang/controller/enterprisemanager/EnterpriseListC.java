package com.jtang.controller.enterprisemanager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.jtang.controller.common.ControllerParent;
import com.jtang.model.Enterprise;
import com.jtang.model.PageInfo;
import com.jtang.service.IEnterpriseService;

public class EnterpriseListC extends ControllerParent implements Controller {
	
	private IEnterpriseService enterpriseS;
	
	public IEnterpriseService getEnterpriseS() {
		return enterpriseS;
	}

	public void setEnterpriseS(IEnterpriseService enterpriseS) {
		this.enterpriseS = enterpriseS;
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
		// TODO Auto-generated method stub
	}
	
	private ModelAndView handleDefaultRequest (HttpServletRequest request){
		
		ModelAndView mv = null;
		mv = new ModelAndView("dashboard");
		mv.addObject("content_page", "/WEB-INF/view/enterpriseList.vm");
		String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
		setUserPosition(request,mv);
		mv.addObject("locations", locations);
		mv.addObject("location_info", "平台管理员");
		mv = setStorageList(request, mv);
		mv = setWorkStorage(request, mv);
		mv = setUser(request, mv);
		setUserPosition(request,mv);
		
		PageInfo<Enterprise> pageInfo = new PageInfo<Enterprise>();
		setPageInfo(pageInfo,request);
		mv.addObject("pageInfo", JSON.toJSONString(enterpriseS.getAllEnterprisesByPage(pageInfo)));
		return mv;
	}

}
