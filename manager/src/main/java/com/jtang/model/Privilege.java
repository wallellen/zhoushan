package com.jtang.model;

import java.sql.Types;

import com.jtang.annotation.TableColumn;

public class Privilege {
	
	@TableColumn(columnName = "privilege_name",
			type = Types.VARCHAR)
	private String name;
	@TableColumn(columnName = "level",
			type = Types.INTEGER)
	private int level;
	@TableColumn(columnName = "comment",
			type = Types.VARCHAR)
	private String comment;
	@TableColumn(columnName = "parent",
			type = Types.VARCHAR)
	private String parent;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}

	
}
