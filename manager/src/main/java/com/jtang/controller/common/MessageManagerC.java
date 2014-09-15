package com.jtang.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.jtang.model.Message;
import com.jtang.model.PageInfo;
import com.jtang.model.User;
import com.jtang.service.IMessageService;

public class MessageManagerC extends ControllerParent implements Controller{

	private IMessageService messageService;
	
	/**
	 * @return the messageService
	 */
	public IMessageService getMessageService() {
		return messageService;
	}

	/**
	 * @param messageService the messageService to set
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView mv = null;
		mv = new ModelAndView("dashboard");
		String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
		setUserPosition(request,mv);
		mv.addObject("locations", locations);
		mv.addObject("location_info", "仓库管理平台");
		mv = setStorageList(request, mv);
		mv = setWorkStorage(request, mv);
		mv = setUser(request, mv);
		if("readAll".equals(request.getParameter("action"))) {
			if(request.getSession().getAttribute("user")!=null){
				User user = (User)request.getSession().getAttribute("user");
				messageService.readAll(user.getId());
			}
		}
		String messageId = request.getParameter("messageId");
		if(messageId == null || messageId.trim().equals("")) {
			mv.addObject("content_page", "/WEB-INF/view/messageManager.vm");
			
			PageInfo<Message> pageInfo = new PageInfo<>();
			setPageInfo(pageInfo, request);
			messageService.getMsgByPage(pageInfo, null, getLoginUser(request));
			mv.addObject("pageInfo",JSON.toJSONString(pageInfo));
		}else{
			mv.addObject("content_page", "/WEB-INF/view/messageDetail.vm");
			messageService.readMsg(messageId);
			mv.addObject("message",JSON.toJSONString(messageService.getMsgById(messageId)));
		}
		
		return mv;
	}

}
