package com.jtang.service;

import java.util.List;

import com.jtang.model.Enterprise;
import com.jtang.model.PageInfo;

public interface IEnterpriseService {
	
	public List<Enterprise> getAllEnterprises();
	
	public Enterprise getEnterPriseByIdOrName(String idOrName);
	
	public PageInfo<Enterprise> getAllEnterprisesByPage(PageInfo<Enterprise> pageInfo);

}
