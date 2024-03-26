package com.demo.dao;

import java.sql.*;
import java.util.ArrayList;
import com.demo.pojo.Read2;

public class Read2DAOImpl implements Read2DAO {

	private Connection conn = null;
	private PreparedStatement pst = null;

	/**
	 * 无参构造函数
	 */
	public Read2DAOImpl () {
		super();
	}

	/**
	 * 带参构造函数
	 * 参数为conn连接，实例化的时候，就直接完成连接的注入
	 */
	public Read2DAOImpl (Connection conn) {
		super();
		this.conn = conn;
	}

	/**
	 * 查询
	 */
	@Override
	public ArrayList<Read2> select(Read2 pojo) throws SQLException {
		try{
			// 基本的SQL
			String sql = "select "
				+" read2.UserID as UserID "
				+", read2.EBookID as EBookID "
				+", read2.ReadTime as ReadTime "
				+", read2.ReadProgress as ReadProgress "
				+" from read2 "
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
			ArrayList<Read2> al = new ArrayList<Read2>();	// 封装数据并返回给Service层
			while(rs.next()){
				// 将Read2相关信息保存到po对象的各个属性中
				Read2 po = new Read2();	//持久层对象，用于调用POJO类的方法
				po.setUserID(rs.getInt("UserID"));
				po.setEBookID(rs.getInt("EBookID"));
				po.setReadTime(rs.getString("ReadTime"));
				po.setReadProgress(rs.getString("ReadProgress"));
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
	public int count(Read2 pojo) throws SQLException {
		try {
			String sql = "select count(*) as cnt from read2 where 1=1 ";
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
	public int insert(Read2 pojo) throws SQLException {
		try {
			String sql = "insert into "
				+ "read2(UserID,EBookID,ReadTime,ReadProgress)"
				+ "values(?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, pojo.getUserID());
			pst.setInt(2, pojo.getEBookID());
			pst.setString(3, pojo.getReadTime());
			pst.setString(4, pojo.getReadProgress());
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
	public int update(Read2 pojo) throws SQLException {
		try {
			int cnt = 0;
			String sql = "update read2 set ";

			if(pojo.getUserID() != 0){
				sql += "UserID='" + pojo.getUserID() + "',";
				cnt++;
			}

			if(pojo.getEBookID() != 0){
				sql += "EBookID='" + pojo.getEBookID() + "',";
				cnt++;
			}

			if(pojo.getReadTime() != null && !pojo.getReadTime().equals("")){
				sql += "ReadTime='" + pojo.getReadTime() + "',";
				cnt++;
			}

			if(pojo.getReadProgress() != null && !pojo.getReadProgress().equals("")){
				sql += "ReadProgress='" + pojo.getReadProgress() + "',";
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
	@Override
	public int delete(Read2 pojo) throws SQLException {
		try {
			String sql = "delete from read2 where EBookID=?";
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

