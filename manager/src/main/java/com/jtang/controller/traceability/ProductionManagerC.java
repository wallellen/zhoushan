package com.jtang.controller.traceability;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.jtang.controller.common.ControllerParent;
import com.jtang.dao.sqlUtil.Conditionor;
import com.jtang.dao.sqlUtil.Zql;
import com.jtang.model.InOut;
import com.jtang.model.PageInfo;
import com.jtang.model.ProcessRecord;
import com.jtang.model.Rawmaterial;
import com.jtang.model.SaleRecord;
import com.jtang.model.Sensor;
import com.jtang.model.Storage;
import com.jtang.model.Temperature;
import com.jtang.model.TransRecord;
import com.jtang.model.VirtualProduction;
import com.jtang.qrcode.ZXingUtil;
import com.jtang.service.IInOutService;
import com.jtang.service.IProcessRecordService;
import com.jtang.service.IRawmaterialService;
import com.jtang.service.ISaleRecordService;
import com.jtang.service.ISensorService;
import com.jtang.service.IStorageService;
import com.jtang.service.ITempService;
import com.jtang.service.ITransRecordService;
import com.jtang.service.IVirtualProductionService;

public class ProductionManagerC extends ControllerParent implements Controller {

	private IVirtualProductionService vps;
	private IRawmaterialService rawService;
	private IProcessRecordService prs;
	private IInOutService inOutS;
	private IStorageService storageS;
	private ISensorService sensorS;
	private ITempService tempS;
	private ITransRecordService tranS;
	private ISaleRecordService saleS;
	
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if(action == null || action.trim().equals("")){
			return handleDefaultRequest(request);
		}else{
			action = action.trim().toLowerCase();
			switch (action) {
			case "rawinfo":
				pushRawInfo(request,response);
				return null;
			case "processinfo":
				return pushProcessInfo(request,response);
			case "storageinfo":
				return pushStorageInfo(request,response);
			case "gotocar" :
				handleShipRequest(request,response);
				return null;
			case "transinfo":
				return pushTransInfo(request,response);
			case "gotomarket" :
				handleSaleRequest(request,response);
				return null;
			case "saleinfo" :
				return pushSaleInfo(request,response);
			case "allinfo" :
				return pushAllInfo(request,response);
			case "searchrfid":
				handleRfidSearchRequest(request,response);
				return null;
			case "bindrfid" :
				handleRfidBindRequest(request,response);
				return null;
			case "searchtrans" :
				handleTransSearchRequest(request,response);
				return null;
			case "bindtrans" :
				handleBindTransRequest(request,response);
				return null;
			case "searchsale" :
				handleSaleSearchRequest(request,response);
				return null;
			case "bindsale" :
				handleBindSaleRequest(request,response);
				return null;
			default:
				return handleDefaultRequest(request);
			}
		}
	}


	private void handleBindSaleRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String [] selectedPros = request.getParameter("selectedPros")
				.substring(0, request.getParameter("selectedPros").length() - 1)
				.split(";");
		String saleNumber  = request.getParameter("saleNumber");
		for(String pro : selectedPros){
			vps.updateSaleNumber(pro, saleNumber);
		}
		
		PrintWriter out = response.getWriter();
		out.print("success");
		out.flush();
		out.close();
	}


	private void handleSaleSearchRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/javascript;charset=utf-8");   
		response.setHeader("Cache-Control", "no-cache");   
		String [] keyWords = request.getParameter("keyWords").split("\\s+");
		String keyWordsList = "";
		for(String keyWord : keyWords){
			if(!keyWord.equals("")){
				keyWordsList += keyWord + "|";
			}
		}
		Conditionor con = Zql.condition("enterpriseId", "=", getEnterpriseId(request));
		if(!keyWordsList.equals("")){
			keyWordsList = keyWordsList.substring(0, keyWordsList.length() - 1);
			con.and(Zql.condition("concat(saleTime,salePlace,freezerNumber,saleMarket)", "regexp", keyWordsList));
		}
		List<SaleRecord > saleList = saleS.getSaleRecordsByCon(con);
		
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONString(saleList));
		out.flush();
		out.close();
	}


	private void handleBindTransRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String [] selectedPros = request.getParameter("selectedPros")
				.substring(0, request.getParameter("selectedPros").length() - 1)
				.split(";");
		String transportNumber  = request.getParameter("transportNumber");
		for(String pro : selectedPros){
			vps.updateTransNumber(pro, transportNumber);
		}
		
		PrintWriter out = response.getWriter();
		out.print("success");
		out.flush();
		out.close();
		
	}


	private void handleTransSearchRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/javascript;charset=utf-8");   
		response.setHeader("Cache-Control", "no-cache");   
		String [] keyWords = request.getParameter("keyWords").split("\\s+");
		String keyWordsList = "";
		for(String keyWord : keyWords){
			if(!keyWord.equals("")){
				keyWordsList += keyWord + "|";
			}
		}
		Conditionor con = Zql.condition("enterpriseId", "=", getEnterpriseId(request));
		if(!keyWordsList.equals("")){
			keyWordsList = keyWordsList.substring(0, keyWordsList.length() - 1);
			con.and(Zql.condition("concat(startPlace,endPlace,startTime,endTime,license,driver)", "regexp", keyWordsList));
		}
		List<TransRecord > transList = tranS.getTransByCon(con);
		
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONString(transList));
		out.flush();
		out.close();
	}


	private void handleRfidBindRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String rfidNum = request.getParameter("rfidNum");
		String [] selectedPros = request.getParameter("selectedPros")
				.substring(0, request.getParameter("selectedPros").length() - 1)
				.split(";");
		for(int i = 0; i < selectedPros.length; i++){
			vps.updateRfid(selectedPros[i], rfidNum);
		}
		PrintWriter out = response.getWriter();
		out.print("success");
		out.flush();
		out.close();
	}


	private void handleRfidSearchRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String rfidTime = request.getParameter("rfidTime");
		String rfidTimeOffset = request.getParameter("rfidTimeOffset");
		String operator = request.getParameter("operator");
		Conditionor con = Zql.condition("action", "=", 1);
		if(rfidTime != null && !rfidTime.equals("")){
			con.and(Zql.betweenAnd("time",
					hourPlusX(rfidTime, 0 - Float.parseFloat(rfidTimeOffset)), 
					hourPlusX(rfidTime,Float.parseFloat(rfidTimeOffset))).brackets());
		}
		if(operator != null && !operator.equals("")){
			con.and(Zql.condition("personId", "=", operator));
		}
		System.out.println(Arrays.toString(con.getParams()));
		List<InOut> dataList = inOutS.queryByCondition(con);
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONString(dataList));
		out.flush();
		out.close();
	}


	private ModelAndView pushAllInfo(HttpServletRequest request,
			HttpServletResponse response){
		String qrCode = request.getParameter("qrCode").trim();
		VirtualProduction vp = vps.getProductionByQrcode(qrCode);
		ModelAndView mv = null;
		mv = new ModelAndView("dashboard");
		mv.addObject("content_page", "/WEB-INF/view/allRecordWindow.vm");
		String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
		setUserPosition(request,mv);
		mv.addObject("locations", locations);
		mv.addObject("location_info", "仓库管理平台");
		mv = setStorageList(request, mv);
		mv = setWorkStorage(request, mv);
		mv = setUser(request, mv);
		setSaleInfoForMV(vp.getSaleNumber(), mv);
		setStorageInfoForMV(vp.getRfid(), mv);
		setTransInfoForMV(vp.getTransNumber(), mv);
		setProcessInfoForMV(vp.getProcessNumber(), mv);	
		mv.addObject("vpInfo", JSON.toJSONString(vp));
		return mv;
	}
	
	private ModelAndView pushSaleInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String saleNumber = request.getParameter("saleNumber");
		ModelAndView mv = new ModelAndView("saleRecordWindow");
	
		return setSaleInfoForMV(saleNumber,mv);
	}
	
	private ModelAndView pushStorageInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView("rfidRecordWindow");
		String rfid = request.getParameter("rfid").trim();
		return setStorageInfoForMV(rfid, mv);
	}
	
	private ModelAndView pushProcessInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String processNumber = request.getParameter("processNumber").trim();
		ModelAndView mv = new ModelAndView("processRecordWindow");
		return setProcessInfoForMV(processNumber,mv);
	}

	private ModelAndView pushTransInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String transNumber = request.getParameter("transNumber").trim();
		ModelAndView mv = new ModelAndView("transRecordWindow");
		return setTransInfoForMV(transNumber,mv);
	}
	
	/**
	 * not used
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void pushRawInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String rawId = request.getParameter("rawId").trim();
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONString(rawService.getRawmaterialById(rawId)));
		out.flush();
		out.close();
	}
	
	private PageInfo<VirtualProduction>  getPageInfoBySerach(Conditionor con, HttpServletRequest request) {
		// TODO Auto-generated method stub
		String [] keyWords = request.getParameter("keyWords").split("\\s+");
		String keyWordsList = "";
		for(String keyWord : keyWords){
			if(!keyWord.equals("")){
				keyWordsList += keyWord + "|";
			}
		}

		if(!keyWordsList.equals("")){
			keyWordsList = keyWordsList.substring(0, keyWordsList.length() - 1);
			//add qrCode to enable the concat function
			con.and(Zql.condition("concat(productionName,qrCode)", "regexp", keyWordsList));
		}
		
		PageInfo<VirtualProduction > pageInfo = new PageInfo<VirtualProduction>();
		setPageInfo(pageInfo,request);
		vps.getAllProductionByPage(pageInfo, con, getEnterpriseId(request));
		return pageInfo;
	}

	@SuppressWarnings("deprecation")
	private ModelAndView handleDefaultRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		ModelAndView mv = null;
		mv = new ModelAndView("dashboard");
		mv.addObject("content_page", "/WEB-INF/view/productionManager.vm");
		String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
		setUserPosition(request,mv);
		mv.addObject("locations", locations);
		mv.addObject("location_info", "仓库管理平台");
		mv = setStorageList(request, mv);
		mv = setWorkStorage(request, mv);
		mv = setUser(request, mv);
		PageInfo<VirtualProduction> pageInfo = new PageInfo<VirtualProduction>();
		setPageInfo(pageInfo,request);
		int status = 0;
		if(request.getParameter("status") != null){
			status = Integer.parseInt(request.getParameter("status").trim());
		}
		Conditionor con = Zql.condition("virtual_production.status", "=", status);
		
		if(request.getParameter("keyWords") != null) {
			pageInfo = getPageInfoBySerach(con,request);
			mv.addObject("pageInfo", JSON.toJSONString(pageInfo));
			mv.addObject("keyWords",request.getParameter("keyWords"));
		}else{
			String sortWords = request.getParameter("sortWords");
			if(sortWords != null) {
				String [] sortArr = sortWords.split(" ");
				mv.addObject("pageInfo", JSON.toJSONString(
						vps.getAllProductionByOrder(pageInfo, con, getEnterpriseId(request), 
								sortArr[1].substring(0,sortArr[1].indexOf('.')), sortArr[0], sortWords.substring(sortWords.indexOf(' ') + 1))));
				mv.addObject("sortWords",sortWords);
			}
			else {
				mv.addObject("pageInfo", JSON.toJSONString(vps.getAllProductionByPage(pageInfo, con, getEnterpriseId(request))));
			}
		}
		
		mv.addObject("status", status);
		
		//确保运行环境有二维码图片
		//二维码的路径约定为./plublic/qrcode/企业id/二维码内容.png
		for(VirtualProduction vp : pageInfo.getDataList()){
			String enDir = request.getRealPath("")+"\\public\\qrcode\\"+vp.getEnterpriseId();
			String qrCode = enDir +"\\"+vp.getQrCode()+".png";
			File qrCodePng = new File(qrCode);
			if(!qrCodePng.exists()){
				File enterpriseDir = new File(enDir);
				if(!enterpriseDir.exists()){
					enterpriseDir.mkdirs();
				}
				ZXingUtil.encodeQRCodeImage(vp.getQrCode(), null,
						qrCode,
						500	, 500, null);
			}
		}
		return mv;
	}


	private void handleSaleRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		// TODO Auto-generated method stub
		SaleRecord sr = new SaleRecord();
		sr.setEnterpriseId(getEnterpriseId(request));
		sr.setFreezerNumber(request.getParameter("sale_freezer_number"));
		sr.setSaleMarket(request.getParameter("sale_market"));
		sr.setSalePlace(request.getParameter("sale_place"));
		sr.setSaleTime(request.getParameter("sale_time"));
		String saleNumber = makeSaleNumber(request);
		while(true){
			sr.setSaleNumber(saleNumber);
			if(saleS.addASaleRecord(sr) == 1){
				break;
			}else{
				saleNumber = makeSaleNumber(request);
			}
		}
		String [] selectedPros = request.getParameter("selectedPros")
				.substring(0, request.getParameter("selectedPros").length() - 1)
				.split(";");
		for(int i = 0 ; i < selectedPros.length ; i++){
			vps.updateSaleNumber(selectedPros[i], saleNumber);
		}
		PrintWriter out = response.getWriter();
		out.print("success");
		out.flush();
		out.close();
	}


	private void handleShipRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		TransRecord tr = new TransRecord();
		tr.setDriver(request.getParameter("trans_driver"));
		tr.setLicense(request.getParameter("trans_license"));
		tr.setStartPlace(request.getParameter("trans_start_place"));
		tr.setEndPlace(request.getParameter("trans_end_place"));
		tr.setStartTime(request.getParameter("trans_start_time"));
		tr.setEndTime(request.getParameter("trans_end_time"));
		tr.setStatus(0);
		tr.setEnterpriseId(getEnterpriseId(request));
		String transportNumber = makeTransNumber(request);
		while(true){
			tr.setTransportNumber(transportNumber);
			if(tranS.addATransRecord(tr) == 1){ //插入失败？ 再次尝试
				break;
			}else {
				transportNumber = makeTransNumber(request);
			}
		}
		String [] selectedPros = request.getParameter("selectedPros")
									.substring(0, request.getParameter("selectedPros").length() - 1)
									.split(";");
		for(int i = 0 ; i < selectedPros.length ; i++){
			vps.updateTransNumber(selectedPros[i], transportNumber);
		}
		PrintWriter out = response.getWriter();
		out.print("success");
		out.flush();
		out.close();
	}
	
	private ModelAndView setTransInfoForMV(String transNumber,ModelAndView mv){
		TransRecord tr = tranS.getTransRecordByPK(transNumber);
		mv.addObject("transInfo", JSON.toJSONString(tr));
		return mv;
	}
	
	private ModelAndView setProcessInfoForMV(String processNumber,
			ModelAndView mv) {
		// TODO Auto-generated method stub
		ProcessRecord pr = prs.getProcessRecordByProcessNumber(processNumber);
		mv.addObject("processRecord", JSON.toJSONString(pr));
		mv.addObject("rawList",JSON.toJSONString(rawService.getRawsByIds(Arrays.asList(pr.getRawIdList().trim().split(";")))));
		return mv;
	}
	
	private ModelAndView setSaleInfoForMV(String saleNumber, ModelAndView mv) {
		// TODO Auto-generated method stub
		SaleRecord sr = saleS.getSaleRecordByPK(saleNumber);
		mv.addObject("saleInfo", sr == null ? null:JSON.toJSONString(sr));
		return mv;
	}
	
	private String makeTransNumber(HttpServletRequest request){
		return new Date().getTime() + getLoginUser(request) + getEnterpriseId(request);
	}
	
	private String makeSaleNumber(HttpServletRequest request){
		return getLoginUser(request) + new Date().getTime() + getEnterpriseId(request);
	}
	private ModelAndView setStorageInfoForMV(String rfid,ModelAndView mv){
		//当前产品的出入库信息
		List<InOut> inOutList = inOutS.queryByCardNum(rfid);
		
		assert inOutList.size() >= 1;
		mv.addObject("inOutList", JSON.toJSONString(inOutList));
		
		int storageId = inOutList.get(0).getStorageId();
		//当前产品所处仓库
		Storage currentStorage = storageS.getStorageById(storageId);
		
		mv.addObject("storageInfo",JSON.toJSONString(currentStorage));
		
		//当前仓库所有的传感器
		List<Sensor> sensorList = sensorS.getSensorListByStorageId(storageId);
		
		//计算查询温度信息的起止时间，如果只有入库，则查询入库时间到当前时间的数据，反之查询入库到出库的数据
		//1是入库，0是出库
		String startTime = null;
		String endTime = null;
		for(InOut io : inOutList){
			if(io.getAction() == 1){
				startTime = io.getTime();
			}else{
				endTime = io.getTime();
			}
		}
		if(endTime == null){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
			endTime = formatter.format(new Date());
		}
		
		//根据时间获取时间并进行处理	
		Map<String,List<Float>> tempData = new HashMap<String, List<Float>>();//每个传感器的温度数据
		Map<String,String> exceptionRate = new HashMap<String,String>();
		Map<String,String> extToName =  new HashMap<String,String>();
		List<String> timeLine = new ArrayList<String>();
		
		long days = computDayInterval(startTime,endTime);
		for(Sensor sensor : sensorList){
			//计算每天的平均值
			List<Float> sensorTempByDay = new ArrayList<Float>();
			List<Temperature> tempList = tempS.getDataByTime(sensor.getExtAddr(), startTime, endTime);
			for(int i = 0; i <= days; i++){
				Float dayTemp = (float) 0.0;
				int count = 0;
				String startDay = dayPlusX(startTime.substring(0, 10),i) + " 00:00:00";
				String endDay = dayPlusX(startDay.substring(0, 10),1) + " 00:00:00";
				timeLine.add(startDay.substring(0,10));
				Iterator<Temperature> it = tempList.iterator();
				while(it.hasNext()){
					Temperature temp = it.next();
					if(temp.getRecordTime().compareTo(startDay) >= 0 && temp.getRecordTime().compareTo(endDay) < 0){
						dayTemp += temp.getTemperature();
						count++;
						it.remove();
					}
				}
				sensorTempByDay.add(count == 0 ? 0:dayTemp / count);	
			}
			tempData.put(sensor.getExtAddr(), sensorTempByDay);	
			extToName.put(sensor.getExtAddr(), sensor.getName());
			
			exceptionRate.put(sensor.getExtAddr(),
					tempS.getLowerNumber(sensor.getExtAddr(), startTime, endTime, currentStorage.getMinTemp())
					+";"
					+tempS.getNormalNumber(sensor.getExtAddr(), startTime, endTime, currentStorage.getMaxTemp(), currentStorage.getMinTemp())
					+";"
					+tempS.getExceedNumber(sensor.getExtAddr(), startTime, endTime, currentStorage.getMaxTemp())
					);		
		}
		
		//接下来去掉脏数据
		Iterator<?> it = tempData.entrySet().iterator();
		List<Float> averageList = new ArrayList<Float>();
		List<Integer> averageCount = new ArrayList<Integer>();
		for(int i = 0; i <= days; i++){
			averageList.add(0F);
			averageCount.add(0);
		}
		while(it.hasNext()){
			Map.Entry<String, List<Float>> entry = (Entry<String, List<Float>>) it.next();
			List<Float> dataList = entry.getValue();
			int zeroCount = 0;
			for(int i = 0; i < dataList.size(); i++){
				if(dataList.get(i) == 0){
					zeroCount++;
				}else{
					averageList.set(i, averageList.get(i) + dataList.get(i));
					averageCount.set(i, averageCount.get(i) + 1);
				}
			}
			if(zeroCount >= dataList.size() / 2){
				it.remove();
			}
		}
		
		DecimalFormat   fnum  =   new  DecimalFormat("##0.00"); 
		for(int i = 0; i <= days; i++){
			averageList.set(i, 
					Float.parseFloat(fnum.format(averageCount.get(i) == 0? 0 : averageList.get(i) / averageCount.get(i)
							)));
		}
		
		it = exceptionRate.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, String> entry = (Entry<String, String>) it.next();
			String value = entry.getValue();
			if(value.trim().equalsIgnoreCase("0;0;0")){
				it.remove();
			}
		}
		tempData.put("average", averageList);
		mv.addObject("exceptionRate",JSON.toJSONString(exceptionRate));
		mv.addObject("tempData",JSON.toJSONString(tempData));
		mv.addObject("extToName", JSON.toJSONString(extToName));
		mv.addObject("timeLine", JSON.toJSONString(timeLine));
		return mv;
	}
	
	private String dayPlusX(String date, int i) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return df.format(new Date(df.parse(date).getTime() + i * 1000 * 24 * 60 * 60));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	private String hourPlusX(String date, float i) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			long old = df.parse(date).getTime();
			long add =   (long)(i * 1000 * 1 * 60 * 60);
			long newl = old + add;
			String re = df.format(new Date(newl));
			return re;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	private long computDayInterval(String startTime, String endTime) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			return (df.parse(endTime.substring(0,10)).getTime() - df.parse(startTime.substring(0,10)).getTime()) / (1000 * 60 * 60 * 24);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * @return the vps
	 */
	public IVirtualProductionService getVps() {
		return vps;
	}

	/**
	 * @param vps the vps to set
	 */
	public void setVps(IVirtualProductionService vps) {
		this.vps = vps;
	}

	/**
	 * @return the rawService
	 */
	public IRawmaterialService getRawService() {
		return rawService;
	}

	/**
	 * @param rawService the rawService to set
	 */
	public void setRawService(IRawmaterialService rawService) {
		this.rawService = rawService;
	}

	/**
	 * @return the prs
	 */
	public IProcessRecordService getPrs() {
		return prs;
	}

	/**
	 * @param prs the prs to set
	 */
	public void setPrs(IProcessRecordService prs) {
		this.prs = prs;
	}

	/**
	 * @return the inOutS
	 */
	public IInOutService getInOutS() {
		return inOutS;
	}

	/**
	 * @param inOutS the inOutS to set
	 */
	public void setInOutS(IInOutService inOutS) {
		this.inOutS = inOutS;
	}

	/**
	 * @return the storageS
	 */
	public IStorageService getStorageS() {
		return storageS;
	}

	/**
	 * @param storageS the storageS to set
	 */
	public void setStorageS(IStorageService storageS) {
		this.storageS = storageS;
	}

	/**
	 * @return the sensorS
	 */
	public ISensorService getSensorS() {
		return sensorS;
	}

	/**
	 * @param sensorS the sensorS to set
	 */
	public void setSensorS(ISensorService sensorS) {
		this.sensorS = sensorS;
	}

	/**
	 * @return the tempS
	 */
	public ITempService getTempS() {
		return tempS;
	}

	/**
	 * @param tempS the tempS to set
	 */
	public void setTempS(ITempService tempS) {
		this.tempS = tempS;
	}

	/**
	 * @return the tranS
	 */
	public ITransRecordService getTranS() {
		return tranS;
	}

	/**
	 * @param tranS the tranS to set
	 */
	public void setTranS(ITransRecordService tranS) {
		this.tranS = tranS;
	}


	/**
	 * @return the saleS
	 */
	public ISaleRecordService getSaleS() {
		return saleS;
	}


	/**
	 * @param saleS the saleS to set
	 */
	public void setSaleS(ISaleRecordService saleS) {
		this.saleS = saleS;
	}


}
