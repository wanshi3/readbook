package com.demo.pojo;

import java.io.Serializable;
import java.util.Random;

public class User extends Base implements Serializable {
    private int ID;
    private String Username;
    private String Password;
    private String Email;
    private String Phone;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
    public String toUsernamer() {
        int minLength = 7; // ��Сλ��
        int maxLength = 10; // ���λ��

        // ����Random����
        Random random = new Random();

        // �������λ��
        int length = random.nextInt(maxLength - minLength + 1) + minLength;

        // �����������
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // ����0��9֮��������
            sb.append(digit);
        }

        String randomNum = sb.toString();
        System.out.println("������֣�" + randomNum);
        return randomNum;
    }
    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                ", Email='" + Email + '\'' +
                ", Phone='" + Phone + '\'' +
                '}';
    }
}
