package com.demo.pojo;

import java.io.Serializable;
import java.util.*;

import com.demo.pojo.Base;

/**
 * (reviewbook)
 * 
 * 该POJO是一个JavaBean，所有的属性都是跟数据库一致，包括字段名，数据类型
 * 为每个属性添加一个get()和set()，顺便再加一个toString()，便于程序调试
 * 另外，所有的POJO类都继承Base，就不用再考虑where、limit、order by的问题了
 */ 

public class Reviewbook extends Base implements Serializable {

	private static final long serialVersionUID = 1L;

	private int UserID;	// 用户外键
	private int BookID;	// 图书外键
	private String Comment;	// 
	private double Rating;	// 
	private String ReviewDate;	//
	private Book book;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private User user;

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public void setUserID(int UserID){
		this.UserID = UserID;
	}

	public int getUserID(){
		return UserID;
	}

	public void setBookID(int BookID){
		this.BookID = BookID;
	}

	public int getBookID(){
		return BookID;
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
		return "Reviewbook["
			+ "UserID = " + UserID
			+ ",BookID = " + BookID
			+ ",Comment = " + Comment
			+ ",Rating = " + Rating
			+ ",ReviewDate = " + ReviewDate
			+"]";
	}
}

