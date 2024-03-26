package com.demo.pojo;

import java.io.Serializable;

/**
 *  ���ڼ������е�select���п����õ�where��limit��order by
 *  ���������ﶨ��һ������bean�࣬��������pojo��̳���
 *  �����������ڶ���pojo���ʱ��ֻ��Ҫ�������ݿ���ֶ���ȷ�����Ժͷ�����
 */
public class Base implements Serializable {

    private static final long serialVersionUID = 1L;

    private String condition; // ��ѯ����
    private String limit; // ��¼��
    private String orderBy; // ��������

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
     * ����һ��toString()�������������Գ���ʱ���鿴��װ���
     */
    @Override
    public String toString() {
        return "Base [condition=" + condition
                + ", limit=" + limit
                + ", orderBy=" + orderBy
                + "]";
    }

    /**
     * ����һ���޲εĹ��캯��
     * �б��޻�^-^
     */
    public Base() {
        super();
    }

    /**
     * ��Ȼ�������Ǿ��ٶ���һ�����εĹ��캯��
     * ��һ�Ժ��õĵ���^-^
     */
    public Base(String condition, String limit, String orderBy) {
        super();
        this.condition = condition;
        this.limit = limit;
        this.orderBy = orderBy;
    }

}