package com.demo.pojo;

import java.io.Serializable;
import java.util.*;

/**
 * Ŀǰ���������г����ǰ�˿�ܣ������ݱ�������Դ���ҳ����
 * ��ҳ�Ĺ��ܣ�ǰ�˿�ܻ��Զ����
 * һ������£�ǰ�˶���Ҫ����pageSize
 * ���ԣ�Ҫʵ�ַ�ҳ����ֻ̨��Ҫ����һ��total��ǰ�˾Ϳ�����
 * ��������DAO�е�select�����ܷ���һ��List���
 * ��ʱ��ServiceDAO����Ҫ��total��ͬList��װ�����ơ�total=50,rows=[{},{}...{}]����ʽ��
 * �ȼ�ס���Bean��ɣ��Ժ�ȥService�㿴���ı���^-^
 */

public class JsonData implements Serializable {

    private static final long serialVersionUID = 1L;

    // �޲ι��캯����
    public JsonData() {
        super();
    }

    // ��2�������Ĺ��캯����һ��������ɾ�Ļ����ʧ��ʱ�����ݷ���
    public JsonData(boolean success,String msg){
        super();
        this.success = success;
        this.msg = msg;
    }

    // ��4�������Ĺ��캯����һ�����ڲ�ѯ�����ɹ�ʱ�����ݷ���
    public JsonData(boolean success,String msg,int total,List datas){
        super();
        this.success = success;
        this.msg = msg;
        this.total = total;
        this.rows.addAll(datas);
    }

    private boolean success; // �����ɹ����
    private String msg; // ���صĽ����Ϣ
    private long total; // ��ѯ��¼��
    private List rows = new ArrayList(); // ��¼���飺�������ŵ���һ������

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