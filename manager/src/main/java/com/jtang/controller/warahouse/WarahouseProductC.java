package com.jtang.controller.warahouse;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.jtang.controller.common.ControllerParent;
import com.jtang.model.Inventory;
import com.jtang.model.Storage;
import com.jtang.model.User;
import com.jtang.service.IInventoryService;
import com.jtang.service.IStorageService;
import com.jtang.service.IUserService;

/**
 * 仓库商品管理控制类
 * 商品的增删查改
 * @author yyj
 *
 */
public class WarahouseProductC extends ControllerParent implements Controller{
	
	private IInventoryService proService;
	private IStorageService storageService;
	private IUserService userService;
	
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IInventoryService getProService() {
		return proService;
	}

	public IStorageService getStorageService() {
		return storageService;
	}

	public void setStorageService(IStorageService storageService) {
		this.storageService = storageService;
	}

	public void setProService(IInventoryService proService) {
		this.proService = proService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView mv = null;
		
		if(this.checkLogin(request) == 1)
		{
			String action = request.getParameter("action");
			
			//处理返回中文乱码
			response.setCharacterEncoding("utf-8");         
			response.setContentType("text/html; charset=utf-8"); 
			
			if(action == null)
			{
				//刷新页面
				mv = new ModelAndView("dashboard");
				String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
				mv.addObject("locations", locations);
				mv.addObject("location_info", "仓库管理平台");
				mv.addObject("content_page", "/WEB-INF/view/inventory.vm");
				
				//获取当前仓库货物
				int storageId = (Integer) request.getSession().getAttribute("workStorage");
				mv.addObject("proList",getAllPro(storageId));
				mv.addObject("allUsers",userService.getAllUser(this.getEnterpriseId(request)));
				
				//必须进行登陆验证，保证session
				//获取当前仓库信息
				String enterpriseId = ((User) request.getSession().getAttribute("user")).getEnterpriseId();
				mv.addObject("storage",getStorage(storageId,enterpriseId));
				
				mv = setStorageList(request, mv);
				mv = setWorkStorage(request, mv);
				mv = setUser(request, mv);
				
				setUserPosition(request, mv);
				
			}
			else if(action.equals("add"))
			{
				//添加商品
				String name =  request.getParameter("name");
				String msg = request.getParameter("msg");
				int unit = Integer.parseInt(request.getParameter("unit"));
				int storageId = (Integer) request.getSession().getAttribute("workStorage");
				//addPro(name,msg,unit,storageId);
			}
			else if(action.equals("edit"))
			{
				//编辑商品
				int id =  Integer.parseInt(request.getParameter("id"));
				String name =  request.getParameter("name");
				String msg = request.getParameter("msg");
				int unit = Integer.parseInt(request.getParameter("unit"));
				int storageId = (Integer) request.getSession().getAttribute("workStorage");
				editPro(name, msg, storageId);
				
			}
			else if(action.equals("delete"))
			{
				//删除商品
				String name = request.getParameter("name");
				int count = Integer.parseInt(request.getParameter("count"));
				int storageId = (Integer) request.getSession().getAttribute("workStorage");
				deletePro(name);
			}
			else if(action.equals("query"))
			{
				//查询当前仓库库存情况
				Map<String,Object> map =  new HashMap<String,Object>();
				//获取当前仓库货物
				int storageId = (Integer) request.getSession().getAttribute("workStorage");
				map.put("proList", getAllPro(storageId));						
								
				PrintWriter  out = response.getWriter();
				String json = JSON.toJSONString(map,true);
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
	
	private int deletePro(String name)
	{
		proService.deletePro(name);
		//storageService.minusStorageUse(storageId, count);
		return 0;
	}
	
	private int addPro(String name,String msg,int storageId)
	{
		//return proService.addPro(storageId, name, msg);
		return 1;
	}
	
	private int editPro(String name,String msg,int storageId)
	{
		return proService.updatePro( storageId, name, msg);
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
