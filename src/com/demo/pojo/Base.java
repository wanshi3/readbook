package com.demo.pojo;

import java.io.Serializable;

/**
 *  由于几乎所有的select都有可能用到where、limit、order by
 *  我们在这里定义一个基本bean类，让其他的pojo类继承它
 *  这样，我们在定义pojo类的时候，只需要按照数据库的字段来确定属性和方法了
 */
public class Base implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private String condition; // 查询条件
  private String limit; // 记录数
  private String orderBy; // 排序条件
  
  public String getCondition() {
    return condition;
  }
  
  public void setCondition(String condition) {
    this.condition = condition;
  }
  
  public String getLimit() {
    return limit;
  }
  
  public void setLimit(String limit) {
    this.limit = limit;
  }
  
  public String getOrderBy() {
    return orderBy;
  }
  
  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  /**
   * 定义一个toString()方法，用来调试程序时，查看封装结果
   */
  @Override
  public String toString() {
    return "Base [condition=" + condition 
    		+ ", limit=" + limit 
    		+ ", orderBy=" + orderBy 
    		+ "]";
  }

  /**
   * 定义一个无参的构造函数
   * 有备无患^-^
   */
  public Base() {
    super();
  }
  
  /**
   * 既然这样，那就再定义一个带参的构造函数
   * 万一以后用的到呢^-^
   */
  public Base(String condition, String limit, String orderBy) {
    super();
    this.condition = condition;
    this.limit = limit;
    this.orderBy = orderBy;
  }

}