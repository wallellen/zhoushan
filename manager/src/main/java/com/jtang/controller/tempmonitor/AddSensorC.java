package com.jtang.controller.tempmonitor;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jtang.model.Sensor;
import com.jtang.model.User;
import com.jtang.service.ISensorService;

public class AddSensorC implements Controller {
	
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
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
		
		String extAddr = request.getParameter("extAddr").trim();
		
		if(sensorService.isSensorExists(extAddr)){
			out.print("exist");
			out.flush();
			out.close();
			return null;
		}
		
		Sensor s = new Sensor();
		s.setExtAddr(extAddr);		
    	s.setShortAddr(request.getParameter("shortAddr").trim());
    	s.setNodeTypes(Integer.parseInt(request.getParameter("nodeTypes").trim()));
    	s.setWorkStatus(Integer.parseInt(request.getParameter("workStatus").trim()));
    	s.setFatherNode(request.getParameter("fatherNode").trim());
    	s.setPosition(request.getParameter("position").trim());
    	s.setCreator(user.getId());
    	s.setCreateTime(formatter.format(new Date()));
    	s.setMender("UnKnown");
    	s.setMendTime("Unknown");
    	s.setWorkTime(0);
    	s.setStorageId(Integer.parseInt(request.getParameter("storageId").trim()));
    	s.setName(request.getParameter("name").trim());
		
    	sensorService.addASensor(s);
    	out.print("success");
		out.flush();
		out.close();
		return null;
    	
	}

}
