package com.demo.pojo;

import java.io.Serializable;
import java.util.*;

import com.demo.pojo.Base;

/**
 * (ebook)
 * 
 * 该POJO是一个JavaBean，所有的属性都是跟数据库一致，包括字段名，数据类型
 * 为每个属性添加一个get()和set()，顺便再加一个toString()，便于程序调试
 * 另外，所有的POJO类都继承Base，就不用再考虑where、limit、order by的问题了
 */ 

public class Ebook extends Base implements Serializable {

	private static final long serialVersionUID = 1L;

	private int EBookID;	//
	private String Title;	// 
	private String Author;	// 
	private String Views;	// 
	private String Picture;	// 
	private String Genre;	// 
	private String Theme;	// 

	public void setEBookID(int EBookID){
		this.EBookID = EBookID;
	}

	public int getEBookID(){
		return EBookID;
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

	public void setViews(String Views){
		this.Views = Views;
	}

	public String getViews(){
		return Views;
	}

	public void setPicture(String Picture){
		this.Picture = Picture;
	}

	public String getPicture(){
		return Picture;
	}

	public void setGenre(String Genre){
		this.Genre = Genre;
	}

	public String getGenre(){
		return Genre;
	}

	public void setTheme(String Theme){
		this.Theme = Theme;
	}

	public String getTheme(){
		return Theme;
	}

	public String toString() {
		return "Ebook["
			+ "EBookID = " + EBookID
			+ ",Title = " + Title
			+ ",Author = " + Author
			+ ",Views = " + Views
			+ ",Picture = " + Picture
			+ ",Genre = " + Genre
			+ ",Theme = " + Theme
			+"]";
	}
}

