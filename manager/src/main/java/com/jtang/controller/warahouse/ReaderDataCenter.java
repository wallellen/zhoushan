package com.jtang.controller.warahouse;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import jt.readerdata.main.StartReaderCenter;

import org.springframework.web.context.WebApplicationContext;

public class ReaderDataCenter extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6088891019865677395L;

	public ReaderDataCenter() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		
		ServletContext sc = config.getServletContext();
		WebApplicationContext ctx = (WebApplicationContext)sc.getAttribute("org.springframework.web.context.WebApplicationContext.ROOT");
		StartReaderCenter center = ctx.getBean("startReaderCenter", StartReaderCenter.class);
		center.start();
	}
	

}
