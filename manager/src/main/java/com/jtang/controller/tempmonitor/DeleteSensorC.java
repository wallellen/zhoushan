package com.jtang.controller.tempmonitor;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jtang.service.ISensorService;

public class DeleteSensorC implements Controller {

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
		String needDelete = request.getParameter("extAddr").trim();
		if(needDelete!=null &&!needDelete.equals("")){
			if( sensorService.deleteASensor(needDelete+"") == 1){
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
