package com.jtang.service.impl;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.jtang.dao.BasicManagerDao;
import com.jtang.dao.sqlUtil.Conditionor;
import com.jtang.dao.sqlUtil.Zql;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.InOut;
import com.jtang.service.IInOutService;


public class InOutServiceImpl implements IInOutService{

	private BasicManagerDao manager;
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}
	
	public int addInOut(String cardNum,String cardTime,int action,String personId,int rfidCount,int storageId) 
	{
		// TODO Auto-generated method stub
		Object[] paras = {cardNum,action,personId,rfidCount,cardTime,storageId};
		int[] argTypes = {Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.INTEGER};
		return manager.add("insert into in_out(cardNum,action,personId,bindCount,time,storageId) values(?,?,?,?,?,?)", paras, argTypes);
		
	}
	
	private int addOut(InOut io){
		return manager.addAuto(io);
	}
 
	/*
	public void addInOuts(int size,int action,String personId,int rfidCount,JSONArray cards,JSONArray cardsTime,String proName,int storageId)
	{
		if( action == 1){
			//入库操作
			for(int i = 0;i < size;i++)
			{
				//addInOut(cards.getString(i),cardsTime.getString(i),action,personId,rfidCount,proId,storageId);
			}
		}
		else if( action == 0 ){
			//出库操作
			for(int i = 0;i < size;i++){
				InOut out = new InOut();
			
				InOut in = getInOutByCardnum(cards.getString(i)).get(0);
				out.setAction(0);
				out.setBarCode("");
				out.setCardNum(cards.getString(i));
				out.setTime(cardsTime.getString(i));
				out.setPersonId(personId);
				out.setBindCount(in.getBindCount());
				out.setProId(in.getProId());
				out.setStorageId(storageId);
				
				addOut(out);
			}
		}
		
		return ;
	}
	*/
	
	/**
	 * 
	 * @param action 
	 *        1 查询入库
	 *        0 查询出库
	 *        2/其他  查询所有
	 * @return
	 */
	private List<InOut> queryTodayInOut(int action,int storageId) {
		// TODO Auto-generated method stub
		
		String nowTime = formatter.format(new Date());
		
		Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0); 
        c.set(Calendar.MINUTE, 0); 
        c.set(Calendar.SECOND, 0); 
        Date d = c.getTime();
        String startTime = formatter.format(d);
        
        String sql = "select * from in_out where in_out.time <? and in_out.time >= ? and in_out.storageId =? ";
        if(action == 0 || action == 1)
        	sql = sql +" and action="+action;
        Object[] paras ={nowTime,startTime,storageId};
        int[] types = {Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		//List<InOut> inouts=manager.query(sql, paras, types, RowMapperEnum.INOUT.getMapperName());
        return manager.query(sql, paras, types, RowMapperEnum.INOUT.getMapperName());
		
	}


	public int countInToday(int storageId) {
		// TODO Auto-generated method stub
		String nowTime = formatter.format(new Date());
		
		Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0); 
        c.set(Calendar.MINUTE, 0); 
        c.set(Calendar.SECOND, 0); 
        Date d = c.getTime();
        String startTime = formatter.format(d);
        
        Object[] paras ={nowTime,startTime,storageId};
        int[] types = {Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		return manager.queryInt("SELECT SUM(bindCount) from in_out WHERE action=1 and time <?and time >= ? and storageId=?",paras,types );
	}

	public int countOutToday(int storageId) {
		// TODO Auto-generated method stub
		String nowTime = formatter.format(new Date());
		
		Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0); 
        c.set(Calendar.MINUTE, 0); 
        c.set(Calendar.SECOND, 0); 
        Date d = c.getTime();
        String startTime = formatter.format(d);
        
        Object[] paras ={nowTime,startTime,storageId};
        int[] types = {Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		return manager.queryInt("SELECT SUM(bindCount) from in_out WHERE action=0 and time <?and time >= ? and storageId=?",paras,types );
	}

	/**
	 * 
	 * @param proId
	 * @param personId
	 * @param startTime
	 * @param action
	 *        1 查询入库
	 *        0 查询出库
	 *        2/其他  查询所有
	 * @return
	 */
	public List<InOut> searchTodayInOut(String proName, String personId,String startTime,int action,int storageId) {
		// TODO Auto-generated method stub
		String nowTime = formatter.format(new Date());
		
		//暂时写死
		Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0); 
        c.set(Calendar.MINUTE, 0); 
        c.set(Calendar.SECOND, 0); 
        Date d = c.getTime();
        startTime = formatter.format(d);
        
        String sql = "select * from in_out where time <? and time >= ?  and storageId=? ";
		if(!proName.equals("0")) 
			sql = sql + " and in_out.cardNum in ( select rfid from virtual_production where productionName = '" + proName+"')";
		if(!personId.equals("0"))
			sql = sql + " and in_out.personId='" + personId+"'";
		 if(action ==0 || action == 1)
	        sql = sql+ " and action="+action;
        
    
        Object[] paras ={nowTime,startTime,storageId};
        int[] types = {Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		//List<InOut> inouts =inoutManager.queryWithPro(sql,paras,types);
        		
		return manager.query(sql, paras, types, RowMapperEnum.INOUT.getMapperName());
	}
	
	/**
	 * 
	 * @param action
	 *      1   入库
	 *      0   出库
	 * @return
	 */
	private List<Map<String, Object>> queryTodayAllGroupByTime(int action,int storageId) {
		// TODO Auto-generated method stub
		
		String nowTime = formatter.format(new Date());

		Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0); 
        c.set(Calendar.MINUTE, 0); 
        c.set(Calendar.SECOND, 0); 
        Date d = c.getTime();
        String startTime = formatter.format(d);
        
        String sql = "select time,SUM(bindCount) as count FROM in_out where time<? and time >=? and storageId=? and action =? GROUP BY time";
        Object[] paras ={nowTime,startTime,storageId,action};
        int[] types = {Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER};
		return manager.queryForListMap(sql, paras, types);
	}
	
	/**
	 * 根据条件
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
	private List<Map<String, Object>> queryAllGroupByTimeAndPro(int storageId,String startTime,String endTime,String proName,String personId,int action)
	{
		String sql ="SELECT virtual_production.productionName as name, SUM(virtual_production.productionCount)as count ,  in_out.time as time FROM virtual_production , in_out WHERE virtual_production.rfid = in_out.cardNum and in_out.time <? and in_out.time>? and in_out.storageId=?";
		if(!proName.equals("0")) 
			sql = sql + " and virtual_production.productionName= '" + proName+"')";
		if(!personId.equals("0"))
			sql = sql + " and in_out.personId='" + personId+"'";
		 if(action ==0 || action == 1)
	        sql = sql+ " and in_out.action="+action;
		 
		 sql += " GROUP BY time,name";
		 Object[] paras ={endTime,startTime,storageId};
	     int[] types = {Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
	     return manager.queryForListMap(sql, paras, types);
		 
	}
	
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
	private List<InOut> queryInOut(int action,int storageId,String startTime,
			String endTime, String proName, String personId){
		
		String sql = "select * from in_out where time <? and time >= ?  and storageId=? ";
		if(!proName.equals("0")) 
			sql = sql + " and in_out.cardNum in ( select rfid from virtual_production where productionName = '" + proName+"')";
		if(!personId.equals("0"))
			sql = sql + " and in_out.personId='" + personId+"'";
		 if(action ==0 || action == 1)
	        sql = sql+ " and action="+action;

		Object[] paras ={endTime,startTime,storageId};
        int[] types = {Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		//return inoutManager.queryWithPro(sql,paras,types);
        return manager.query(sql, paras, types, RowMapperEnum.INOUT.getMapperName());
        		
	}
	
	public List<InOut> getInOutByCardnum(String cardNum) {
		// TODO Auto-generated method stub
        
		return manager.query("select * from in_out where cardNum = '"+cardNum+"'",
				null, null, RowMapperEnum.INOUT.getMapperName());
				 
	}
	

	public List<InOut> queryTodayIn(int storageId) {
		// TODO Auto-generated method stub	
		return this.queryTodayInOut(1,storageId);
	}

	public List<InOut> queryTodayOut(int storageId) {
		// TODO Auto-generated method stub
		return this.queryTodayInOut(0,storageId);
	}

	public List<InOut> queryTodayAll(int storageId) {
		// TODO Auto-generated method stub
		return this.queryTodayInOut(2,storageId);
	}

	public List<InOut> searchTodayIn(String proName, String personId,
			String startTime,int storageId) {
		// TODO Auto-generated method stub
		return this.searchTodayInOut(proName, personId, startTime, 1,storageId);
	}

	public List<InOut> searchTodayOut(String proName, String personId,
			String startTime,int storageId) {
		// TODO Auto-generated method stub
		return this.searchTodayInOut(proName, personId, startTime, 0,storageId);
	}

	public List<Map<String, Object>> queryTodayInGroupByTime(int storageId) {
		// TODO Auto-generated method stub
		return this.queryTodayAllGroupByTime(1,storageId);
	}

	public List<Map<String, Object>> queryTodayOutGroupByTime(int storageId) {
		// TODO Auto-generated method stub
		return this.queryTodayAllGroupByTime(0,storageId);
	}

	public List<InOut> queryAll(int storageId, String startTime,
			String endTime, String proName, String personId) {
		// TODO Auto-generated method stub
		return this.queryInOut(2, storageId, startTime, endTime, proName, personId);
	}

	public List<InOut> queryIn(int storageId, String startTime, String endTime,
			String proName, String personId) {
		// TODO Auto-generated method stub
		return this.queryInOut(1, storageId, startTime, endTime, proName, personId);
	}

	public List<InOut> queryOut(int storageId, String startTime,
			String endTime, String proName, String personId) {
		// TODO Auto-generated method stub
		return this.queryInOut(0, storageId, startTime, endTime, proName, personId);
	}

	public List<Map<String, Object>> queryInGroupByTimeAndPro(int storageId,
			String startTime, String endTime, String proName, String personId) {
		// TODO Auto-generated method stub
		return this.queryAllGroupByTimeAndPro(storageId, startTime, endTime, proName, personId, 1);
	}

	public List<Map<String, Object>> queryOutGroupByTimeAndPro(int storageId,
			String startTime, String endTime, String proName, String personId) {
		// TODO Auto-generated method stub
		return this.queryAllGroupByTimeAndPro(storageId, startTime, endTime, proName, personId, 0);
	}

	public List<InOut> queryByCardNum(String cardNum) {
		// TODO Auto-generated method stub
		return manager.query(Zql.selectFrom("in_out")
				.where(Zql.condition("cardNum", "=", cardNum)), RowMapperEnum.INOUT.getMapperName());
	}

	@Override
	public List<InOut> queryByCondition(Conditionor con) {
		// TODO Auto-generated method stub
		return manager.query(Zql.select("*")
								.from("in_out").where(con), 
								RowMapperEnum.INOUTRAW.getMapperName());
	}


}
