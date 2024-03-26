package com.demo.dao;

import java.sql.*;
import java.util.ArrayList;
import com.demo.pojo.Ebook;

public class EbookDAOImpl implements EbookDAO {

	private Connection conn = null;
	private PreparedStatement pst = null;

	/**
	 * 无参构造函数
	 */
	public EbookDAOImpl () {
		super();
	}

	/**
	 * 带参构造函数
	 * 参数为conn连接，实例化的时候，就直接完成连接的注入
	 */
	public EbookDAOImpl (Connection conn) {
		super();
		this.conn = conn;
	}

	/**
	 * 查询
	 */
	@Override
	public ArrayList<Ebook> select(Ebook pojo) throws SQLException {
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
				+" from ebook "
				+" where 1=1 ";

			// 拼接查询条件
			String condition = pojo.getCondition();
			if(condition != null && ! condition.equals("")){
				sql += " and " + condition;
			}

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
			ArrayList<Ebook> al = new ArrayList<Ebook>();	// 封装数据并返回给Service层
			while(rs.next()){
				// 将Ebook相关信息保存到po对象的各个属性中
				Ebook po = new Ebook();	//持久层对象，用于调用POJO类的方法
				po.setEBookID(rs.getInt("EBookID"));
				po.setTitle(rs.getString("Title"));
				po.setAuthor(rs.getString("Author"));
				po.setViews(rs.getString("Views"));
				po.setPicture(rs.getString("Picture"));
				po.setGenre(rs.getString("Genre"));
				po.setTheme(rs.getString("Theme"));
				// 由于存在外键(EBookID)，这里需要将ebook相关信息保存到Ebook对象的相关属性中
				Ebook ebook = new Ebook();
				al.add(po);
			}
			// System.out.println(al.toString());
			return al;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<Ebook> select2(Ebook pojo) throws SQLException {
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
					+" from ebook "
					+" JOIN (SELECT EbookID, AVG(Rating) "
					+ " AS AvgRating FROM Reviewebook GROUP BY EbookID ) "
					+" AS AvgRatings ON EBooK.EBooKID = AvgRatings.EbookID "
					+" ORDER BY AvgRatings.AvgRating DESC LIMIT 9 ";

			// 拼接查询条件
			String condition = pojo.getCondition();
			if(condition != null && ! condition.equals("")){
				sql += " and " + condition;
			}

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
			ArrayList<Ebook> al = new ArrayList<Ebook>();	// 封装数据并返回给Service层
			while(rs.next()){
				// 将Ebook相关信息保存到po对象的各个属性中
				Ebook po = new Ebook();	//持久层对象，用于调用POJO类的方法
				po.setEBookID(rs.getInt("EBookID"));
				po.setTitle(rs.getString("Title"));
				po.setAuthor(rs.getString("Author"));
				po.setViews(rs.getString("Views"));
				po.setPicture(rs.getString("Picture"));
				po.setGenre(rs.getString("Genre"));
				po.setTheme(rs.getString("Theme"));
				// 由于存在外键(EBookID)，这里需要将ebook相关信息保存到Ebook对象的相关属性中
				Ebook ebook = new Ebook();
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
	public int count(Ebook pojo) throws SQLException {
		try {
			String sql = "select count(*) as cnt from ebook where 1=1 ";
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
	public int insert(Ebook pojo) throws SQLException {
		try {
			String sql = "insert into "
				+ "ebook(Title,Author,Views,Picture,Genre,Theme)"
				+ "values(?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, pojo.getTitle());
			pst.setString(2, pojo.getAuthor());
			pst.setString(3, pojo.getViews());
			pst.setString(4, pojo.getPicture());
			pst.setString(5, pojo.getGenre());
			pst.setString(6, pojo.getTheme());
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
	public int update(Ebook pojo) throws SQLException {
		try {
			int cnt = 0;
			String sql = "update ebook set ";

			if(pojo.getEBookID() != -1){
				sql += "EBookID='" + pojo.getEBookID() + "',";
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

			if(pojo.getViews() != null && !pojo.getViews().equals("")){
				sql += "Views='" + pojo.getViews() + "',";
				cnt++;
			}

			if(pojo.getPicture() != null && !pojo.getPicture().equals("")){
				sql += "Picture='" + pojo.getPicture() + "',";
				cnt++;
			}

			if(pojo.getGenre() != null && !pojo.getGenre().equals("")){
				sql += "Genre='" + pojo.getGenre() + "',";
				cnt++;
			}

			if(pojo.getTheme() != null && !pojo.getTheme().equals("")){
				sql += "Theme='" + pojo.getTheme() + "',";
				cnt++;
			}

			// 去掉最后一个逗号
			sql = sql.substring(0,sql.length()-1);
			sql += " where EBookID=?";

			pst = conn.prepareStatement(sql);
			pst.setInt(1, pojo.getEBookID());
//			System.out.println(sql);
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
	public int delete(Ebook pojo) throws SQLException {
		try {
			String sql = "delete from ebook where EBookID=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, pojo.getEBookID());
			System.out.println(sql);
			int i = pst.executeUpdate();
			return i;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}

}

