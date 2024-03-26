package com.demo.dao;

import java.sql.*;
import java.util.ArrayList;
import com.demo.pojo.User;

public class UserDAOImpl implements UserDAO {

	private Connection conn = null;
	private PreparedStatement pst = null;

	/**
	 * 无参构造函数
	 */
	public UserDAOImpl () {
		super();
	}

	/**
	 * 带参构造函数
	 * 参数为conn连接，实例化的时候，就直接完成连接的注入
	 */
	public UserDAOImpl (Connection conn) {
		super();
		this.conn = conn;
	}

	/**
	 * 查询
	 */
	@Override
	public ArrayList<User> select(User pojo) throws SQLException {
		try{
			// 基本的SQL
			String sql = "select "
				+" user.UserID as UserID "
				+", user.Username as Username "
				+", user.Password as Password "
				+", user.Name as Name "
				+" from user "
				+" where 1=1 ";

			// 拼接查询条件
			String condition = pojo.getCondition();
			if(condition != null && ! condition.equals("")){
				sql += " and " + condition;
			}

			// 拼接排序条件
			String orderBy = pojo.getOrderBy();
			if(orderBy != null && ! orderBy.equals("")){
				sql += orderBy;
			}

			// 拼接分页条件
			String limit = pojo.getLimit();
			if(limit != null && ! limit.equals("")){
				sql += limit;
			}

			 System.out.println(sql);
			// 连接数据库，并将sql执行结果转换为java对象
			pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			ArrayList<User> al = new ArrayList<User>();	// 封装数据并返回给Service层
			while(rs.next()){
				// 将User相关信息保存到po对象的各个属性中
				User po = new User();	//持久层对象，用于调用POJO类的方法
				po.setUserID(rs.getInt("UserID"));
				po.setUsername(rs.getString("Username"));
				po.setPassword(rs.getString("Password"));
				po.setName(rs.getString("Name"));
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
//	登入查询
	@Override
	public ArrayList<User> loginCheck(User pojo) throws SQLException {
		try{
			// 基本的SQL
			String sql = "select "
					+" user.UserID as UserID "
					+", user.Username as Username "
					+", user.Password as Password "
					+", user.Name as Name "
					+" from user "
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
			ArrayList<User> al = new ArrayList<User>();	// 封装数据并返回给Service层
			while(rs.next()){
				// 将User相关信息保存到po对象的各个属性中
				User po = new User();	//持久层对象，用于调用POJO类的方法
				po.setUserID(rs.getInt("UserID"));
				po.setUsername(rs.getString("Username"));
				po.setPassword(rs.getString("Password"));
				po.setName(rs.getString("Name"));
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
	public int count(User pojo) throws SQLException {
		try {
			String sql = "select count(*) as cnt from user where 1=1 ";
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
	public int insert(User pojo) throws SQLException {
		try {
			String sql = "insert into "
				+ "user(Username,Password,Name)"
				+ "values(?,?,?)";
			pst = conn.prepareStatement(sql);
//			System.out.println(pojo.getName());
			pst.setString(1, pojo.getUsername());
			pst.setString(2, pojo.getPassword());
			pst.setString(3, pojo.getName());
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
	public int update(User pojo) throws SQLException {
		try {
			int cnt = 0;
			String sql = "update user set ";

			if(pojo.getUsername() != null && !pojo.getUsername().equals("")){
				sql += "Username='" + pojo.getUsername() + "',";
				cnt++;
			}

			if(pojo.getPassword() != null && !pojo.getPassword().equals("")){
				sql += "Password='" + pojo.getPassword() + "',";
				cnt++;
			}


			if(pojo.getName() != null && !pojo.getName().equals("")){
				sql += "Name='" + pojo.getName() + "',";
				cnt++;
			}

			// 去掉最后一个逗号
			sql = sql.substring(0,sql.length()-1);
			sql += " where UserID=?";

			pst = conn.prepareStatement(sql);
			pst.setInt(1, pojo.getUserID());
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
	public int delete(User pojo) throws SQLException {
		try {
			String sql = "delete from user where UserID=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, pojo.getUserID());
//			System.out.println(sql);
			int i = pst.executeUpdate();
			return i;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}

}

