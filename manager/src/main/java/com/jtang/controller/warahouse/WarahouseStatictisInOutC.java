package com.jtang.controller.warahouse;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.jtang.model.InOut;
import com.jtang.service.IInOutService;

public class WarahouseStatictisInOutC implements Controller{
	
	private IInOutService inoutService;

	public IInOutService getInoutService() {
		return inoutService;
	}

	public void setInoutService(IInOutService inoutService) {
		this.inoutService = inoutService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		String action = request.getParameter("action");
		if(action == null){return null;}
		
		//处理返回中文乱码
		response.setCharacterEncoding("utf-8");         
		response.setContentType("text/html; charset=utf-8"); 
		
		PrintWriter  out = response.getWriter();
		int storageId = (Integer) request.getSession().getAttribute("workStorage");
		String json="";

		if(action.equals("statictis_in"))
		{
			//统计今日所有入库
			Map<String,Object> map =  new HashMap<String,Object>();
			map.put("all", getInStatisticToday(storageId));
			map.put("groupByTime", getInTodayGroupByTime(storageId));
			json = JSON.toJSONString(map,true);
			
		}
		else if(action.equals("statictis_today"))
		{
			//统计今日所有出入库信息
			Map<String,Object> map =  getStatisticToday(storageId);
			json = JSON.toJSONString(map);
		}
		else if(action.equals("search_today"))
		{
			String searchProName = request.getParameter("searchProName");
			int actionId = Integer.parseInt(request.getParameter("searchAction"));
			String searchPersonId = request.getParameter("searchPersonId");
			
			json = searchToday(searchProName,searchPersonId,actionId,storageId);
			
		}
		else if(action.equals("search_all"))
		{
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String proName = request.getParameter("proSearchId") ;
			String personId = request.getParameter("personSearchId");
			int searchAction = Integer.parseInt(request.getParameter("searchAction"));
			List<InOut> map = searchAll(searchAction,startTime, endTime, proName, storageId, personId );
			json = JSON.toJSONString(map,true);
		}
		else if(action.equals("search_allforchart"))
		{
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String proName = request.getParameter("proSearchId") ;
			String personId = request.getParameter("personSearchId");
			int searchAction = Integer.parseInt(request.getParameter("searchAction"));		
			Map<String,Object> list = searchGroupByTimeAndPro(searchAction, startTime, endTime, proName, storageId, personId) ;
			json = JSON.toJSONString(list,true);
		}
		

		out.write(json);
		out.flush();
		out.close();
		
		return null;
	}
	
	private Map<String,Object> searchGroupByTimeAndPro(int action,String startTime,String endTime,String proName,int storageId,String personId)
	{
		List<Map<String,Object>> list = null;
		Map<String,Object> map = new HashMap<String,Object>();

		if(action == 1)
			list = inoutService.queryInGroupByTimeAndPro(storageId, startTime, endTime, proName, personId);
		else
			list = inoutService.queryOutGroupByTimeAndPro(storageId, startTime, endTime, proName, personId);
		
		int length = list.size();
		String[] times = new String[length];
		for(int i = 0;i < length;i++)
		{
			times[i] = (String) list.get(i).get("time");
			String name = (String) list.get(i).get("name");
		    if(!map.containsKey(name))
		    {
		    	//新增项目
		    	int[] datas = new int[length];
		    	datas[i] = ((BigDecimal)list.get(i).get("count")).intValue();
		    	map.put(name, datas);
		    }
		    else {
		    	//已有项目添加数据
		    	((int [])map.get(name))[i] =  ((BigDecimal)list.get(i).get("count")).intValue();
		    }
		    
		    //添加时间数据
		    map.put("time", times);
		    
		}
		
		return map;
	}
	
	private List<InOut> searchAll(int action,String startTime,String endTime,String proName,int storageId,String personId)
	{
		List<InOut> list = null;
		if(action == 0)
			list = inoutService.queryOut(storageId, startTime, endTime, proName, personId);
		else if(action == 1)
			list = inoutService.queryIn(storageId, startTime, endTime, proName, personId);
		else
			list = inoutService.queryAll(storageId, startTime, endTime, proName, personId);
		return list;
	}
	
	private String searchToday(String searchProName,String searchPersonId,int actionId,int storageId)
	{
		List<InOut> list = inoutService.searchTodayInOut(searchProName, searchPersonId, "", actionId, storageId);
		return  JSON.toJSONString(list,true);
	}
	
	
	private Map<String,Object> getStatisticToday(int storageId)
	{
		List<InOut> list =  inoutService.queryTodayAll(storageId);
		int inCount = getInCountToday(storageId);
		int outCount = getOutCountToday(storageId);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		Map<String,Object> mapInner = new HashMap<String,Object>();
		int len = list.size();
		String[] times = new String[len];

		int[] inDatas = new int[len];
		String[] inCards = new String[len];
		int[] outDatas = new int[len];
		String[] outCards = new String[len];
		for(int i = 0 ; i < len ; i++)
		{
			InOut io = list.get(i);
			times[i] = io.getTime();
			if( io.getAction() == 0)
			{			
				outDatas[i] = io.getBindCount();
				outCards[i] = io.getCardNum();
			}	
			else{
				inDatas[i] = io.getBindCount();	
				inCards[i] = io.getCardNum();
			}	
		}
		mapInner.put("inDatas", inDatas);
		mapInner.put("inCards", inCards);
		mapInner.put("outDatas", outDatas);
		mapInner.put("outCards", outCards);
		mapInner.put("times", times);
		
		map.put("chartDatas", mapInner);
		map.put("inSum", inCount);
		map.put("outSum", outCount);
		return map;
	}
	private Map<String,Object> getInStatisticToday(int storageId)
	{
		List<InOut> list = inoutService.queryTodayIn(storageId);
		int inCount = getInCountToday(storageId);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", list);
		map.put("inSum", inCount);
		return map;
	}
	
	private Map<String,Object> getInTodayGroupByTime(int storageId)
	{
		List<Map<String,Object>> map = inoutService.queryTodayInGroupByTime(storageId);
		int size = map.size();
		String[] times = new String[size];
		int[] counts = new int[size];
		for(int i = 0;i < size ;i++)
		{
			times[i] = (String) map.get(i).get("time");
			counts[i] = ((BigDecimal) map.get(i).get("count")).intValue();			
		}
		
		Map<String,Object> data =  new HashMap<String,Object>();
		data.put("times", times);
		data.put("counts", counts);
			
		return data;
	}
	
	private String getOutStatisticToday(int storageId)
	{
		List<InOut> list = inoutService.queryTodayOut(storageId);
		int outCount = getOutCountToday(storageId);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", list);
		map.put("outSum", outCount);
		return JSON.toJSONString(map,true);
	}
	
	private int getInCountToday(int storageId)
	{
		return inoutService.countInToday(storageId);
	}
	
	private int getOutCountToday(int storageId)
	{
		return inoutService.countOutToday(storageId);
	}
}
