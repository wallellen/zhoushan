package com.jtang.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jtang.model.Pro_Trace;
import com.jtang.model.ProductQuery;
import com.jtang.service.IProductQueryService;
import com.jtang.service.IProductService;

public class DataqueryController implements Controller {
	
	private IProductService productService;
	private IProductQueryService productQueryService;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				// TODO Auto-generated method stub
				ModelAndView mv = null;
				
				if(request.getMethod().equalsIgnoreCase("get")){
					String search=request.getParameter("search");
					if(search == null)
					{
						//简单获取页面
						mv = new ModelAndView("dashboard");
						mv.addObject("content_page", "/WEB-INF/view/dataquery.vm");
						String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
						mv.addObject("locations", locations);
						mv.addObject("location_info", "溯源管理平台");
						int RowsNum=productService.getProductsNum();
						int PageSize=10;
						int PageCount=(RowsNum%PageSize==0)?RowsNum/PageSize:RowsNum/PageSize+1;
						mv.addObject("PageCount", PageCount);
						mv.addObject("PageSize", PageSize);
					}
					else if(search.equals("barcode"))
					{
						int pageIndex=Integer.valueOf(request.getParameter("pageIndex"));
						int pageSize=Integer.valueOf(request.getParameter("pageSize"));
						String barcode=request.getParameter("barcode");
						List<ProductQuery> res = ProductQuery(pageIndex,pageSize,barcode);
						String json =  JSON.toJSONStringWithDateFormat(res,"yyyy-MM-dd HH:mm:ss.SSS", SerializerFeature.WriteDateUseDateFormat);
						//String json =  JSON.toJSONString(res);
						//将查询到的结果集转换为ProductQuery对象时，对Date类型考虑为字符串即可
						//System.out.println(json);
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write(json);
					}
					else	;
				}
				else if(request.getMethod().equalsIgnoreCase("post")){		
					int pageIndex=Integer.valueOf(request.getParameter("pageIndex"));
					int pageSize=Integer.valueOf(request.getParameter("pageSize"));
					System.out.println("pageIndex:"+pageIndex);
					System.out.println("pageSize:"+pageSize);
					/*
					List<Product> res = getProductToday();
					String json =  JSON.toJSONStringWithDateFormat(res,"yyyy-MM-dd HH:mm:ss.SSS", SerializerFeature.WriteDateUseDateFormat);
					System.out.println(json);
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(json);*/
					List<ProductQuery> res = ProductQuery(pageIndex,pageSize);
					String json =  JSON.toJSONStringWithDateFormat(res,"yyyy-MM-dd HH:mm:ss.SSS", SerializerFeature.WriteDateUseDateFormat);
					//String json =  JSON.toJSONString(res);
					//将查询到的结果集转换为ProductQuery对象时，对Date类型考虑为字符串即可
					//System.out.println(json);
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(json);
				}
				
				return mv;
	}
	
	public IProductService getProductService() {
		return productService;
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
	public IProductQueryService getProductQueryService() {
		return productQueryService;
	}

	public void setProductQueryService(IProductQueryService productQueryService) {
		this.productQueryService = productQueryService;
	}

	private List<ProductQuery> ProductQuery()
	{
		List<ProductQuery> res=productQueryService.getAllProductQuerys();
		return res;
	}
	
	private List<ProductQuery> ProductQuery(int pageIndex,int pageSize)
	{
		List<ProductQuery> res=productQueryService.getProductQueryPage((pageIndex-1)*pageSize, pageSize);
		return res;
	}
	
	private List<ProductQuery> ProductQuery(int pageIndex,int pageSize,String barcode)
	{
		List<ProductQuery> res=productQueryService.getProductQueryPage((pageIndex-1)*pageSize, pageSize,barcode);
		return res;
	}
	
	private List<Pro_Trace> getProductToday()
	{
		List<Pro_Trace> res=productService.getAllProducts();
		return res;
	}

}
