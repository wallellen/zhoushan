package com.jtang.dao.sqlUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 暂未实现between里嵌套select语句,在between里面放置select语句显得比较奇怪,虽然可行.
 * 如需实现,改写SimpleConditionor的getParams方法,或者覆盖它,并覆盖toString和toRString即可.
 * @author Administartor
 *
 */
public class BetweenConditionor extends SimpleConditionor {

	public BetweenConditionor(String colmunName, String operator, Object content) {
		super(colmunName, operator, content);
		// TODO Auto-generated constructor stub
	}

	public BetweenConditionor(String colmunName, Object first, Object second) {
		// TODO Auto-generated constructor stub
		this(colmunName," between ? and ",new ArrayList<Object>(Arrays.asList(first,second)));
	}
		
}
