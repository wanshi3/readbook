package com.demo.dao;

import java.sql.*;
import java.util.ArrayList;

import com.demo.pojo.Book;
import com.demo.pojo.Reviewbook;
import com.demo.pojo.User;

public class ReviewbookDAOImpl implements ReviewbookDAO {

	private Connection conn = null;
	private PreparedStatement pst = null;

	/**
	 * 无参构造函数
	 */
	public ReviewbookDAOImpl () {
		super();
	}

	/**
	 * 带参构造函数
	 * 参数为conn连接，实例化的时候，就直接完成连接的注入
	 */
	public ReviewbookDAOImpl (Connection conn) {
		super();
		this.conn = conn;
	}
	@Override
	public ArrayList<Reviewbook> selectreview(Reviewbook pojo) throws SQLException {
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
					+",reviewbook.Comment as Comment"
					+",reviewbook.Rating as Rating"
					+",reviewbook.ReviewDate as ReviewDate"
					+",reviewbook.UserID as UserID"
					+",user.Name as Name"
					+" from book inner join reviewbook on book.BookID=reviewbook.BookID "
					+" inner join user on user.UserID=reviewbook.UserID "
					+" where 1=1 ";

			// 拼接查询条件
			String condition = pojo.getCondition();
			if(condition != null && ! condition.equals("")){
				sql += " and " + condition;
			}


			System.out.println(sql);
			// 连接数据库，并将sql执行结果转换为java对象
			pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			ArrayList<Reviewbook> al = new ArrayList<Reviewbook>();	// 封装数据并返回给Service层
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
				User p2 = new User();
				p2.setName(rs.getString("Name"));
				// 将Reviewbook相关信息保存到po对象的各个属性中
				Reviewbook p = new Reviewbook();	//持久层对象，用于调用POJO类的方法
				p.setUserID(rs.getInt("UserID"));
				p.setBookID(rs.getInt("BookID"));
				p.setComment(rs.getString("Comment"));
				p.setRating(rs.getInt("Rating"));
				p.setReviewDate(rs.getString("ReviewDate"));
				p.setBook(po);
				p.setUser(p2);
				al.add(p);
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
	@Override
	public ArrayList<Reviewbook> select(Reviewbook pojo) throws SQLException {
		try{
			// 基本的SQL
			String sql = "select "
				+" reviewbook.UserID as UserID "
				+", reviewbook.BookID as BookID "
				+", reviewbook.Comment as Comment "
				+", reviewbook.Rating as Rating "
				+", reviewbook.ReviewDate as ReviewDate "
				+" from reviewbook "
				+" where 1=1 ";

			// 拼接查询条件
			String condition = pojo.getCondition();
			if(condition != null && ! condition.equals("")){
				sql += " and " + condition;
			}

			// 拼接排序条件
			String orderBy = pojo.getOrderBy();
			if(orderBy != null && ! orderBy.equals("")){
				sql += " and " + orderBy;
			}

			// 拼接分页条件
			String limit = pojo.getLimit();
			if(limit != null && ! limit.equals("")){
				sql += " and " + limit;
			}

			 System.out.println(sql);
			// 连接数据库，并将sql执行结果转换为java对象
			pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			ArrayList<Reviewbook> al = new ArrayList<Reviewbook>();	// 封装数据并返回给Service层
			while(rs.next()){
				// 将Reviewbook相关信息保存到po对象的各个属性中
				Reviewbook po = new Reviewbook();	//持久层对象，用于调用POJO类的方法
				po.setUserID(rs.getInt("UserID"));
				po.setBookID(rs.getInt("BookID"));
				po.setComment(rs.getString("Comment"));
				po.setRating(rs.getInt("Rating"));
				po.setReviewDate(rs.getString("ReviewDate"));
				// 将以上全部信息添加到集合
				al.add(po);
			}
			// System.out.println(al.toString());
			return al;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 统计记录数
	 */
	@Override
	public int count(Reviewbook pojo) throws SQLException {
		try {
			String sql = "select count(*) as cnt from reviewbook where 1=1 ";
			String condition = pojo.getCondition();
			if(condition != null && !condition.equals("")){
				sql += " and " + condition;
			}
			// System.out.println(sql);
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
	public int insert(Reviewbook pojo) throws SQLException {
		try {
			String sql = "insert into "
				+ "reviewbook(UserID,BookID,Comment,Rating,ReviewDate)"
				+ "values(?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, pojo.getUserID());
			pst.setInt(2, pojo.getBookID());
			pst.setString(3, pojo.getComment());
			pst.setDouble(4, pojo.getRating());
			pst.setString(5, pojo.getReviewDate());
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
	public int update(Reviewbook pojo) throws SQLException {
		try {
			int cnt = 0;
			String sql = "update reviewbook set ";

			if(pojo.getUserID() != 0){
				sql += "UserID='" + pojo.getUserID() + "',";
				cnt++;
			}

			if(pojo.getBookID() != 0){
				sql += "BookID='" + pojo.getBookID() + "',";
				cnt++;
			}

			if(pojo.getComment() != null && !pojo.getComment().equals("")){
				sql += "Comment='" + pojo.getComment() + "',";
				cnt++;
			}

			if(pojo.getRating() != 0){
				sql += "Rating='" + pojo.getRating() + "',";
				cnt++;
			}

			if(pojo.getReviewDate() != null && !pojo.getReviewDate().equals("")){
				sql += "ReviewDate='" + pojo.getReviewDate() + "',";
				cnt++;
			}

			// 去掉最后一个逗号
			sql = sql.substring(0,sql.length()-1);
			sql += " where BookID=?";

			pst = conn.prepareStatement(sql);
			pst.setInt(1, pojo.getBookID());
			//System.out.println(sql);
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
	public int delete(Reviewbook pojo) throws SQLException {
		try {
			String sql = "delete from reviewbook where BookID=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, pojo.getBookID());
			//System.out.println(sql);
			int i = pst.executeUpdate();
			return i;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}

}

