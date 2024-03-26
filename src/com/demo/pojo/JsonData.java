package com.demo.pojo;

import java.io.Serializable;
import java.util.*;

/**
 * 目前，几乎所有成熟的前端框架，其数据表格插件都自带分页功能
 * 分页的功能，前端框架会自动完成
 * 一般情况下，前端都需要设置pageSize
 * 所以，要实现分页，后台只需要传输一个total给前端就可以了
 * 而我们在DAO中的select，仅能返回一个List结果
 * 这时候，ServiceDAO就需要把total连同List封装成类似“total=50,rows=[{},{}...{}]”格式了
 * 先记住这个Bean类吧，以后去Service层看它的表现^-^
 */

public class JsonData implements Serializable {

  private static final long serialVersionUID = 1L;

  // 无参构造函数，
  public JsonData() {
    super();
  }
  
  // 带2个参数的构造函数，一般用于增删改或操作失败时的数据返回
  public JsonData(boolean success,String msg){
    super();
    this.success = success;
    this.msg = msg;
  }

  // 带4个参数的构造函数，一般用于查询操作成功时的数据返回
  public JsonData(boolean success,String msg,int total,List datas){
    super();
    this.success = success;
    this.msg = msg;
    this.total = total;
    this.rows.addAll(datas);
  }
  
  private boolean success; // 操作成功与否
  private String msg; // 返回的结果信息
  private long total; // 查询记录数
  private List rows = new ArrayList(); // 记录详情：这里面存放的是一个集合
  
  public boolean isSuccess() {
    return success;
  }
  public void setSuccess(boolean success) {
    this.success = success;
  }
  public String getMsg() {
    return msg;
  }
  public void setMsg(String msg) {
    this.msg = msg;
  }
  public long getTotal() {
    return total;
  }
  public void setTotal(int total) {
    this.total = total;
  }
  public List getRows() {
    return rows;
  }
  public void setRows(List rows) {
    this.rows = rows;
  }

  @Override
  public String toString() {
    return "JsonData [success=" + success 
    		+ ", msg=" + msg 
    		+ ", total=" + total 
    		+ ", rows=" + rows 
    		+ "]";
  }
  
}