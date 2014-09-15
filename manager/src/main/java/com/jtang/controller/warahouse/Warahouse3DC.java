package com.jtang.controller.warahouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jtang.controller.common.ControllerParent;
import com.jtang.model.Inventory;
import com.jtang.model.Storage;
import com.jtang.model.User;
import com.jtang.service.IInventoryService;
import com.jtang.service.IStorageService;

public class Warahouse3DC extends ControllerParent implements Controller{
	
	public IInventoryService getProService() {
		return proService;
	}

	public void setProService(IInventoryService proService) {
		this.proService = proService;
	}

	public IStorageService getStorageService() {
		return storageService;
	}

	public void setStorageService(IStorageService storageService) {
		this.storageService = storageService;
	}

	private IInventoryService proService;
	private IStorageService storageService;

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
			mv.addObject("content_page", "/WEB-INF/view/repository.vm");
			
			
			int storageId = (Integer) request.getSession().getAttribute("workStorage");
			//mv.addObject("proList",getAllPro(storageId));
			
			//必须进行登陆验证，保证session
			//获取当前仓库信息
			String enterpriseId = ((User) request.getSession().getAttribute("user")).getEnterpriseId();
			mv.addObject("storage",getStorage(storageId,enterpriseId));
			
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
	
	private List<Inventory> getAllPro(int storageId)
	{
		return proService.getAllPro(storageId);
	}
	
	private Storage getStorage(int storageId,String enterpriseId)
	{
		List<Storage> data = storageService.getStorageListByIds(new String[]{storageId+""}, enterpriseId);
		if(data.size() > 0)
		{
			return data.get(0);
		}
		else 
			return null;
	}
	

}
