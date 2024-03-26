package com.demo.pojo;

import java.io.Serializable;
import java.util.*;

import com.demo.pojo.Base;

/**
 * (reviewebook)
 * 
 * 该POJO是一个JavaBean，所有的属性都是跟数据库一致，包括字段名，数据类型
 * 为每个属性添加一个get()和set()，顺便再加一个toString()，便于程序调试
 * 另外，所有的POJO类都继承Base，就不用再考虑where、limit、order by的问题了
 */ 

public class Reviewebook extends Base implements Serializable {

	private static final long serialVersionUID = 1L;

	private int UserID;	// 用户外键
	private int EBookID;	// 电子书图书外键
	private String Comment;	// 
	private double Rating;	// 
	private String ReviewDate;	// 

	public com.demo.pojo.Ebook getEbook() {
		return Ebook;
	}

	public void setEbook(com.demo.pojo.Ebook ebook) {
		Ebook = ebook;
	}

	public com.demo.pojo.User getUser() {
		return User;
	}

	public void setUser(com.demo.pojo.User user) {
		User = user;
	}

	private Ebook Ebook;
	private User User;
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

	public void setComment(String Comment){
		this.Comment = Comment;
	}

	public String getComment(){
		return Comment;
	}

	public void setRating(double Rating){
		this.Rating = Rating;
	}

	public double getRating(){
		return Rating;
	}

	public void setReviewDate(String ReviewDate){
		this.ReviewDate = ReviewDate;
	}

	public String getReviewDate(){
		return ReviewDate;
	}

	public String toString() {
		return "Reviewebook["
			+ "UserID = " + UserID
			+ ",EBookID = " + EBookID
			+ ",Comment = " + Comment
			+ ",Rating = " + Rating
			+ ",ReviewDate = " + ReviewDate
			+"]";
	}
}

