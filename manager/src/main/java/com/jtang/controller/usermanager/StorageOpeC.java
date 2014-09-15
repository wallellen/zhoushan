package com.jtang.controller.usermanager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.jtang.controller.common.ControllerParent;
import com.jtang.model.Storage;
import com.jtang.service.IStorageService;

public class StorageOpeC extends ControllerParent implements Controller {
	
	private IStorageService storageS;

	public IStorageService getStorageS() {
		return storageS;
	}

	public void setStorageS(IStorageService storageS) {
		this.storageS = storageS;
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
				case "get_all":
					return handleGetAll(request, response);
				default:
					return handleDefaultRequest(request);
			}
		}
	}
	
	private ModelAndView handleGetAll(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		List<Storage> list = storageS.getStorageListByEnterId(this.getEnterpriseId(request));
		PrintWriter  out = response.getWriter();
		String json = JSON.toJSONString(list);
		out.write(json);
		out.flush();
		out.close();
		return null;
	}
	
	private ModelAndView handleDefaultRequest (HttpServletRequest request){
		return null;
	}

}
