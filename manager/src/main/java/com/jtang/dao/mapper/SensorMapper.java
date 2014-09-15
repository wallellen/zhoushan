package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.Sensor;

/**
 * @author zhouxinyu
 *
 */
/*
 * CREATE  TABLE `storagemanager`.`Sensor` (

  `extAddr` VARCHAR(65) NOT NULL ,

  `shortAddr` VARCHAR(10) NULL ,

  `nodeTypes` INT NULL COMMENT '0表示协调器,1表示路由器,2表示终端节点' ,

  `workStatus` INT NULL COMMENT '0表示停止,1表示正常工作,2表示异常工作' ,

  `fatherNode` VARCHAR(10) NULL COMMENT '父节点的shortaddr' ,

  PRIMARY KEY (`extAddr`) ,

  UNIQUE INDEX `extAddr_UNIQUE` (`extAddr` ASC) );
 */
/**
 * 温度传感器
 * @author zxy
 *
 */
public class SensorMapper implements RowMapper<Sensor> {

	public Sensor mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Sensor s = new Sensor();
    	s.setExtAddr(rs.getString("extAddr"));
    	s.setShortAddr(rs.getString("shortAddr"));
    	s.setNodeTypes(rs.getInt("nodeTypes"));
    	s.setWorkStatus(rs.getInt("workStatus"));
    	s.setFatherNode(rs.getString("fatherNode"));
    	s.setPosition(rs.getString("position"));
    	s.setCreator(rs.getString("creator"));
    	s.setCreateTime(rs.getString("createTime"));
    	s.setMender(rs.getString("mender"));
    	s.setMendTime(rs.getString("mendTime"));
    	s.setWorkTime(rs.getInt("workTime"));
    	s.setStorageId(rs.getInt("storageId"));
    	s.setName(rs.getString("name"));
        return s;
    }
}
