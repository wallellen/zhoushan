package com.jtang.controller.warahouse;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.jtang.controller.common.ControllerParent;
import com.jtang.model.Inventory;
import com.jtang.model.VirtualProduction;
import com.jtang.service.IInOutService;
import com.jtang.service.IInventoryService;
import com.jtang.service.IStorageService;
import com.jtang.service.IVirtualProductionService;

public class WarahouseAddInOutC extends ControllerParent implements Controller{
	
	public IInOutService getInoutService() {
		return inoutService;
	}

	public void setInoutService(IInOutService inoutService) {
		this.inoutService = inoutService;
	}

	private IInOutService inoutService;
	private IInventoryService proService;
	private IStorageService storageService;
	private IVirtualProductionService vpService;
	
	public IVirtualProductionService getVpService() {
		return vpService;
	}

	public void setVpService(IVirtualProductionService vpService) {
		this.vpService = vpService;
	}
	

	public IStorageService getStorageService() {
		return storageService;
	}

	public void setStorageService(IStorageService storageService) {
		this.storageService = storageService;
	}

	public IInventoryService getProService() {
		return proService;
	}

	public void setProService(IInventoryService proService) {
		this.proService = proService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		addInOutOpe(request,response);
		return null;
	}
	
	private void addInOutOpe(HttpServletRequest request,HttpServletResponse response)
	{
		
		String data = request.getParameter("in_out");
		//System.out.println(data);
		Map<String, Object> inOutDatas = JSON.parseObject(data, new TypeReference<Map<String, Object>>() {});
		int action=(Integer)inOutDatas.get("inOutAction");
		String personId=(String) inOutDatas.get("personId");
		int cardCount = (Integer) inOutDatas.get("cardNum");
		JSONArray cardNums = (JSONArray) inOutDatas.get("cards");
		JSONArray cardTimes = (JSONArray) inOutDatas.get("cardsTime");
		int storageId = (Integer) request.getSession().getAttribute("workStorage");

		for( int i = 0 ; i < cardCount ; i++)
		{
			String cardNum = cardNums.getString(i);
			String cardTime = cardTimes.getString(i);

			//获取产品信息
			
			List<VirtualProduction> pros = vpService.getVirtualProductionByRFID(getEnterpriseId(request),cardNum);
			
			addInOuts(pros,cardNum,cardTime,action,personId,storageId);
 
		}
		
		return;
	}
	
	private void addInOuts(List<VirtualProduction> pros , String cardNum, String cardTime,int action,String personId,int storageId)
	{
		if( pros == null || pros.size() == 0) return;
		int sum = 0;
		for(int i = pros.size()-1 ; i >= 0 ; i--)
		{
			VirtualProduction pro = pros.get(i);
			String name = pro.getProductionName();
			int count = pro.getProductionCount();
			sum += count;
			
            if( action == 0 )
            {
            	//出库
            	proService.minusProCount(name, count);
            	storageService.minusStorageUse(storageId, count);
            }
            else if( action == 1)
            {
            	//入库
            	if( proService.getPro(name) == null){
            		proService.addPro(new Inventory(name,"",storageId,count));
            	}
            	else{
            		proService.addProCount(name, count);
            	}
                storageService.addStorageUse(storageId, count);
            	
            }
		}
		inoutService.addInOut(cardNum, cardTime, action, personId, sum, storageId);
	}

}
