/**
 * 
 */
package com.jtang.controller.common;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jt.sensordata.main.ClientManager;

import org.springframework.web.servlet.ModelAndView;

import com.jtang.model.PageInfo;
import com.jtang.model.Storage;
import com.jtang.model.User;

/**
 * @author Administartor
 *
 */
public class ControllerParent {
	
	/**
	 * set the user's position for velocity
	 */
	
	protected void setUserPosition(HttpServletRequest request,ModelAndView mv){
		HttpSession hs = request.getSession();
		if(hs.getAttribute("user") != null){
			User loginUser = (User) hs.getAttribute("user");
			mv.addObject("userPosition", loginUser.getPositionId());
		}
	}

	/**
	 * 将当前登录用户的有权限的仓库列表保存到试图当中供显示
	 * @param request
	 * @param mv
	 * @return
	 */
	protected ModelAndView setStorageList(HttpServletRequest request,ModelAndView mv){
		if(request.getSession().getAttribute("storageList")!=null){
			mv.addObject("storageList",request.getSession().getAttribute("storageList"));
		}
		return mv;
	}
	
	protected ModelAndView setWorkStorage(HttpServletRequest request,ModelAndView mv){
		if(request.getSession().getAttribute("workStorage")!=null){
			mv.addObject("workStorage",request.getSession().getAttribute("workStorage"));
		}
		return mv;
	}
	
	/**
	 * 当前登陆用户名
	 * @param request
	 * @param mv
	 * @return
	 */
	protected ModelAndView setUser(HttpServletRequest request,ModelAndView mv){
		if(request.getSession().getAttribute("user")!=null){
			User user = (User)request.getSession().getAttribute("user");
			mv.addObject("userName",user.getName());
		}
		return mv;
	}
	
	protected String getWorkStorageId(HttpServletRequest request){
		HttpSession hs = request.getSession();
		
		Object storageId = hs.getAttribute("workStorage");
		
		return storageId==null?"":storageId.toString();
		
	}
	
     /**
	 * 登陆验证
	 * @param request
	 * @return
	 * 	    0  未登录
	 *      1  已登录
	 */
	protected int checkLogin(HttpServletRequest request)
	{
		User user = (User) request.getSession().getAttribute("user");
		if(user == null)
			return 0;
		else 
			return 1;
	}
	
	protected HashMap<String,Float> getMaxMinTemp(HttpServletRequest request){
		if(request.getSession().getAttribute("storageList")==null || request.getSession().getAttribute("workStorage")==null){
			return null;
		}
		HashMap<String,Float>  maxMin = new HashMap<String,Float> ();
		int workStorageId = (Integer) request.getSession().getAttribute("workStorage");
		List<Storage> storageList = (List<Storage>) request.getSession().getAttribute("storageList");
		
		for(int i=0;i<storageList.size();i++){
			if(workStorageId == storageList.get(i).getId()){
				maxMin.put("max", storageList.get(i).getMaxTemp());
				maxMin.put("min", storageList.get(i).getMinTemp());
				return maxMin;
			}
		}
		return null;
	}
	
	/**
	 * 获取当前登录用户所属的企业
	 */
	protected String getEnterpriseId(HttpServletRequest request){
		HttpSession hs = request.getSession();
		if(hs.getAttribute("user") != null){
			User loginUser = (User) hs.getAttribute("user");
			return loginUser.getEnterpriseId();
		}
		return "";
	}
	
	protected String getLoginUser(HttpServletRequest request){
		HttpSession hs = request.getSession();
		if(hs.getAttribute("user") != null){
			User loginUser = (User) hs.getAttribute("user");
			return loginUser.getId();
		}
		return "";
	}
	
	protected <T> void setPageInfo(PageInfo<T> pageInfo,HttpServletRequest request){
		if(request.getParameter("currentPage") == null){
			//说明是初始化状态，按默认值执行
			pageInfo.setCurrentPage(1);
			pageInfo.setRecordsPP(5);
		}else{
			pageInfo.setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));
			pageInfo.setRecordsPP(Integer.parseInt(request.getParameter("recordsPP")));
			//如果不需要重新查询一共有多少页，设置pageInfo的pageNum即可
			if(request.getParameter("pageNum") != null){
				//说明是选择页码到达这一步，故pageNum，totalCount等不用修改.
				pageInfo.setPageNum(Integer.parseInt(request.getParameter("pageNum")));
				pageInfo.setTotalCount(Integer.parseInt(request.getParameter("totalCount")));
			}
		}
	}
	
	/**
	 * 发送消息给somebody
	 * flag标注是否需要将该条记录持久化到数据库，true表示需要，false表示不需要
	 * @param userId
	 * @param msg
	 * @param flag
	 */
	protected void sendMessageToUser(String userId, Object msg, boolean flag){
		ClientManager.sendMessageToPerson(userId, msg,flag);
	}
}
