package com.demo.dao;

import java.sql.*;
import java.util.ArrayList;

import com.demo.pojo.*;

public class ReviewebookDAOImpl implements ReviewebookDAO {

	private Connection conn = null;
	private PreparedStatement pst = null;

	public ArrayList<Reviewebook> selectreview(Reviewebook pojo) throws SQLException {
		try{
			// 基本的SQL
			String sql = "select "
					+" ebook.EBookID as EBookID "
					+", ebook.Title as Title "
					+", ebook.Author as Author "
					+", ebook.Views as Views "
					+", ebook.Picture as Picture "
					+", ebook.Genre as Genre "
					+", ebook.Theme as Theme "
					+",reviewebook.Comment as Comment"
					+",reviewebook.Rating as Rating"
					+",reviewebook.ReviewDate as ReviewDate"
					+",reviewebook.UserID as UserID"
					+",user.Name as Name"
					+" from ebook inner join reviewebook on ebook.EBookID=reviewebook.EBookID "
					+" inner join user on user.UserID=reviewebook.UserID "
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
			ArrayList<Reviewebook> al = new ArrayList<Reviewebook>();	// 封装数据并返回给Service层
			while(rs.next()){
				// 将Book相关信息保存到po对象的各个属性中
				Ebook po = new Ebook();	//持久层对象，用于调用POJO类的方法
				po.setEBookID(rs.getInt("EBookID"));
				po.setTitle(rs.getString("Title"));
				po.setAuthor(rs.getString("Author"));
				po.setViews(rs.getString("Views"));
				po.setPicture(rs.getString("Picture"));
				po.setGenre(rs.getString("Genre"));
				po.setTheme(rs.getString("Theme"));
				// 将以上全部信息添加到集合
				User p2 = new User();
				p2.setName(rs.getString("Name"));
				// 将Reviewbook相关信息保存到po对象的各个属性中
				Reviewebook p = new Reviewebook();	//持久层对象，用于调用POJO类的方法
				p.setUserID(rs.getInt("UserID"));
				p.setEBookID(rs.getInt("EBookID"));
				p.setComment(rs.getString("Comment"));
				p.setRating(rs.getInt("Rating"));
				p.setReviewDate(rs.getString("ReviewDate"));
				p.setEbook(po);
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
	/**
	 * 无参构造函数
	 */
	public ReviewebookDAOImpl () {
		super();
	}

	/**
	 * 带参构造函数
	 * 参数为conn连接，实例化的时候，就直接完成连接的注入
	 */
	public ReviewebookDAOImpl (Connection conn) {
		super();
		this.conn = conn;
	}

	/**
	 * 查询
	 */
	@Override
	public ArrayList<Reviewebook> select(Reviewebook pojo) throws SQLException {
		try{
			// 基本的SQL
			String sql = "select "
				+" reviewebook.UserID as UserID "
				+", reviewebook.EBookID as EBookID "
				+", reviewebook.Comment as Comment "
				+", reviewebook.Rating as Rating "
				+", reviewebook.ReviewDate as ReviewDate "
				+" from reviewebook "
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

			// System.out.println(sql);
			// 连接数据库，并将sql执行结果转换为java对象
			pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			ArrayList<Reviewebook> al = new ArrayList<Reviewebook>();	// 封装数据并返回给Service层
			while(rs.next()){
				// 将Reviewebook相关信息保存到po对象的各个属性中
				Reviewebook po = new Reviewebook();	//持久层对象，用于调用POJO类的方法
				po.setUserID(rs.getInt("UserID"));
				po.setEBookID(rs.getInt("EBookID"));
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
	public int count(Reviewebook pojo) throws SQLException {
		try {
			String sql = "select count(*) as cnt from reviewebook where 1=1 ";
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
	public int insert(Reviewebook pojo) throws SQLException {
		try {
			String sql = "insert into "
				+ "reviewebook(UserID,EBookID,Comment,Rating,ReviewDate)"
				+ "values(?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, pojo.getUserID());
			pst.setInt(2, pojo.getEBookID());
			pst.setString(3, pojo.getComment());
			pst.setDouble(4, pojo.getRating());
			pst.setString(5, pojo.getReviewDate());
			// System.out.println(sql);
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
	public int update(Reviewebook pojo) throws SQLException {
		try {
			int cnt = 0;
			String sql = "update reviewebook set ";

			if(pojo.getUserID() != 0 ){
				sql += "UserID='" + pojo.getUserID() + "',";
				cnt++;
			}

			if(pojo.getEBookID() != 0){
				sql += "EBookID='" + pojo.getEBookID() + "',";
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
			sql += " where EBookID=?";

			pst = conn.prepareStatement(sql);
			pst.setInt(1, pojo.getEBookID());
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
	public int delete(Reviewebook pojo) throws SQLException {
		try {
			String sql = "delete from reviewebook where EBookID=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, pojo.getEBookID());
			//System.out.println(sql);
			int i = pst.executeUpdate();
			return i;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}

}

