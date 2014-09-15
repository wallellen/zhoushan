package com.jtang.controller.traceability;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jtang.controller.common.ControllerParent;
import com.jtang.model.SaleRecord;
import com.jtang.model.TransRecord;
import com.jtang.service.ISaleRecordService;
import com.jtang.service.ITransRecordService;

public class DataManagerC extends ControllerParent implements Controller {

	private ITransRecordService tranS;
	private ISaleRecordService saleS;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if(action == null || action.trim().equals("")){
			return handleDefaultRequest(request);
		}else{
			action = action.trim().toLowerCase();
			switch (action) {
			case "submittransdata" :
				importTransRecord(request,response);
				return null;
			case "submitsaledata" :
				importSaleRecord(request,response);
				return null;
			default:
				break;
			}
			return null;
		}
	}

	private void importSaleRecord(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
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
		PrintWriter out = response.getWriter();
		out.print("success");
		out.flush();
		out.close();
	}

	private void importTransRecord(HttpServletRequest request,
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
		PrintWriter out = response.getWriter();
		out.print("success");
		out.flush();
		out.close();
	}

	private ModelAndView handleDefaultRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		ModelAndView mv = null;
		mv = new ModelAndView("dashboard");
		mv.addObject("content_page", "/WEB-INF/view/dataManager.vm");
		String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
		setUserPosition(request,mv);
		mv.addObject("locations", locations);
		mv.addObject("location_info", "仓库管理平台");
		mv = setStorageList(request, mv);
		mv = setWorkStorage(request, mv);
		mv = setUser(request, mv);
		return mv;
	}
	
	private String makeTransNumber(HttpServletRequest request){
		return new Date().getTime() + getLoginUser(request) + getEnterpriseId(request);
	}
	private String makeSaleNumber(HttpServletRequest request){
		return getLoginUser(request) + new Date().getTime() + getEnterpriseId(request);
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

	//next is getter and setter for spring 
	
}
