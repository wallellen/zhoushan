package com.jtang.controller.maintainmanager;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.jtang.controller.common.ControllerParent;
import com.jtang.model.Enterprise;
import com.jtang.model.Message;
import com.jtang.model.StorageService;
import com.jtang.model.TempService;
import com.jtang.model.TraceService;
import com.jtang.service.IEnterpriseService;
import com.jtang.service.IMessageService;
import com.jtang.service.IStorageServiceSettingService;
import com.jtang.service.ITempServiceSettingService;
import com.jtang.service.ITraceSettingService;

public class EnterpriseBasicC extends ControllerParent implements Controller {
	
	private IEnterpriseService enterpriseS;
	private IStorageServiceSettingService storageSettingS;
    private ITempServiceSettingService tempSettingS;
    private ITraceSettingService traceSettingS;
    private IMessageService messageS;

	public IMessageService getMessageS() {
		return messageS;
	}

	public void setMessageS(IMessageService messageS) {
		this.messageS = messageS;
	}

	public IEnterpriseService getEnterpriseS() {
		return enterpriseS;
	}

	public IStorageServiceSettingService getStorageSettingS() {
		return storageSettingS;
	}

	public void setStorageSettingS(IStorageServiceSettingService storageSettingS) {
		this.storageSettingS = storageSettingS;
	}

	public ITempServiceSettingService getTempSettingS() {
		return tempSettingS;
	}

	public void setTempSettingS(ITempServiceSettingService tempSettingS) {
		this.tempSettingS = tempSettingS;
	}

	public ITraceSettingService getTraceSettingS() {
		return traceSettingS;
	}

	public void setTraceSettingS(ITraceSettingService traceSettingS) {
		this.traceSettingS = traceSettingS;
	}

	public void setEnterpriseS(IEnterpriseService enterpriseS) {
		this.enterpriseS = enterpriseS;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if(action == null || action.trim().equals("")){
			return handleDefaultRequest(request);
		}else{
			action = action.trim().toLowerCase();
			switch (action) {
				case "get_basic":
					return handleGetBaiscRequest(request, response);
				case "get_storage":
					return handleGetStorageRequest(request, response);
				case "get_temp":
					return handleGetTempRequest(request, response);
				case "get_trace":
					return handleGetTraceRequest(request, response);
				case "apply":
					return handleApplyRequest(request, response);
				default:
					return handleDefaultRequest(request);
			}
		}
	}
	
	private Message createMsg(String from, String to, String content, String title)
	{
		Message msg = new Message();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		msg.setTime(df.format(new Date()));
		msg.setMessageId(from + System.currentTimeMillis());
		msg.setFromWho(from);
		msg.setToWho(to);
		msg.setContent(content);
		msg.setTitle(title);
		msg.setFlag(0);
		msg.setStatus(0);
		
		return msg;
	}
	
	private ModelAndView handleApplyRequest(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String from = this.getEnterpriseId(request);
		String service = request.getParameter("service");
		String targetId = request.getParameter("target_id");
		String content = "["+from+"]申请将["+service+"]权限更改为["+targetId+"]";
		Message msg = createMsg(from, "platform", content, "服务变更");
		
		messageS.addMsg(msg);
		return null;
	}

	private ModelAndView handleGetTraceRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		Map<String, Object> res = new HashMap<String, Object>();
		Enterprise enter = enterpriseS.getEnterPriseByIdOrName(this.getEnterpriseId(request));
		List<TraceService> list = traceSettingS.getAllTraceService();
		
		res.put("current", enter.getTraceService());
		res.put("all", list);
		
		PrintWriter  out = response.getWriter();
		String json = JSON.toJSONString(res);
		out.write(json);
		out.flush();
		out.close();
		
		return null;
	}

	private ModelAndView handleGetTempRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		Map<String, Object> res = new HashMap<String, Object>();
		Enterprise enter = enterpriseS.getEnterPriseByIdOrName(this.getEnterpriseId(request));
		List<TempService> list = tempSettingS.getAllTempService();
		
		res.put("current", enter.getTempService());
		res.put("all", list);
		
		PrintWriter  out = response.getWriter();
		String json = JSON.toJSONString(res);
		out.write(json);
		out.flush();
		out.close();
		
		return null;
	}

	private ModelAndView handleGetStorageRequest(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Map<String, Object> res = new HashMap<String, Object>();
		Enterprise enter = enterpriseS.getEnterPriseByIdOrName(this.getEnterpriseId(request));
		List<StorageService> list = storageSettingS.getAllStorageService();
		
		res.put("current", enter.getStorageService());
		res.put("all", list);
		
		PrintWriter  out = response.getWriter();
		String json = JSON.toJSONString(res);
		out.write(json);
		out.flush();
		out.close();
		
		return null;
	}
	
	private ModelAndView handleGetBaiscRequest (HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		
		Enterprise enter = enterpriseS.getEnterPriseByIdOrName(this.getEnterpriseId(request));
		
		PrintWriter  out = response.getWriter();
		String json = JSON.toJSONString(enter);
		out.write(json);
		out.flush();
		out.close();
		
		return null;
	}
	private ModelAndView handleDefaultRequest (HttpServletRequest request)
	{
		ModelAndView mv = null;
		mv = new ModelAndView("dashboard");
		mv.addObject("content_page", "/WEB-INF/view/enterpriseBasicInfo.vm");
		String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
		setUserPosition(request,mv);
		mv.addObject("locations", locations);
		mv.addObject("location_info", "企业管理员");
		mv = setUser(request, mv);
		setUserPosition(request,mv);
		
		return mv;
	}

}
