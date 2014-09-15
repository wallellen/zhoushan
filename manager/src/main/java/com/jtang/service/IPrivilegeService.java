package com.jtang.service;

import java.util.List;
import com.jtang.model.Privilege;

public interface IPrivilegeService {
	
	/**
	 * 返回完整的privilege
	 * @return
	 */
	public List<Privilege> getTreeprivilege( );

}
