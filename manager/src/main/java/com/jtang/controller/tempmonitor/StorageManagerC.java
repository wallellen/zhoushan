/**
 * 
 */
package com.jtang.controller.tempmonitor;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jtang.controller.common.ControllerParent;
import com.jtang.model.Storage;
import com.jtang.model.User;
import com.jtang.service.IStorageService;
import com.jtang.service.IUserService;

/**
 * @author Administartor
 *
 */
public class StorageManagerC extends ControllerParent implements Controller {

	public IStorageService storageService;
	public IUserService userService;
	
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
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

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		String action = request.getParameter("action");
		if(action == null){
			ModelAndView mv = new ModelAndView("dashboard");
			mv.addObject("content_page", "/WEB-INF/view/storageManager.vm");
			String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
			mv.addObject("locations", locations);
			mv.addObject("location_info", "仓库管理平台");
			
				
			HttpSession hs = request.getSession();
			User loginUser = (User) hs.getAttribute("user");
			if(loginUser !=null){
				setUserPosition(request,mv);
				String [] storageIdList = userService.getStorageListByUser(loginUser.getId());
				if(storageIdList !=null){
					List<Storage> storageList = storageService.getStorageListByIds(storageIdList,loginUser.getEnterpriseId());
					mv.addObject("storageList",storageList);
					hs.setAttribute("storageList", storageList);
				}

			}		
			
			mv = setStorageList(request, mv);
			mv = setWorkStorage(request, mv);
			return mv;
		}else if(action.equals("changeWorkStorage")){
			HttpSession hs = request.getSession();
			String workStorage = request.getParameter("storageId");
			hs.setAttribute("workStorage", Integer.parseInt(workStorage));
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.print("success");
			out.flush();
			out.close();
		}else if(action.equals("update")){
			HttpSession hs = request.getSession();
			
			User user = (User) hs.getAttribute("user");
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			
			if(user == null){
				out.print("needlogin");
				out.flush();
				out.close();
				return null;
			}
			
			String storageId = request.getParameter("storageId").trim();
			
			if(!storageService.isStorageExists(Integer.parseInt(storageId))){
				out.print("notexist");
				out.flush();
				out.close();
				return null;
			}
			Storage s = new Storage();
			s.setId(Integer.parseInt(storageId));
			s.setName(request.getParameter("storageName").trim());
			s.setLength(Integer.parseInt(request.getParameter("storageLength").trim()));
			s.setWidth(Integer.parseInt(request.getParameter("storageWidth").trim()));
			s.setHigh(Integer.parseInt(request.getParameter("storageHigh").trim()));
			s.setCapacity(Integer.parseInt(request.getParameter("storageCapacity").trim()));
			s.setUsed(Integer.parseInt(request.getParameter("storageUsed").trim()));
			s.setMaxTemp(Float.parseFloat(request.getParameter("storageMaxTemp")));
			s.setMinTemp(Float.parseFloat(request.getParameter("storageMinTemp")));
			storageService.updateStorage(s);
			out.print("success");
			out.flush();
			out.close();
			return null;
			
		}else if(action.equals("updateMaxAndMin")){
			HttpSession hs = request.getSession();
			
			User user = (User) hs.getAttribute("user");
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			
			if(user == null){
				out.print("needlogin");
				out.flush();
				out.close();
				return null;
			}
			
			String storageId = request.getParameter("storageId").trim();
			
			if(!storageService.isStorageExists(Integer.parseInt(storageId))){
				out.print("notexist");
				out.flush();
				out.close();
				return null;
			}
			
			float maxTemp = Float.parseFloat(request.getParameter("maxTemp"));
			float minTemp = Float.parseFloat(request.getParameter("minTemp"));

			storageService.updateMaxMinTemp(Integer.parseInt(storageId), maxTemp, minTemp);
			out.print("success");
			out.flush();
			out.close();
			return null;
		}
	
		return null;
	}

}
