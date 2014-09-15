package com.jtang.service.impl;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jtang.dao.BasicManagerDao;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.PrivilegeUser;
import com.jtang.service.IPrivilegeUserService;

public class PrivilegeUserServiceImpl implements IPrivilegeUserService {

	public BasicManagerDao manager;  //由Spring注入实体类

	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}
	
	
	public boolean hasPrivilege(String privilegeName, String privilegeUser) {
		// TODO Auto-generated method stub
		String sql = "select * from privilege_user where privilege_name = ? and"
				+ " user_id = ?";
		int [] argTypes = {Types.VARCHAR,Types.VARCHAR};
		Object [] args = {privilegeName,privilegeUser};

		List<PrivilegeUser> pulist = manager.query(sql, args, argTypes,RowMapperEnum.PRIVILEGEUSER.getMapperName());
		if(pulist == null || pulist.size() == 0){
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> getPersonPrivilege(String userId) {
		// TODO Auto-generated method stub
		Object[] paras = {userId};
		Map<String,Object> res= new HashMap<String,Object>();
		
		try {
			res.put("priname", (List<String>)manager.queryForList("select privilege_name from privilege_user where user_id =?", paras, Class.forName("java.lang.String")));
			res.put("parent", (List<Integer>)manager.queryForList("select  privilege_tree.which_system  from privilege_user , privilege_tree where privilege_user.user_id=? and privilege_tree.privilege_name = privilege_user.privilege_name",
					          paras, Class.forName("java.lang.Integer")));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return manager.queryForList("select * from privilege_user where user_id=?", paras, types);
		return res;
	}

}
