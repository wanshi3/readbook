package com.demo.pojo;

import java.io.Serializable;
import java.util.*;

import com.demo.pojo.Base;

/**
 * (user)
 * 
 * 该POJO是一个JavaBean，所有的属性都是跟数据库一致，包括字段名，数据类型
 * 为每个属性添加一个get()和set()，顺便再加一个toString()，便于程序调试
 * 另外，所有的POJO类都继承Base，就不用再考虑where、limit、order by的问题了
 */ 

public class User extends Base implements Serializable {

	private static final long serialVersionUID = 1L;

	private int UserID;	//
	private String Username;	// 
	private String Password;	//
	private String Name;	// 

	public void setUserID(int UserID){
		this.UserID = UserID;
	}

	public int getUserID(){
		return UserID;
	}

	public void setUsername(String Username){
		this.Username = Username;
	}

	public String getUsername(){
		return Username;
	}

	public void setPassword(String Password){
		this.Password = Password;
	}

	public String getPassword(){
		return Password;
	}

	public void setName(String Name){
		this.Name = Name;
	}

	public String getName(){
		return Name;
	}
	public String toUsernamer(){
		int minLength = 5; // 最小位数
		int maxLength = 6; // 最大位数

		// 创建Random对象
		Random random = new Random();

		// 生成随机位数
		int length = random.nextInt(maxLength - minLength + 1) + minLength;

		// 生成随机数字
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int digit = random.nextInt(10); // 生成0到9之间的随机数
			sb.append(digit);
		}

		String randomNum = sb.toString();
		//System.out.println("随机数字：" + randomNum);
		return randomNum;
	}
	public String toString() {
		return "User["
			+ "UserID = " + UserID
			+ ",Username = " + Username
			+ ",Password = " + Password
			+ ",Name = " + Name
			+"]";
	}
}

