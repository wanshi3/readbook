package com.demo.dao;

import java.sql.*;
import java.util.ArrayList;
import com.demo.pojo.Book;
import com.demo.pojo.Reviewbook;

public class BookDAOImpl implements BookDAO {

	private Connection conn = null;
	private PreparedStatement pst = null;

	/**
	 * 无参构造函数
	 */
	public BookDAOImpl () {
		super();
	}

	/**
	 * 带参构造函数
	 * 参数为conn连接，实例化的时候，就直接完成连接的注入
	 */
	public BookDAOImpl (Connection conn) {
		super();
		this.conn = conn;
	}

	/**
	 * 查询
	 */
	@Override
	public ArrayList<Book> select(Book pojo) throws SQLException {
		try{
			// 基本的SQL
			String sql = "select "
				+" book.BookID as BookID "
				+", book.Title as Title "
				+", book.Author as Author "
				+", book.Price as Price "
				+", book.PublicationYear as PublicationYear "
				+", book.Theme as Theme "
				+", book.Genre as Genre "
				+", book.InStock as InStock "
				+", book.Picture as Picture "
				+" from book "
				+" where 1=1 ";

			// 拼接查询条件
			String condition = pojo.getCondition();
			if(condition != null && ! condition.equals("")){
				sql += " and " + condition;
			}
			System.out.println(sql);

			// 拼接排序条件
			String orderBy = pojo.getOrderBy();
			if(orderBy != null && ! orderBy.equals("")){
				sql +=  orderBy;
			}

			// 拼接分页条件
			String limit = pojo.getLimit();
			if(limit != null && ! limit.equals("")){
				sql +=  limit;
			}

			 System.out.println(sql);
			// 连接数据库，并将sql执行结果转换为java对象
			pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			ArrayList<Book> al = new ArrayList<Book>();	// 封装数据并返回给Service层
			while(rs.next()){
				// 将Book相关信息保存到po对象的各个属性中
				Book po = new Book();	//持久层对象，用于调用POJO类的方法
				po.setBookID(rs.getInt("BookID"));
				po.setTitle(rs.getString("Title"));
				po.setAuthor(rs.getString("Author"));
				po.setPrice(rs.getInt("Price"));
				po.setPublicationYear(rs.getString("PublicationYear"));
				po.setTheme(rs.getString("Theme"));
				po.setGenre(rs.getString("Genre"));
				po.setInStock(rs.getString("InStock"));
				po.setPicture(rs.getString("Picture"));
				// 将以上全部信息添加到集合
				al.add(po);
			}
			 System.out.println(al.toString());
			return al;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查询
	 */

	/**
	 * 统计记录数
	 */
	@Override
	public int count(Book pojo) throws SQLException {
		try {
			String sql = "select count(*) as cnt from book where 1=1 ";
			String condition = pojo.getCondition();
			if(condition != null && !condition.equals("")){
				sql += " and " + condition;
			}
			 System.out.println(sql);
			pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			rs.next();
			int cnt = rs.getInt("cnt");
			return cnt;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 添加单个记录
	 */
	@Override
	public int insert(Book pojo) throws SQLException {
		try {
			String sql = "insert into "
				+ "book(Title,Author,Price,PublicationYear,Theme,Genre,InStock,Picture)"
				+ "values(?,?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, pojo.getTitle());
			pst.setString(2, pojo.getAuthor());
			pst.setDouble(3, pojo.getPrice());
			pst.setString(4, pojo.getPublicationYear());
			pst.setString(5, pojo.getTheme());
			pst.setString(6, pojo.getGenre());
			pst.setString(7, pojo.getInStock());
			pst.setString(8, pojo.getPicture());
			 System.out.println(sql);
			int i = pst.executeUpdate();
			return i;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 更新单个记录
	 */
	@Override
	public int update(Book pojo) throws SQLException {
		try {
			int cnt = 0;
			String sql = "update book set ";

			if(pojo.getBookID()!=0){
				sql += "BookID='" + pojo.getBookID() + "',";
				cnt++;
			}

			if(pojo.getTitle() != null && !pojo.getTitle().equals("")){
				sql += "Title='" + pojo.getTitle() + "',";
				cnt++;
			}

			if(pojo.getAuthor() != null && !pojo.getAuthor().equals("")){
				sql += "Author='" + pojo.getAuthor() + "',";
				cnt++;
			}
			if(pojo.getPrice()!=0 )
			{
				sql += "Price='" + pojo.getPrice() + "',";
				cnt++;
			}


			if(pojo.getPublicationYear() != null && !pojo.getPublicationYear().equals("")){
				sql += "PublicationYear='" + pojo.getPublicationYear() + "',";
				cnt++;
			}

			if(pojo.getTheme() != null && !pojo.getTheme().equals("")){
				sql += "Theme='" + pojo.getTheme() + "',";
				cnt++;
			}

			if(pojo.getGenre() != null && !pojo.getGenre().equals("")){
				sql += "Genre='" + pojo.getGenre() + "',";
				cnt++;
			}

			if(pojo.getInStock() != null && !pojo.getInStock().equals("")){
				sql += "InStock='" + pojo.getInStock() + "',";
				cnt++;
			}

			if(pojo.getPicture() != null && !pojo.getPicture().equals("")){
				sql += "Picture='" + pojo.getPicture() + "',";
				cnt++;
			}

			// 去掉最后一个逗号
			sql = sql.substring(0,sql.length()-1);
			sql += " where BookID=?";

			pst = conn.prepareStatement(sql);
			pst.setInt(1, pojo.getBookID());
			System.out.println(sql);
			int i = pst.executeUpdate();
			return i;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 删除单个记录
	 */
	@Override
	public int delete(Book pojo) throws SQLException {
		try {
			String sql = "delete from book where BookID= ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, pojo.getBookID());
//			System.out.println(sql);
			int i = pst.executeUpdate();
			return i;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}

}

