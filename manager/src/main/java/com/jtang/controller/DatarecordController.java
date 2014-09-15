package com.jtang.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.jtang.model.Pro_Trace;
import com.jtang.model.Rawmaterial;
import com.jtang.model.StoreRecord;
import com.jtang.model.TransRecord;
import com.jtang.service.IProductService;
import com.jtang.service.IRawmaterialService;
import com.jtang.service.IStoreRecordService;
import com.jtang.service.ITransRecordService;

public class DatarecordController implements Controller {
	
	private IProductService productService;
	private IRawmaterialService rawmaterialService;
	private IStoreRecordService storeRecordService;
	private ITransRecordService transRecordService;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				// TODO Auto-generated method stub
				ModelAndView mv = null;
				
				if(request.getMethod().equalsIgnoreCase("get")){
					String action=request.getParameter("action");
					System.out.println(action);
					if(action == null)
					{
						//简单获取页面
						mv = new ModelAndView("dashboard");
						mv.addObject("content_page", "/WEB-INF/view/datarecord.vm");
						String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
						mv.addObject("locations", locations);
						mv.addObject("location_info", "溯源管理平台");
					}
					else if(action.equals("statistic"))
					{
						List<Pro_Trace> res = getProductToday();
						String json =  JSON.toJSONString(res);
						//System.out.println(json);
						response.getWriter().write(json);
					}
					else	;
				}
				else if(request.getMethod().equalsIgnoreCase("post")){
					addProduct(request,response);
				}
				
				return mv;
	}
	
	public IProductService getProductService() {
		return productService;
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	public IRawmaterialService getRawmaterialService() {
		return rawmaterialService;
	}

	public void setRawmaterialService(IRawmaterialService rawmaterialService) {
		this.rawmaterialService = rawmaterialService;
	}

	public IStoreRecordService getStoreRecordService() {
		return storeRecordService;
	}

	public void setStoreRecordService(IStoreRecordService storeRecordService) {
		this.storeRecordService = storeRecordService;
	}

	public ITransRecordService getTransRecordService() {
		return transRecordService;
	}

	public void setTransRecordService(ITransRecordService transRecordService) {
		this.transRecordService = transRecordService;
	}

	private void addProduct(HttpServletRequest request,HttpServletResponse response) throws ParseException
	{
		Rawmaterial rawmaterial=new Rawmaterial();
		StoreRecord storeRecord=new StoreRecord();
		TransRecord transRecord=new TransRecord();
		Pro_Trace product=new Pro_Trace();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		String data = request.getParameter("product");
		JSONArray jsonArr = JSONArray.parseArray(data);

		String rm_name=(String)jsonArr.getJSONObject(0).getString("value");//计数从0开始
		String rm_manufacturer=(String)jsonArr.getJSONObject(1).getString("value");
		String rm_place=(String)jsonArr.getJSONObject(2).getString("value");
		Date  rm_date = sdf.parse(jsonArr.getJSONObject(3).getString("value"));
		rawmaterial.setName(rm_name);rawmaterial.setManufacturer(rm_manufacturer);rawmaterial.setProductionPlace(rm_place);rawmaterial.setProductionDate(rm_date.toString());
		rawmaterialService.addARawmaterial(rawmaterial);
		int rawmaterialId = 0;
		
		int sto_place=Integer.valueOf(jsonArr.getJSONObject(4).getString("value"));//计数从0开始
		Date sto_start=sdf.parse(jsonArr.getJSONObject(5).getString("value"));
		Date sto_end=sdf.parse(jsonArr.getJSONObject(6).getString("value"));
		float sto_temp = Float.valueOf(jsonArr.getJSONObject(7).getString("value"));
		storeRecord.setStorageId(sto_place);storeRecord.setStartTime(sto_start);;
		storeRecord.setEndTime(sto_end);storeRecord.setTemperature(sto_temp);
		storeRecordService.addAStoreRecord(storeRecord);
		int storageRecordId=storeRecordService.findStoreRecordId();
		
		String tran_start_place=jsonArr.getJSONObject(8).getString("value");//计数从0开始
		String tran_end_place=jsonArr.getJSONObject(9).getString("value");
		Date tran_start_time=sdf.parse(jsonArr.getJSONObject(10).getString("value"));
		Date tran_end_time=sdf.parse(jsonArr.getJSONObject(11).getString("value"));
		float tran_temp = Float.valueOf(jsonArr.getJSONObject(12).getString("value"));
		transRecord.setStartPlace(tran_start_place);transRecord.setEndPlace(tran_end_place);
		//transRecord.setStartTime(tran_start_time);transRecord.setEndTime(tran_end_time);transRecord.setTemperature(tran_temp);
		transRecordService.addATransRecord(transRecord);
		//int transRecordId=transRecordService.findTransRecordId();
		
		String pro_name=(String)jsonArr.getJSONObject(13).getString("value");//计数从0开始
		Date pro_date = sdf.parse(jsonArr.getJSONObject(14).getString("value"));
		String barcode=(String)jsonArr.getJSONObject(15).getString("value");

		product.setName(pro_name);product.setBarcode(barcode);product.setProductionDate(pro_date);
		product.setRawmaterialRecordId(rawmaterialId);product.setStorageRecordId(storageRecordId);
		//product.setTransportationRecordId(transRecordId);
		productService.addAProduct(product);
		return;
	}
	
	
	private List<Pro_Trace> getProductToday()
	{
		List<Pro_Trace> res=productService.getAllProducts();
		return res;
	}

}
