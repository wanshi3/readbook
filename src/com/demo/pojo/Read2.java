package com.demo.pojo;

import java.io.Serializable;
import java.util.*;

import com.demo.pojo.Base;

/**
 * (read2)
 * 
 * 该POJO是一个JavaBean，所有的属性都是跟数据库一致，包括字段名，数据类型
 * 为每个属性添加一个get()和set()，顺便再加一个toString()，便于程序调试
 * 另外，所有的POJO类都继承Base，就不用再考虑where、limit、order by的问题了
 */ 

public class Read2 extends Base implements Serializable {

	private static final long serialVersionUID = 1L;

	private int UserID;	// 用户id
	private User user;	// 外键
	private int EBookID;	// 电子图书id
	private Ebook ebook;	// 外键
	private String ReadTime;	// 
	private String ReadProgress;	// 

	public void setUserID(int UserID){
		this.UserID = UserID;
	}

	public int getUserID(){
		return UserID;
	}

	public void setEBookID(int EBookID){
		this.EBookID = EBookID;
	}

	public int getEBookID(){
		return EBookID;
	}

	public void setReadTime(String ReadTime){
		this.ReadTime = ReadTime;
	}

	public String getReadTime(){
		return ReadTime;
	}

	public void setReadProgress(String ReadProgress){
		this.ReadProgress = ReadProgress;
	}

	public String getReadProgress(){
		return ReadProgress;
	}

	public String toString() {
		return "Read2["
			+ "UserID = " + UserID
			+ ",EBookID = " + EBookID
			+ ",ReadTime = " + ReadTime
			+ ",ReadProgress = " + ReadProgress
			+"]";
	}
}

