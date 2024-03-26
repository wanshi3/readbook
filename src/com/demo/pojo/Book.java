package com.demo.pojo;

import java.io.Serializable;
import java.util.*;

import com.demo.pojo.Base;


/**
 * (book)
 * 
 * 该POJO是一个JavaBean，所有的属性都是跟数据库一致，包括字段名，数据类型
 * 为每个属性添加一个get()和set()，顺便再加一个toString()，便于程序调试
 * 另外，所有的POJO类都继承Base，就不用再考虑where、limit、order by的问题了
 */ 

public class Book extends Base implements Serializable {

	private static final long serialVersionUID = 1L;

	private int BookID;	//
	private String Title;	// 
	private String Author;	// 
	private double Price;	// 
	private String PublicationYear;	// 
	private String Theme;	// 
	private String Genre;	// 
	private String InStock;	// 



	private String Picture;	//

	public void setBookID(int BookID){
		this.BookID = BookID;
	}

	public int getBookID(){
		return BookID;
	}

	public void setTitle(String Title){
		this.Title = Title;
	}

	public String getTitle(){
		return Title;
	}

	public void setAuthor(String Author){
		this.Author = Author;
	}

	public String getAuthor(){
		return Author;
	}

	public void setPrice(double Price){
		this.Price = Price;
	}

	public double getPrice(){
		return Price;
	}

	public void setPublicationYear(String PublicationYear){
		this.PublicationYear = PublicationYear;
	}

	public String getPublicationYear(){
		return PublicationYear;
	}

	public void setTheme(String Theme){
		this.Theme = Theme;
	}

	public String getTheme(){
		return Theme;
	}

	public void setGenre(String Genre){
		this.Genre = Genre;
	}

	public String getGenre(){
		return Genre;
	}

	public void setInStock(String InStock){
		this.InStock = InStock;
	}

	public String getInStock(){
		return InStock;
	}

	public void setPicture(String Picture){
		this.Picture = Picture;
	}

	public String getPicture(){
		return Picture;
	}

	public String toString() {
		return "Book["
			+ "BookID = " + BookID
			+ ",Title = " + Title
			+ ",Author = " + Author
			+ ",Price = " + Price
			+ ",PublicationYear = " + PublicationYear
			+ ",Theme = " + Theme
			+ ",Genre = " + Genre
			+ ",InStock = " + InStock
			+ ",Picture = " + Picture
			+"]";
	}
}

