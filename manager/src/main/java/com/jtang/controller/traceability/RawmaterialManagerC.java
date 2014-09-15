package com.jtang.controller.traceability;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jt.sensordata.bean.GlobalVariable;
import jt.sensordata.main.ClientManager;

import org.jboss.netty.handler.codec.http.HttpRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jtang.controller.common.ControllerParent;
import com.jtang.dao.sqlUtil.Conditionor;
import com.jtang.dao.sqlUtil.Zql;
import com.jtang.model.Message;
import com.jtang.model.PageInfo;
import com.jtang.model.ProcessRecord;
import com.jtang.model.Rawmaterial;
import com.jtang.model.VirtualProduction;
import com.jtang.qrcode.ZXingUtil;
import com.jtang.service.IProcessRecordService;
import com.jtang.service.IRawmaterialService;
import com.jtang.service.IVirtualProductionService;
public class RawmaterialManagerC extends ControllerParent implements Controller {

	public IRawmaterialService rawService;
	
	private IProcessRecordService processRecordService;
	
	private IVirtualProductionService virtualProductionService;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if(action == null || action.trim().equals("")){

			return defaultRequest(request,response);
		}
		if(action.trim().equals("process")){ //点击立即加工
			String data = request.getParameter("data");
			@SuppressWarnings("unchecked")
			Map<String,Object> dataJson = JSON.parseObject(data, Map.class);
			if(makeProduction(dataJson,request)){
				PrintWriter out = response.getWriter();
				out.print("success");
				out.flush();
				out.close();		
			}else{
				PrintWriter out = response.getWriter();
				out.print("fail");
				out.flush();
				out.close();
			}
			return null;
		}
		return null;
	}


	private PageInfo<Rawmaterial>  getPageInfoBySerach(HttpServletRequest request) {
		// TODO Auto-generated method stub
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
			con.and(Zql.condition("concat(Name,Manufacturer,ProductionPlace,ProductionDate,QualityEvaluation,amount,spare)", "regexp", keyWordsList));
		}
		
		PageInfo<Rawmaterial > pageInfo = new PageInfo<Rawmaterial>();
		setPageInfo(pageInfo,request);
		rawService.getUnuesedRawmaterialsByCon(pageInfo, con);
		return pageInfo;
	}

	/**
	 * @param proInfo
	 * @return
	 */
	@Transactional
	@SuppressWarnings("deprecation")
	private boolean makeProduction(Map<String, Object> proInfo,HttpServletRequest request) {
		// TODO Auto-generated method stub
		String productionName = (String) proInfo.get("productionName");
		String productionCount = (String) proInfo.get("productionCount");
		String processLoc = (String) proInfo.get("processLoc");
		String processEnv = (String) proInfo.get("processEnv");
		String rawIdList = "";
		String rawCountList = "";
		JSONArray raws = (JSONArray) proInfo.get("raws");
		for(int i = 0; i < raws.size() ; i++){
			@SuppressWarnings("unchecked")
			Map<String, String> raw = raws.getObject(i, Map.class);
			
			//从数据库中扣减原料
			rawService.minusRawCount(raw.get("rawId"), Integer.parseInt(raw.get("useCount").trim()));
			
			if(Integer.parseInt(raw.get("spareCount")) - Integer.parseInt(raw.get("useCount")) <= 100) {
				Message msg = new Message();
				msg.setContent("批次号为["+raw.get("rawId")+"]的原料剩余量不足100个单位，请注意即使采购！");
				msg.setFromWho("platform");
				String now = GlobalVariable.formatter.format(new Date());
				msg.setTime(now);
				msg.setTitle("原料不足预警！");
				msg.setToWho(getLoginUser(request));
				msg.setMessageId("platform" + new Date().getTime() + getLoginUser(request));
				ClientManager.sendMessageToPerson(getLoginUser(request), msg, true);
			}
			
			rawIdList += raw.get("rawId") + ((i == raws.size() - 1)?"":";");
			rawCountList += raw.get("useCount") + ((i == raws.size() - 1)?"":";");
		}
		//生成加工记录
		ProcessRecord pr = new ProcessRecord();
		pr.setEnterpriseId(getEnterpriseId(request));
		pr.setProcessEnv(processEnv);
		pr.setProcessLoc(processLoc);
		pr.setProcessNumber(makeProcessNumber(request));
		pr.setProcessTime(formatter.format(new Date()));
		pr.setProcessUser(getLoginUser(request));
		pr.setRawCountList(rawCountList);
		pr.setRawIdList(rawIdList);
		processRecordService.addAProcessRecord(pr);
		
		//生成虚拟产品记录
		VirtualProduction vp =
				new VirtualProduction();
		vp.setEnterpriseId(pr.getEnterpriseId());
		vp.setProcessNumber(pr.getProcessNumber());
		vp.setProductionCount(Integer.parseInt(productionCount.trim()));
		vp.setProductionName(productionName);
		vp.setStatus(0);
		vp.setQrCode(makeQRCode());
		virtualProductionService.addProduction(vp, "rfid","transNumber","saleNumber");
		
		//生成二维码图片
		//二维码的路径约定为./plublic/qrcode/企业id/二维码内容.png
		File enterpriseDir = new File(request.getRealPath("")+"\\public\\qrcode\\"+vp.getEnterpriseId());
		if(!enterpriseDir.exists()){
			enterpriseDir.mkdirs();
		}
		ZXingUtil.encodeQRCodeImage(vp.getQrCode(), null,
				request.getRealPath("")+"\\public\\qrcode\\"+vp.getEnterpriseId()+"/"+vp.getQrCode()+".png",
				500	, 500, null);
		return true;
	}

	private String makeQRCode() {
		// TODO Auto-generated method stub
		//暂时的一种办法
		return  UUID.randomUUID().toString();
	}

	private ModelAndView defaultRequest(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView("dashboard");
		mv.addObject("content_page", "/WEB-INF/view/rawmaterialManager.vm");
		String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
		setUserPosition(request,mv);
		mv.addObject("locations", locations);
		mv.addObject("location_info", "仓库管理平台");
		mv = setStorageList(request, mv);
		mv = setWorkStorage(request, mv);
		mv = setUser(request, mv);
		if(request.getParameter("keyWords") == null){
			PageInfo<Rawmaterial > pageInfo = new PageInfo<Rawmaterial>();
			setPageInfo(pageInfo,request);
			
			String sortWords = request.getParameter("sortWords");
			if(sortWords != null) {
				mv.addObject("pageInfo",JSON.toJSONString(rawService.getUnuesedRawmaterialsByOrder(pageInfo, getEnterpriseId(request),sortWords)));
				mv.addObject("sortWords",sortWords);
			}else {
				mv.addObject("pageInfo",JSON.toJSONString(rawService.getUnuesedRawmaterialsByPage(pageInfo, getEnterpriseId(request))));
			}
		}else{
			PageInfo<Rawmaterial > pageInfo = getPageInfoBySerach(request);
			mv.addObject("pageInfo",JSON.toJSONString(pageInfo));
			mv.addObject("keyWords",request.getParameter("keyWords"));
		}
		return mv;
	}
	
	
	private String makeProcessNumber(HttpServletRequest request){
		return new Date().getTime() + getEnterpriseId(request) + getLoginUser(request);
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
	 * @return the processRecordService
	 */
	public IProcessRecordService getProcessRecordService() {
		return processRecordService;
	}

	/**
	 * @param processRecordService the processRecordService to set
	 */
	public void setProcessRecordService(IProcessRecordService processRecordService) {
		this.processRecordService = processRecordService;
	}

	/**
	 * @return the virtualProductionService
	 */
	public IVirtualProductionService getVirtualProductionService() {
		return virtualProductionService;
	}

	/**
	 * @param virtualProductionService the virtualProductionService to set
	 */
	public void setVirtualProductionService(
			IVirtualProductionService virtualProductionService) {
		this.virtualProductionService = virtualProductionService;
	}
}
