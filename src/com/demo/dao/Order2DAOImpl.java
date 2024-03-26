package com.demo.dao;

import java.sql.*;
import java.util.ArrayList;

import com.demo.dao.Order2DAO;
import com.demo.pojo.Order2;

public class Order2DAOImpl implements Order2DAO {

	private Connection conn = null;
	private PreparedStatement pst = null;

	/**
	 * 无参构造函数
	 */
	public Order2DAOImpl () {
		super();
	}

	/**
	 * 带参构造函数
	 * 参数为conn连接，实例化的时候，就直接完成连接的注入
	 */
	public Order2DAOImpl (Connection conn) {
		super();
		this.conn = conn;
	}

	/**
	 * 查询
	 */
	@Override
	public ArrayList<Order2> select(Order2 pojo) throws SQLException {
		try{
			// 基本的SQL
			String sql = "select "
				+" order2.OrderID as OrderID "
				+", order2.UserID as UserID "
				+", order2.CreatedTime as CreatedTime "
				+", order2.TotalAmout as TotalAmout "
				+" from order2 "
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
			ArrayList<Order2> al = new ArrayList<Order2>();	// 封装数据并返回给Service层
			while(rs.next()){
				// 将Order2相关信息保存到po对象的各个属性中
				Order2 po = new Order2();	//持久层对象，用于调用POJO类的方法
				po.setUserID(rs.getInt("UserID"));
				po.setCreatedTime(rs.getString("CreatedTime"));
				po.setTotalAmout(rs.getInt("TotalAmout"));
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
	public int count(Order2 pojo) throws SQLException {
		try {
			String sql = "select count(*) as cnt from order2 where 1=1 ";
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
	public int insert(Order2 pojo) throws SQLException {
		try {
			String sql = "insert into "
				+ "order2(BookID,UserID,CreatedTime,TotalAmout,OrderQuantity)"
				+ "values(?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setInt(1,pojo.getBookID());
			pst.setInt(2, pojo.getUserID());
			pst.setString(3, pojo.getCreatedTime());
			pst.setInt(4,pojo.getOrderQuantity());
			pst.setDouble(5, pojo.getTotalAmout());

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
	public int update(Order2 pojo) throws SQLException {
		try {
			int cnt = 0;
			String sql = "update order2 set ";



			if(pojo.getUserID() != 0){
				sql += "UserID='" + pojo.getUserID() + "',";
				cnt++;
			}

			if(pojo.getCreatedTime() != null && !pojo.getCreatedTime().equals("")){
				sql += "CreatedTime='" + pojo.getCreatedTime() + "',";
				cnt++;
			}

			if(pojo.getTotalAmout() != 0){
				sql += "TotalAmout='" + pojo.getTotalAmout() + "',";
				cnt++;
			}

			// 去掉最后一个逗号
			sql = sql.substring(0,sql.length()-1);
			sql += " where OrderID=?";

			pst = conn.prepareStatement(sql);
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
	public int delete(Order2 pojo) throws SQLException {
		try {
			String sql = "delete from order2 where OrderID=?";
			pst = conn.prepareStatement(sql);

			//System.out.println(sql);
			int i = pst.executeUpdate();
			return i;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}

}

