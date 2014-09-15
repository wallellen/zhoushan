package com.jtang.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.jtang.dao.sqlUtil.Conditionor;
import com.jtang.model.InOut;

public interface IInOutService {
	public List<InOut> getInOutByCardnum(String cardNum);
	/**
	 * 增加单个出入库
	 * @param cardNum
	 * @param cardTime
	 * @param action
	 * @param personId
	 * @param rfidCount
	 * @return
	 */
	public int addInOut(String cardNum,String cardTime,int action,String personId,int rfidCount,int storageId);
	/**
	 * 批量增加出入库
	 * @param size
	 * @param action
	 * @param personId
	 * @param rfidCount
	 * @param cards
	 * @param cardsTime
	 * @return
	 */
	//public void addInOuts(int size,int action,String personId,int rfidCount,JSONArray cards,JSONArray cardsTime,String proName,int storageId);
	public List<InOut> searchTodayIn(String proName,String personid,String startTime,int storageId);
	public List<InOut> searchTodayOut(String proName,String personid,String startTime,int storageId);
	public List<InOut> searchTodayInOut(String proName,String personId,String startTime,int action,int storageId);
	public int countInToday(int storageId);
	public int countOutToday(int storageId);
	public List<InOut> queryTodayIn(int storageId);
	public List<InOut> queryTodayOut(int storageId); 
	public List<InOut> queryTodayAll(int storageId);
	
	/**
	 * 根据时间分类今日出入库信息
	 * @param storageId
	 * @return
	 */
	
	public List<Map<String,Object>> queryTodayInGroupByTime(int storageId);
	public List<Map<String,Object>> queryTodayOutGroupByTime(int storageId);
	/**
	 * 查询指定时间段内出入库情况
	 * 按商品分类，可分别获取商品出入库总量
	 * @param storageId
	 * @param startTime
	 * @param endTime
	 * @param proId
	 * @param personId
	 * @return
	 */
	public List<Map<String,Object>> queryInGroupByTimeAndPro(int storageId,String startTime,String endTime,String proName,String personId);
	public List<Map<String,Object>> queryOutGroupByTimeAndPro(int storageId,String startTime,String endTime,String proName,String personId);
	
	/**
	 * 根据条件查询出入库信息
	 * @param action
	 * 		2 全部
	 *   	1 入库
	 *   	0 出库
	 * @param storageId
	 *      仓库ID 必须指定
	 * @param startTime
	 * @param endTime
	 * @param proId
	 * 	    0 不限制，查询全部商品
	 * @param personId
	 *      0 不限制，查询全部商品
	 * @return
	 */
	public List<InOut> queryAll(int storageId,String startTime,String endTime,String proName,String personId);
	public List<InOut> queryIn(int storageId,String startTime,String endTime,String proName,String personId);
	public List<InOut> queryOut(int storageId,String startTime,String endTime,String proName,String personId);
    public List<InOut> queryByCardNum(String cardNum);
	List<InOut> queryByCondition(Conditionor con);
}
