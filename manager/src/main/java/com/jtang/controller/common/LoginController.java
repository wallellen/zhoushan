package com.jtang.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jtang.model.User;
import com.jtang.service.IUserService;



public class LoginController implements Controller {

	public IUserService userService;
	
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		User user = userService.loginUser(userId, userPwd);
		if(user==null){
			/*
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.print("Please check your username and password!");
			out.flush();
			out.close();
			return null;
			*/
			ModelAndView mv = new ModelAndView("error","message","Please check your username and password!");
			return mv;
		}else{
			HttpSession hs = request.getSession();
			hs.setAttribute("user", user);
			hs.removeAttribute("workStorage");
			response.sendRedirect(request.getContextPath()+"/dashboard?user="+user.getName());
					
			/*ModelAndView mv = new ModelAndView("dashboard");*/
			return null;
		}
		
		
	}

}
