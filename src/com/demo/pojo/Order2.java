package com.demo.pojo;

import java.io.Serializable;
import java.util.*;

import com.demo.pojo.Base;

/**
 * (order2)
 * 
 * 该POJO是一个JavaBean，所有的属性都是跟数据库一致，包括字段名，数据类型
 * 为每个属性添加一个get()和set()，顺便再加一个toString()，便于程序调试
 * 另外，所有的POJO类都继承Base，就不用再考虑where、limit、order by的问题了
 */ 

public class Order2 extends Base implements Serializable {

	private static final long serialVersionUID = 1L;


	private int UserID;	// user外键

	public int getBookID() {
		return BookID;
	}

	public void setBookID(int bookID) {
		BookID = bookID;
	}

	private int BookID;
	private int OrderQuantity;	//
	private String CreatedTime;	//
	private double TotalAmout;	//

	public int getOrderQuantity() {
		return OrderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		OrderQuantity = orderQuantity;
	}


	public void setUserID(int UserID){
		this.UserID = UserID;
	}

	public int getUserID(){
		return UserID;
	}

	public void setCreatedTime(String CreatedTime){
		this.CreatedTime = CreatedTime;
	}

	public String getCreatedTime(){
		return CreatedTime;
	}

	public void setTotalAmout(double TotalAmout){
		this.TotalAmout = TotalAmout;
	}

	public double getTotalAmout(){
		return TotalAmout;
	}

	public String toString() {
		return "Order2["
			+ ",UserID = " + UserID
			+ ",CreatedTime = " + CreatedTime
			+ ",TotalAmout = " + TotalAmout
			+"]";
	}
}

