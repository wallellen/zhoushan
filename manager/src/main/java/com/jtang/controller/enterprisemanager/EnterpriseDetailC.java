package com.jtang.controller.enterprisemanager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.jtang.controller.common.ControllerParent;
import com.jtang.model.Enterprise;
import com.jtang.model.StorageService;
import com.jtang.model.TempService;
import com.jtang.model.TraceService;
import com.jtang.service.IEnterpriseService;
import com.jtang.service.IStorageServiceSettingService;
import com.jtang.service.ITempServiceSettingService;
import com.jtang.service.ITraceSettingService;

public class EnterpriseDetailC extends ControllerParent implements Controller {

    public IStorageServiceSettingService getStorageSettingS() {
		return storageSettingS;
	}

	public void setStorageSettingS(IStorageServiceSettingService storageSettingS) {
		this.storageSettingS = storageSettingS;
	}

	private IEnterpriseService enterpriseS;
    private IStorageServiceSettingService storageSettingS;
    private ITempServiceSettingService tempSettingS;
    private ITraceSettingService traceSettingS;
	
	public ITempServiceSettingService getTempSettingS() {
		return tempSettingS;
	}

	public ITraceSettingService getTraceSettingS() {
		return traceSettingS;
	}

	public void setTraceSettingS(ITraceSettingService traceSettingS) {
		this.traceSettingS = traceSettingS;
	}

	public void setTempSettingS(ITempServiceSettingService tempSettingS) {
		this.tempSettingS = tempSettingS;
	}

	public IEnterpriseService getEnterpriseS() {
		return enterpriseS;
	}

	public void setEnterpriseS(IEnterpriseService enterpriseS) {
		this.enterpriseS = enterpriseS;
	}
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if(action == null || action.trim().equals("")){
			return handleDefaultRequest(request);
		}else{
			action = action.trim().toLowerCase();
			switch (action) {
				case "search":
					return handleSearchRequest(request , response);
				case "storage_service":
					return handleStorageService(request , response);
				case "temp_service":
					return handleTempService(request , response);
				case "trace_service":
					return handleTraceService(request , response);
				case "add":
					return handleAddService(request , response);
				case "delete":
					return handleDelService(request , response);
				case "user_count":
					return handleUserCountService(request , response);
				default:
					return handleDefaultRequest(request);
			}
		}
	}
	

	private ModelAndView handleUserCountService(HttpServletRequest request , HttpServletResponse response) throws IOException
	{
		String serviceName = request.getParameter("service");
		List<Map<String,Object>> list = null;
		switch (serviceName) {
		case "storage":
			list = storageSettingS.getUserCount();
		default:
			break;
		}		
		
		PrintWriter  out = response.getWriter();
		String json = JSON.toJSONString(list);
		out.write(json);
		out.flush();
		out.close();
		
		return null;
	}
	
	
	private ModelAndView handleDelService(HttpServletRequest request , HttpServletResponse response) throws IOException
	{
		String serviceName = request.getParameter("service");
		int id = Integer.parseInt(request.getParameter("id"));
		switch (serviceName) {
		case "storage":
			storageSettingS.delete(id);
		default:
			break;
		}
		
		return null;
	}
	
	private ModelAndView handleAddService(HttpServletRequest request , HttpServletResponse response)
	{
		String serviceName = request.getParameter("service_name");
		
		switch (serviceName) {
		case "storage":
			addStoregeService(request);
		default:
			break;
		}		
		return null;
	}
	
	private void addStoregeService(HttpServletRequest request)
	{
		StorageService s = new StorageService();
		s.setInoutService(Integer.parseInt(request.getParameter("inoutService")));
		s.setInventoryService(Integer.parseInt(request.getParameter("inventoryService")));
		s.setRepPreviwService(Integer.parseInt(request.getParameter("repPreviwService")));
		s.setRfidService(Integer.parseInt(request.getParameter("rfidService")));
		
		storageSettingS.add(s);
	}
	
	private ModelAndView handleTraceService(HttpServletRequest request , HttpServletResponse response) throws IOException
	{
		List<TraceService> list = traceSettingS.getAllTraceService();
		
		PrintWriter  out = response.getWriter();
		String json = JSON.toJSONString(list);
		out.write(json);
		out.flush();
		out.close();
		
		return null;
	}
	
	private ModelAndView handleTempService(HttpServletRequest request , HttpServletResponse response) throws IOException
	{
		List<TempService> list = tempSettingS.getAllTempService();
		
		PrintWriter  out = response.getWriter();
		String json = JSON.toJSONString(list);
		out.write(json);
		out.flush();
		out.close();
		
		return null;
	}
	
	private ModelAndView handleStorageService(HttpServletRequest request , HttpServletResponse response) throws IOException
	{
		List<StorageService> list = storageSettingS.getAllStorageService();
		
		PrintWriter  out = response.getWriter();
		String json = JSON.toJSONString(list);
		out.write(json);
		out.flush();
		out.close();
		
		return null;
	}
	
	private ModelAndView handleSearchRequest (HttpServletRequest request , HttpServletResponse response) throws IOException{
		String idOrName = request.getParameter("search");
		Enterprise enter = enterpriseS.getEnterPriseByIdOrName(idOrName);
		
		PrintWriter  out = response.getWriter();
		String json = JSON.toJSONString(enter);
		out.write(json);
		out.flush();
		out.close();
		
		return null;
	}
	
	private ModelAndView handleDefaultRequest (HttpServletRequest request){
		ModelAndView mv = null;
		mv = new ModelAndView("dashboard");
		mv.addObject("content_page", "/WEB-INF/view/enterpriseDetail.vm");
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
