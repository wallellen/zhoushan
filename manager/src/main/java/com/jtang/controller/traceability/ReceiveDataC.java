package com.jtang.controller.traceability;

import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;

import dataserver.rmi.bean.Vehicle;
import dataserver.rmi.stub.UserManagerInterface;

public class ReceiveDataC implements Controller {
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				// TODO Auto-generated method stub
				String lineno=request.getParameter("lineno");  
				try {
					Registry registry = LocateRegistry.getRegistry("localhost",2001);
					//命名服务.服务器端对象暴露，并把接口的aop实现注册到registry中去，
				    //客户端只需要registry.lookup接口的aop即可，并可以通过这个aop接口实现于接口真正实现通信。 
					UserManagerInterface userManager = (UserManagerInterface) registry.lookup("userManager");
					Vehicle vehicle=userManager.getVehicle(Integer.parseInt(lineno));
					if(null==vehicle)
					    System.out.println("vehicle==null");					
					else	
					    System.out.println(lineno+"\t"+vehicle.getId()+"\t"
				    		+vehicle.getLongitude());
					
					String json=JSON.toJSONString(vehicle);
					
					PrintWriter out = response.getWriter();
		    		out.print(JSON.toJSONString(json));
		    		out.flush();
		    		out.close();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
	}

}
