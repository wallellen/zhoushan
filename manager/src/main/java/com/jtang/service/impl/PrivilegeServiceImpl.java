package com.jtang.service.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.jtang.dao.BasicManagerDao;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.Privilege;
import com.jtang.service.IPrivilegeService;

public class PrivilegeServiceImpl implements IPrivilegeService {

	private BasicManagerDao manager;

	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}

	public List<Privilege> getTreeprivilege() {
		// TODO Auto-generated method stub
		List<Privilege> list = manager.query("select * from privilege_tree ", null, null, RowMapperEnum.PRIVILEGE.getMapperName());
		return list;
		
	}

	public List<Map<String, Object>> getPersonPrivilege(String userId) {
		// TODO Auto-generated method stub
		Object[] paras = {userId};
		int[] types = {Types.INTEGER};
		return manager.queryForListMap("select *　from privilege where user_id=?", paras, types);
	}

}
