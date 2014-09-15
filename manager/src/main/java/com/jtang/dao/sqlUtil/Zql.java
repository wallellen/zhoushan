package com.jtang.dao.sqlUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 该类提供一系列工厂方法，提供sql语句的生成
 * 欢迎反馈bug
 * @author Administartor
 * @ThreadUnsafe
 */
public class Zql {
	/**
	 * 生产Conditionor的工厂方法,
	 * 当使用sql in的时候,content的参数请传入List
	 * 当使用sql between and的时候,请使用Zql.betweenAnd方法
	 * @param colmunName
	 * @param operator
	 * @param content
	 * @return
	 */
	public static Conditionor condition(String colmunName,String operator,Object content){
		if(operator.toLowerCase().trim().equals("in")){
			if(content instanceof List<?> || content instanceof Selector){
				return new InConditionor(colmunName,content);
			}else{
				throw new RuntimeException("You should give a collection to new a InConditionor");
			}
		}
		return new SimpleConditionor(colmunName,operator,content);
	}
	
	/**
	 * 生成between and的Conditionor
	 * @param colmunName
	 * @param first 位于between后的对象
	 * @param second 位于and后的对象
	 * @return
	 */
	public static Conditionor betweenAnd(String colmunName,Object first,Object second){
		return new BetweenConditionor(colmunName, first, second);
	}
	
	/**
	 * 设置查询的列,并返回一个Selector,如需查询所有,传入"*"即可
	 * @param colmuns
	 * @return
	 */
	public static Selector select(String... colmuns){
		Selector selector = new SimpleSelector();
		return selector.select(colmuns);
	}
	
	public static Selector selectFrom(String... tables){
		Selector selector = new SimpleSelector();
		selector.select("*");
		return selector.from(tables);
	}
	
	public static Updator update(String table){
		Updator updator = new SimpleUpdator();
		return updator.update(table);
	}
	
	public static Deletor deleteFrom(String table){
		Deletor deletor = new SimpleDeletor();
		return deletor.deleteFrom(table);
	}
	
	public static Insertor insertInto(String table){
		Insertor insertor = new SimpleInsertor();
		return insertor.insertInto(table);
	}
	
	
	/**
	 * 使用方法如下
	 * @param args
	 */
	public static void main(String[] args){
		Conditionor con = 
				Zql.condition("id", "=", 123)
				.and(Zql.condition("name", ">", "zxy")
						.or(Zql.condition("loc", "<", "zhejiang"))
						)
				;
		Selector se = Zql.select("*")
				.from("user")
				.join("city", "user.id", "=", "city.id")
				.orderby("id desc")
				.groupby("dd","fdg")
				.where(con);
		System.out.println(se);
		
		System.out.println(Arrays.toString(se.getParams()));
		System.out.println(Arrays.toString(se.getTypes()));
		
		System.out.println("-------update----------");
		Updator up = Zql.update("Student")
				.set("sex", "=", "male")
				.set("height", "=", 75)
				.where(con);
		System.out.println(up.toString());
		System.out.println(Arrays.toString(up.getParams()));
		System.out.println(Arrays.toString(up.getTypes()));
		
		System.out.println("---------delete---------------");
		Deletor de = Zql.deleteFrom("Student").where(con);
		System.out.println(de.toString());
		System.out.println(Arrays.toString(de.getParams()));
		System.out.println(Arrays.toString(de.getTypes()));
		
		System.out.println("---------insert-------------");
		
		Insertor in = Zql.insertInto("Student")
				.keyValue("name", "zxy")
				.keyValue("age", 10)
				.keyValue("sex", "male")
				.keyValue("score", 88.5);
		
		System.out.println(in);
		System.out.println(Arrays.toString(in.getParams()));
		System.out.println(Arrays.toString(in.getTypes()));
		
		System.out.println("----select from * in (?,?,?)------------------");
		ArrayList<Integer> num = new ArrayList<Integer>();
		num.add(4);
		num.add(5);
		Conditionor con1 = Zql.condition("id", "in", num)
						.or(Zql.condition("id2", "in", num))
						.and(Zql.betweenAnd("score", "zxy", "ymg"))
							;
		System.out.println(con1.toString());
		System.out.println(Arrays.toString(con1.getParams()));
		System.out.println(Arrays.toString(con1.getTypes()));
		
		System.out.println("------select from select---------------------");
		Selector sel1 = Zql.select("*").from(se, "B").from("table A")
				.where(con1);
		System.out.println(sel1);
		System.out.println(Arrays.toString(sel1.getParams()));
		System.out.println(Arrays.toString(sel1.getTypes()));
		
		System.out.println("--where id = selector---------------------");
		
		Selector sel2 = Zql.select("*").from("user")
				.where(Zql.condition("id", "=", sel1
						).and(Zql.condition("real", "in", Arrays.asList(1,2))
								));
		System.out.println(sel2.toString());
		System.out.println(Arrays.toString(sel2.getParams()));
		System.out.println(Arrays.toString(sel2.getTypes()));
		
		System.out.println("-----in selector-----------");
		
		Selector insel = Zql.select("*").from("user").where(
				Zql.condition("id", "in", 
						se));
		System.out.println(insel.toString());
		System.out.println(Arrays.toString(insel.getParams()));
		System.out.println(Arrays.toString(insel.getTypes()));
		
		System.out.println("--------------------------");
		System.out.println(Zql.update("user").set("id", "=", 1)
				.set("scroe", "--", 10).where(con1));
		
		System.out.println("------------------------------");
		Conditionor c = Zql.condition("name", "=", "zxy").and(Zql.condition("sex", "in", Arrays.asList(1,2,3,4))).brackets();
		Conditionor newCon = Zql.condition("id", "=", 123)
				.and(Zql.condition("name", "=", "zxy")
						.or(Zql.condition("loc", "=", "df")).and(c))
				.and(Zql.condition("sex", "=", "male")
						.or(Zql.betweenAnd("score", 1, 100)).brackets()
						.and(Zql.condition("fdf", "=", 23)).brackets())
				;
		Conditionor newCon1 = Zql.condition("id", "=", 123);
		Conditionor newCon2	 =Zql.condition("name", "=", "zxy").and(Zql.condition("test", "=", 123));
		
		System.out.println(newCon1.and(newCon2.brackets()));
		
		System.out.println("-----------------------");
		System.out.println(Zql.select("*").from("user A").from(se, "B").from("City C").limit(10));
	}
}
