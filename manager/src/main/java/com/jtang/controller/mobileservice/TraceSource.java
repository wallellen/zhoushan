package com.jtang.controller.mobileservice;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.jtang.model.StoreRecord;
import com.jtang.service.IStoreRecordService;

public class TraceSource implements Controller {

	IStoreRecordService storeRecordService;
	
	/**
	 * @return the storeRecordService
	 */
	public final IStoreRecordService getStoreRecordService() {
		return storeRecordService;
	}

	/**
	 * @param storeRecordService the storeRecordService to set
	 */
	public final void setStoreRecordService(IStoreRecordService storeRecordService) {
		this.storeRecordService = storeRecordService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		response.setContentType("text/html; Charset=utf-8");
		response.setCharacterEncoding("utf-8");
		
        String recorderId = request.getParameter("recorderId");
        if(recorderId == null || recorderId.equals("")){
        	return null;
        }else{
        	System.out.println(recorderId);
        	List<StoreRecord> recordList = storeRecordService.getAStoreRecordById(Integer.parseInt(recorderId));
            HashMap<String,String> json = new HashMap<String,String>();
            for(StoreRecord sr : recordList){
            	json.put("startTime", sr.getStartTime() + "");
            	json.put("endTime", sr.getEndTime() + "");
            	json.put("temperature", sr.getTemperature() + "");
            }
            
    		PrintWriter out = response.getWriter();
    		out.print(JSON.toJSONString(json));
    		out.flush();
    		out.close();
    		return null;
        }
	}

}
