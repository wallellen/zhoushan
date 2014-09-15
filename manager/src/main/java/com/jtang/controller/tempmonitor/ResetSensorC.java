package com.jtang.controller.tempmonitor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jtang.service.ISensorService;

public class ResetSensorC implements Controller {


/**
 * 该controller仅仅reset传感器的工作时间
 * 强烈建议在更换新电池后进行此操作
 * 方便我们给出精准的建议
 */
public ISensorService sensorService;
	

	public ISensorService getSensorService() {
		return sensorService;
	}


	public void setSensorService(ISensorService sensorService) {
		this.sensorService = sensorService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String needReset = request.getParameter("extAddr").trim();
		if(needReset!=null &&!needReset.equals("")){
			if( sensorService.resetSensorForClient(needReset+"") == 1){
				out.print("success");
				out.flush();
				out.close();
			}else{
				out.print("fail");
				out.flush();
				out.close();
			}
		}
		

		
		return null;
	}

}
